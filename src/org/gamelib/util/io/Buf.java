/**
 * 
 */
package org.gamelib.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.gamelib.util.io.Serializer.Serialization;

/**
 * A (<code>byte</code>) buffer implementing {@link ObjectSerialization} for ease by way of de/serialization. Most commonly you'd just want to wrap a nio {@link ByteBuffer} in {@link NIOByteBuffer}.
 * 
 * @author pwnedary
 */
public interface Buf extends Strm, ObjectSerialization {
	/** Returns this buffer's capacity. */
	int capacity();

	int position();

	void position(int newPosition);

	int limit();

	void limit(int newLimit);

	/** Clears this buffer by setting the position to zero and the limit to the capacity. */
	void clear();

	/** Flips this buffer by setting the limit to the position and the position to zero. */
	void flip();

	/** Rewinds this buffer by setting the position to zero. */
	void rewind();

	/**
	 * Returns the number of bytes between the current position and the limit.
	 * 
	 * @return The number of bytes remaining in this buffer
	 */
	int remaining();

	/**
	 * Tells whether there are any bytes between the current position and the limit.
	 * 
	 * @return <tt>true</tt> if there's any bytes remaining
	 */
	boolean hasRemaining();

	void flush();

	ByteBuffer nio();

	/* BYTE */

	/** Reads a single byte. */
	@Override
	byte read();

	/** Reads count bytes and writes them to the specified byte[], starting at offset. */
	@Override
	public void read(byte[] dst, int off, int len);

	/** Writes a byte. */
	@Override
	public void write(byte b);

	/** Writes the bytes. Note the byte[] length is not written. */
	@Override
	public void write(byte[] src, int off, int len);

	public static abstract class BufImpl extends ObjectSerializationImp implements Buf {
		@Override
		public int availible() {
			return capacity() - position();
		}

		@Override
		public long skip(long n) {
			long remaining = n;
			int nr;
			if (n <= 0) return 0;
			int size = (int) Math.min(2048, remaining); // MAX_SKIP_BUFFER_SIZE
			byte[] skipBuffer = new byte[size];
			while (remaining > 0) {
				int position = position();
				read(skipBuffer, 0, (int) Math.min(size, remaining));
				nr = position() - position;
				if (nr < 0) break;
				remaining -= nr;
			}
			return 0;
		}

		@Override
		public void clear() {
			position(0);
			limit(capacity());
		}

		@Override
		public void flip() {
			limit(position());
			position(0);
		}

		@Override
		public void rewind() {
			position(0);
		}

		@Override
		public int remaining() {
			return limit() - position();
		}

		@Override
		public boolean hasRemaining() {
			return remaining() > 0;
		}

		/** Reads an Enum. */
		@SuppressWarnings("unchecked")
		public <T extends Enum<T>> T readEnum() {
			try {
				return (T) Class.forName(readString()).getEnumConstants()[readInt()];
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		@SuppressWarnings("unchecked")
		public <T> Object readObject() {
			try {
				String typeName = readString();
				Class<T> type = (Class<T>) Class.forName(typeName);
				if (type == boolean.class || type == Boolean.class) return readBoolean();
				else if (type == byte.class || type == Byte.class) return read();
				else if (type == char.class || type == Character.class) return readChar();
				else if (type == short.class || type == Short.class) return readShort();
				else if (type == int.class || type == Integer.class) return readInt();
				else if (type == long.class || type == Long.class) return readLong();
				else if (type == float.class || type == Float.class) return readFloat();
				else if (type == double.class || type == Double.class) return readDouble();
				else if (type == String.class) return readString();
				else if (type.isEnum()) return readEnum();
				else {
					T value;
					Serializer<?> serializer = Serialization.serializers.get(type);
					if (serializer == null && type.newInstance() instanceof Serializer<?>) Serialization.serializers.put(type, serializer = (Serializer<?>) type.newInstance());
					if (serializer != null) value = (T) serializer.read(this);
					else {
						value = type.newInstance();
						for (Field field : type.getDeclaredFields()) {
							field.setAccessible(true);
							if (!Modifier.isTransient(field.getModifiers())) field.set(value, readObject());
						}
					}
					return value;
				}
			} catch (ReflectiveOperationException e) {
				e.printStackTrace();
				return null;
			}
		}

		/** Writes an enum. */
		public void writeEnum(Enum<?> value) {
			writeString(value.getClass().getName());
			writeInt(value.ordinal());
		}

		@Override
		public void writeObject(Object value) {
			Class<?> type = value.getClass();
			writeString(type.getName());
			if (type == boolean.class || type == Boolean.class) writeBoolean((boolean) value);
			else if (type == byte.class || type == Byte.class) write((byte) value);
			else if (type == char.class || type == Character.class) writeChar((char) value);
			else if (type == short.class || type == Short.class) writeShort((short) value);
			else if (type == int.class || type == Integer.class) writeInt((int) value);
			else if (type == long.class || type == Long.class) writeLong((long) value);
			else if (type == float.class || type == Float.class) writeFloat((float) value);
			else if (type == double.class || type == Double.class) writeDouble((double) value);
			else if (type == String.class) writeString((String) value);
			else if (type.isEnum()) writeEnum((Enum<?>) value);
			else {
				@SuppressWarnings("unchecked")
				Serializer<Object> serializer = (Serializer<Object>) (value instanceof Serializer<?> ? value : Serialization.serializers.get(type));
				if (serializer != null) serializer.write(this, value);
				else try {
					for (Field field : type.getDeclaredFields()) {
						field.setAccessible(true);
						if (!Modifier.isTransient(field.getModifiers())) writeObject(field.get(value));
					}
				} catch (ReflectiveOperationException e) {
					e.printStackTrace();
				}
			}
			flush();
		}
	}

	public static class NIOByteBuffer extends BufImpl implements Buf {
		protected ByteBuffer buffer;

		/** Constructs a new Buf backed by buffer */
		public NIOByteBuffer(ByteBuffer buffer) {
			this.buffer = buffer;
		}

		@Override
		public int capacity() {
			return buffer.capacity();
		}

		@Override
		public int position() {
			return buffer.position();
		}

		@Override
		public void position(int newPosition) {
			buffer.position(newPosition);
		}

		@Override
		public int limit() {
			return buffer.limit();
		}

		@Override
		public void limit(int newLimit) {
			buffer.limit(newLimit);
		}

		@Override
		public void flush() {}

		@Override
		public ByteBuffer nio() {
			return buffer;
		}

		@Override
		public byte read() {
			return buffer.get();
		}

		@Override
		public void read(byte[] dst, int off, int len) {
			buffer.get(dst, off, len);
		}

		@Override
		public void write(byte b) {
			buffer.put(b);
		}

		@Override
		public void write(byte[] src, int off, int len) {
			buffer.put(src, off, off);
		}

		@Override
		public int readInt() {
			return buffer.getInt();
		}

		@Override
		public void writeInt(int value) {
			buffer.putInt(value);
		}

		@Override
		public float readFloat() {
			return buffer.getFloat();
		}

		@Override
		public void writeFloat(float value) {
			buffer.putFloat(value);
		}

		@Override
		public short readShort() {
			return buffer.getShort();
		}

		@Override
		public void writeShort(short value) {
			buffer.putShort(value);
		}

		@Override
		public long readLong() {
			return buffer.getLong();
		}

		@Override
		public void writeLong(long value) {
			buffer.putLong(value);
		}

		@Override
		public boolean readBoolean() {
			return buffer.get() == 1;
		}

		@Override
		public void writeBoolean(boolean value) {
			buffer.put((byte) (value ? 1 : 0));
		}

		@Override
		public char readChar() {
			return buffer.getChar();
		}

		@Override
		public void writeChar(char value) {
			buffer.putChar(value);
		}

		@Override
		public double readDouble() {
			return buffer.getDouble();
		}

		@Override
		public void writeDouble(double value) {
			buffer.putDouble(value);
		}

		@Override
		public String readString() {
			byte[] bytes = new byte[readInt()]; // UTF_8 1byte/ch
			read(bytes, 0, bytes.length);
			return new String(bytes, StandardCharsets.UTF_8);
		}

		@Override
		public void writeString(String value) {
			writeInt(value.length());
			buffer.put(value.getBytes(StandardCharsets.UTF_8));
		}
	}

	public static class StreamByteBuffer extends BufImpl implements Buf {
		protected int maxCapacity;
		protected int position;
		protected int capacity;
		protected byte[] buffer;
		protected int limit, total;
		protected InputStream inputStream;
		protected OutputStream outputStream;

		/**
		 * Creates a new Output for writing to a byte array.
		 * 
		 * @param bufferSize The initial size of the buffer.
		 * @param maxBufferSize The buffer is doubled as needed until it exceeds maxBufferSize and an exception is thrown. Can be -1 for no maximum.
		 */
		public StreamByteBuffer(int bufferSize, int maxBufferSize) {
			if (maxBufferSize < -1) throw new IllegalArgumentException("maxBufferSize cannot be < -1: " + maxBufferSize);
			this.capacity = bufferSize;
			this.maxCapacity = maxBufferSize == -1 ? Integer.MAX_VALUE : maxBufferSize;
			buffer = new byte[bufferSize];
		}

		/** Creates a new Input for reading from an InputStream with a buffer size of 4096. */
		public StreamByteBuffer(InputStream inputStream) {
			this(4096, 4096);
			if ((this.inputStream = inputStream) == null) throw new IllegalArgumentException("inputStream cannot be null.");
		}

		/** Creates a new Output for writing to an OutputStream. A buffer size of 4096 is used. */
		public StreamByteBuffer(OutputStream outputStream) {
			this(4096, 4096);
			if ((this.outputStream = outputStream) == null) throw new IllegalArgumentException("outputStream cannot be null.");
		}

		public StreamByteBuffer(InputStream inputStream, OutputStream outputStream) {
			this(4096, 4096);
			if ((this.inputStream = inputStream) == null) throw new IllegalArgumentException("inputStream cannot be null.");
			if ((this.outputStream = outputStream) == null) throw new IllegalArgumentException("outputStream cannot be null.");
		}

		@Override
		public int capacity() {
			return capacity;
		}

		@Override
		public int position() {
			return position;
		}

		@Override
		public void position(int newPosition) {
			this.position = newPosition;
		}

		@Override
		public int limit() {
			return limit;
		}

		@Override
		public void limit(int newLimit) {
			this.limit = newLimit;
		}

		/** @return true if the buffer has been resized. */
		protected boolean require2(int required) throws RuntimeException {
			if (capacity - position >= required) return false;
			if (required > maxCapacity) throw new RuntimeException("Buffer overflow. Max capacity: " + maxCapacity + ", required: " + required);
			flush();
			while (capacity - position < required) {
				if (capacity == maxCapacity) throw new RuntimeException("Buffer overflow. Available: " + (capacity - position) + ", required: " + required);
				// Grow buffer.
				capacity = Math.min(capacity * 2, maxCapacity);
				if (capacity < 0) capacity = maxCapacity;
				byte[] tmp = new byte[capacity];
				System.arraycopy(buffer, 0, tmp, 0, position);
				buffer = tmp;
			}
			return true;
		}

		@Override
		public void flush() {
			if (outputStream == null) return;
			try {
				outputStream.write(buffer, 0, position);
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
			position = 0;
		}

		/**
		 * @param required Must be > 0. The buffer is filled until it has at least this many bytes.
		 * @return the number of bytes remaining.
		 * @throws RuntimeException if EOS is reached before required bytes are read (buffer underflow).
		 */
		protected int require(int required) {
			int remaining = limit - position;
			if (remaining >= required) return remaining;
			if (required > capacity) throw new RuntimeException("Buffer too small: capacity: " + capacity + ", required: " + required);

			int count;
			// Try to fill the buffer.
			if (remaining > 0) {
				count = fill(buffer, limit, capacity - limit);
				if (count == -1) throw new BufferUnderflowException();
				remaining += count;
				if (remaining >= required) {
					limit += count;
					return remaining;
				}
			}

			// Was not enough, compact and try again.
			System.arraycopy(buffer, position, buffer, 0, remaining);
			total += position;
			position = 0;

			while (true) {
				count = fill(buffer, remaining, capacity - remaining);
				if (count == -1) {
					if (remaining >= required) break;
					throw new BufferUnderflowException();
				}
				remaining += count;
				if (remaining >= required) break; // Enough has been read.
			}

			limit = remaining;
			return remaining;
		}

		protected int fill(byte[] buffer, int offset, int count)
				throws RuntimeException {
			if (inputStream == null) return -1;
			try {
				return inputStream.read(buffer, offset, count);
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}

		@Override
		public byte read() {
			require(1);
			return buffer[position++];
		}

		@Override
		public void read(byte[] bytes, int offset, int count) {
			if (bytes == null) throw new IllegalArgumentException("bytes cannot be null.");
			int copyCount = Math.min(limit - position, count);
			while (true) {
				System.arraycopy(buffer, position, bytes, offset, copyCount);
				position += copyCount;
				count -= copyCount;
				if (count == 0) break;
				offset += copyCount;
				copyCount = Math.min(count, capacity);
				require(copyCount);
			}
		}

		@Override
		public void write(byte value) {
			if (position == capacity) require(1);
			buffer[position++] = value;
		}

		@Override
		public void write(byte[] bytes, int offset, int length) {
			if (bytes == null) throw new IllegalArgumentException("bytes cannot be null.");
			int copyCount = Math.min(capacity - position, length);
			while (true) {
				System.arraycopy(bytes, offset, buffer, position, copyCount);
				position += copyCount;
				if ((length -= copyCount) == 0) return;
				offset += copyCount;
				copyCount = Math.min(capacity, length);
				require(copyCount);
			}
		}

		@Override
		public int readInt() {
			require(4);
			return (buffer[position++] & 0xFF) << 24 | //
			(buffer[position++] & 0xFF) << 16 | //
			(buffer[position++] & 0xFF) << 8 | //
			(buffer[position++] & 0xFF);
		}

		// string
		@Override
		public String readString() {
			int length = readInt();
			StringBuilder builder = new StringBuilder(length);
			for (int i = 0; i < length; i++) {
				builder.append(readChar());
			}
			return builder.toString();
		}

		// float
		@Override
		public float readFloat() {
			return Float.intBitsToFloat(readInt());
		}

		// short
		@Override
		public short readShort() {
			require(2);
			return (short) (((buffer[position++] & 0xFF) << 8) | (buffer[position++] & 0xFF));
		}

		// long
		@Override
		public long readLong() {
			require(8);
			byte[] buffer = this.buffer;
			return (long) buffer[position++] << 56 //
					| (long) (buffer[position++] & 0xFF) << 48 //
					| (long) (buffer[position++] & 0xFF) << 40 //
					| (long) (buffer[position++] & 0xFF) << 32 //
					| (long) (buffer[position++] & 0xFF) << 24 //
					| (buffer[position++] & 0xFF) << 16 //
					| (buffer[position++] & 0xFF) << 8 //
					| buffer[position++] & 0xFF;
		}

		// boolean
		@Override
		public boolean readBoolean() {
			require(1);
			return buffer[position++] == 1;
		}

		// char
		@Override
		public char readChar() {
			require(2);
			return (char) (((buffer[position++] & 0xFF) << 8) | (buffer[position++] & 0xFF));
		}

		// double
		@Override
		public double readDouble() {
			return Double.longBitsToDouble(readLong());
		}

		// int
		@Override
		public void writeInt(int value) {
			require2(4);
			buffer[position++] = (byte) (value >> 24);
			buffer[position++] = (byte) (value >> 16);
			buffer[position++] = (byte) (value >> 8);
			buffer[position++] = (byte) value;
		}

		// string

		/** Writes a UTF-8 string. */
		@Override
		public void writeString(String value) {
			writeInt(value.length());
			for (int i = 0; i < value.length(); i++)
				writeChar(value.charAt(i));
		}

		// float

		/** Writes a 4 byte float. */
		@Override
		public void writeFloat(float value) throws RuntimeException {
			writeInt(Float.floatToIntBits(value));
		}

		// short

		/** Writes a 2 byte short. Uses BIG_ENDIAN byte order. */
		@Override
		public void writeShort(short value) throws RuntimeException {
			require2(2);
			buffer[position++] = (byte) (value >>> 8);
			buffer[position++] = (byte) value;
		}

		// long

		/** Writes an 8 byte long. Uses BIG_ENDIAN byte order. */
		@Override
		public void writeLong(long value) throws RuntimeException {
			require2(8);
			byte[] buffer = this.buffer;
			buffer[position++] = (byte) (value >>> 56);
			buffer[position++] = (byte) (value >>> 48);
			buffer[position++] = (byte) (value >>> 40);
			buffer[position++] = (byte) (value >>> 32);
			buffer[position++] = (byte) (value >>> 24);
			buffer[position++] = (byte) (value >>> 16);
			buffer[position++] = (byte) (value >>> 8);
			buffer[position++] = (byte) value;
		}

		// boolean
		@Override
		public void writeBoolean(boolean value) throws RuntimeException {
			if (position == capacity) require2(1);
			buffer[position++] = (byte) (value ? 1 : 0);
		}

		// char
		@Override
		public void writeChar(char value) throws RuntimeException {
			require2(2);
			buffer[position++] = (byte) (value >>> 8);
			buffer[position++] = (byte) value;
		}

		// double

		@Override
		public void writeDouble(double value) throws RuntimeException {
			writeLong(Double.doubleToLongBits(value));
		}

		@Override
		public ByteBuffer nio() {
			ByteBuffer byteBuffer = ByteBuffer.wrap(buffer, 0, buffer.length);
			byteBuffer.position(position);
			byteBuffer.limit(limit);
			return byteBuffer;
		}
	}
}
