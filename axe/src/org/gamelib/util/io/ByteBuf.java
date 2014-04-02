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
import java.nio.InvalidMarkException;

import org.gamelib.util.io.Serializer.Serialization;

/** A random and sequential accessible sequence of zero or more bytes (octets), abstracting byte arrays ({@code byte[]})
 * and {@linkplain ByteBuffer NIO Buffers}.
 * 
 * @author pwnedary */
public interface ByteBuf extends ObjectSerialization {
	/** Returns the number of bytes (octets) this buffer can contain.
	 * 
	 * @return the capacity */
	int capacity();

	/** Returns this buffer's position. </p>
	 * 
	 * @return The position of this buffer */
	int position();

	/** Sets this buffer's position. If the mark is defined and larger than the new position then it is discarded.
	 * 
	 * @param newPosition The new position value; must be non-negative and no larger than the current limit
	 * @return This buffer
	 * @throws IllegalArgumentException If the preconditions on <tt>newPosition</tt> do not hold */
	ByteBuf position(int newPosition);

	/** Returns this buffer's limit.
	 * 
	 * @return The limit of this buffer */
	int limit();

	/** Sets this buffer's limit. If the position is larger than the new limit then it is set to the new limit. If the
	 * mark is defined and larger than the new limit then it is discarded.
	 * 
	 * @param newLimit The new limit value; must be non-negative and no larger than this buffer's capacity
	 * @return This buffer
	 * @throws IllegalArgumentException If the preconditions on <tt>newLimit</tt> do not hold */
	ByteBuf limit(int newLimit);

	/** Sets this buffer's mark at its position.
	 * 
	 * @return This buffer */
	ByteBuf mark();

	/** Resets this buffer's position to the previously-marked position. Invoking this method neither changes nor
	 * discards the mark's value.
	 * 
	 * @return This buffer
	 * @throws InvalidMarkException If the mark has not been set */
	ByteBuf reset();

	/** Clears this buffer by setting the position to zero, the limit to the capacity and discarding the mark.
	 * 
	 * @return This buffer */
	ByteBuf clear();

	/** Flips this buffer by setting the limit to the position, the position to zero and discarding the mark.
	 * 
	 * @return This buffer */
	ByteBuf flip();

	/** Rewinds this buffer by setting the position to zero and discarding the mark.
	 * 
	 * @return This buffer */
	ByteBuf rewind();

	/** Returns the number of bytes between the current position and the limit.
	 * 
	 * @return The number of bytes remaining in this buffer */
	int remaining();

	/** Tells whether there are any bytes between the current position and the limit.
	 * 
	 * @return <tt>true</tt> if there's any bytes remaining */
	boolean hasRemaining();

	ByteBuf flush();

	/** Returns a {@linkplain ByteBuffer NIO Byte Buffer} representing this buffer. */
	ByteBuffer nio();

	/* BYTE */

	/** Reads a single byte. */
	byte get();

	/** Reads count bytes and writes them to the specified byte[], starting at offset. */
	public void get(byte[] dst, int off, int len);

	/** Writes a byte. */
	public void put(byte b);

	/** Writes the bytes. Note the byte[] length is not written. */
	public void put(byte[] src, int off, int len);

	/* BOOLEAN */

	/** Reads an 1 byte boolean. */
	public boolean getBoolean();

	/** Writes an 1 byte boolean. */
	public void putBoolean(boolean value);

	/* CHAR */

	/** Reads a 2 byte char. */
	public char getChar();

	/** Writes a 2 byte char. Uses BIG_ENDIAN byte order. */
	public void putChar(char value);

	/* SHORT */

	/** Reads a 2 byte short. */
	public short getShort();

	/** Writes a 2 byte short. Uses BIG_ENDIAN byte order. */
	public void putShort(short value);

	/* INTEGER */

	/** Reads a 4 byte int. */
	public int getInt();

	/** Writes a 4 byte int. Uses BIG_ENDIAN byte order. */
	public void putInt(int value);

	/* LONG */

	/** Reads an 8 byte long. */
	public long getLong();

	/** Writes an 8 byte long. Uses BIG_ENDIAN byte order. */
	public void putLong(long value);

	/* FLOAT */

	/** Reads a 4 byte float. */
	public float getFloat();

	/** Writes a 4 byte float. */
	public void putFloat(float value);

	/* DOUBLE */

	/** Reads an 8 bytes double. */
	public double getDouble();

	/** Writes an 8 byte double. */
	public void putDouble(double value);

	/** Enumeration of endianness or byte orders. */
	public enum ByteOrder {
		/** Bytes of a multibyte value are ordered from most significant to least significant. */
		BIG_ENDIAN,
		/** Bytes of a multibyte value are ordered from least significant to most significant. */
		LITTLE_ENDIAN;
	}

	public static abstract class ByteBufImpl implements ByteBuf {
		protected int mark = -1;

		/* @Override public long skip(long n) { long remaining = n; int nr; if (n <= 0) return 0; int size = (int)
		 * Math.min(2048, remaining); // MAX_SKIP_BUFFER_SIZE byte[] skipBuffer = new byte[size]; while (remaining > 0)
		 * { int position = position(); get(skipBuffer, 0, (int) Math.min(size, remaining)); nr = position() - position;
		 * if (nr < 0) break; remaining -= nr; } return 0; } */

		@Override
		public ByteBuf mark() {
			this.mark = position();
			return this;
		}

		@Override
		public ByteBuf reset() {
			if (mark == -1) throw new InvalidMarkException();
			return position(mark);
		}

		@Override
		public ByteBuf clear() {
			position(0);
			limit(capacity());
			this.mark = -1;
			return this;
		}

		@Override
		public ByteBuf flip() {
			limit(position());
			position(0);
			this.mark = -1;
			return this;
		}

		@Override
		public ByteBuf rewind() {
			position(0);
			this.mark = -1;
			return this;
		}

		@Override
		public int remaining() {
			return limit() - position();
		}

		@Override
		public boolean hasRemaining() {
			return remaining() > 0;
		}

		@Override
		public ByteBuf flush() {
			return this;
		}

		/** Reads an Enum. */
		@SuppressWarnings("unchecked")
		public <T extends Enum<T>> T readEnum() {
			try {
				return (T) Class.forName(readString()).getEnumConstants()[getInt()];
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
				if (type == boolean.class || type == Boolean.class) return getBoolean();
				else if (type == byte.class || type == Byte.class) return get();
				else if (type == char.class || type == Character.class) return getChar();
				else if (type == short.class || type == Short.class) return getShort();
				else if (type == int.class || type == Integer.class) return getInt();
				else if (type == long.class || type == Long.class) return getLong();
				else if (type == float.class || type == Float.class) return getFloat();
				else if (type == double.class || type == Double.class) return getDouble();
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
			putInt(value.ordinal());
		}

		@Override
		public void writeObject(Object value) {
			Class<?> type = value.getClass();
			writeString(type.getName());
			if (type == boolean.class || type == Boolean.class) putBoolean((boolean) value);
			else if (type == byte.class || type == Byte.class) put((byte) value);
			else if (type == char.class || type == Character.class) putChar((char) value);
			else if (type == short.class || type == Short.class) putShort((short) value);
			else if (type == int.class || type == Integer.class) putInt((int) value);
			else if (type == long.class || type == Long.class) putLong((long) value);
			else if (type == float.class || type == Float.class) putFloat((float) value);
			else if (type == double.class || type == Double.class) putDouble((double) value);
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

	public static class StreamByteBuf extends ByteBufImpl implements ByteBuf {
		protected int maxCapacity;
		protected int position;
		protected int capacity;
		protected byte[] buffer;
		protected int limit, total;
		protected InputStream inputStream;
		protected OutputStream outputStream;

		/** Creates a new Output for writing to a byte array.
		 * 
		 * @param bufferSize The initial size of the buffer.
		 * @param maxBufferSize The buffer is doubled as needed until it exceeds maxBufferSize and an exception is
		 *            thrown. Can be -1 for no maximum. */
		public StreamByteBuf(int bufferSize, int maxBufferSize) {
			if (maxBufferSize < -1) throw new IllegalArgumentException("maxBufferSize cannot be < -1: " + maxBufferSize);
			this.capacity = bufferSize;
			this.maxCapacity = maxBufferSize == -1 ? Integer.MAX_VALUE : maxBufferSize;
			buffer = new byte[bufferSize];
		}

		/** Creates a new Input for reading from an InputStream with a buffer size of 4096. */
		public StreamByteBuf(InputStream inputStream) {
			this(4096, 4096);
			if ((this.inputStream = inputStream) == null) throw new IllegalArgumentException("inputStream cannot be null.");
		}

		/** Creates a new Output for writing to an OutputStream. A buffer size of 4096 is used. */
		public StreamByteBuf(OutputStream outputStream) {
			this(4096, 4096);
			if ((this.outputStream = outputStream) == null) throw new IllegalArgumentException("outputStream cannot be null.");
		}

		public StreamByteBuf(InputStream inputStream, OutputStream outputStream) {
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
		public ByteBuf position(int newPosition) {
			this.position = newPosition;
			return this;
		}

		@Override
		public int limit() {
			return limit;
		}

		@Override
		public ByteBuf limit(int newLimit) {
			this.limit = newLimit;
			return this;
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
		public ByteBuf flush() {
			if (outputStream == null) return this;
			try {
				outputStream.write(buffer, 0, position);
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
			position = 0;
			return this;
		}

		/** @param required Must be > 0. The buffer is filled until it has at least this many bytes.
		 * @return the number of bytes remaining.
		 * @throws RuntimeException if EOS is reached before required bytes are read (buffer underflow). */
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
		public byte get() {
			require(1);
			return buffer[position++];
		}

		@Override
		public void get(byte[] bytes, int offset, int count) {
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
		public void put(byte value) {
			if (position == capacity) require(1);
			buffer[position++] = value;
		}

		@Override
		public void put(byte[] bytes, int offset, int length) {
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
		public int getInt() {
			require(4);
			return (buffer[position++] & 0xFF) << 24 | //
			(buffer[position++] & 0xFF) << 16 | //
			(buffer[position++] & 0xFF) << 8 | //
			(buffer[position++] & 0xFF);
		}

		// string
		@Override
		public String readString() {
			int length = getInt();
			StringBuilder builder = new StringBuilder(length);
			for (int i = 0; i < length; i++) {
				builder.append(getChar());
			}
			return builder.toString();
		}

		// float
		@Override
		public float getFloat() {
			return Float.intBitsToFloat(getInt());
		}

		// short
		@Override
		public short getShort() {
			require(2);
			return (short) (((buffer[position++] & 0xFF) << 8) | (buffer[position++] & 0xFF));
		}

		// long
		@Override
		public long getLong() {
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
		public boolean getBoolean() {
			require(1);
			return buffer[position++] == 1;
		}

		// char
		@Override
		public char getChar() {
			require(2);
			return (char) (((buffer[position++] & 0xFF) << 8) | (buffer[position++] & 0xFF));
		}

		// double
		@Override
		public double getDouble() {
			return Double.longBitsToDouble(getLong());
		}

		// int
		@Override
		public void putInt(int value) {
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
			putInt(value.length());
			for (int i = 0; i < value.length(); i++)
				putChar(value.charAt(i));
		}

		// float

		/** Writes a 4 byte float. */
		@Override
		public void putFloat(float value) throws RuntimeException {
			putInt(Float.floatToIntBits(value));
		}

		// short

		/** Writes a 2 byte short. Uses BIG_ENDIAN byte order. */
		@Override
		public void putShort(short value) throws RuntimeException {
			require2(2);
			buffer[position++] = (byte) (value >>> 8);
			buffer[position++] = (byte) value;
		}

		// long

		/** Writes an 8 byte long. Uses BIG_ENDIAN byte order. */
		@Override
		public void putLong(long value) throws RuntimeException {
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
		public void putBoolean(boolean value) throws RuntimeException {
			if (position == capacity) require2(1);
			buffer[position++] = (byte) (value ? 1 : 0);
		}

		// char
		@Override
		public void putChar(char value) throws RuntimeException {
			require2(2);
			buffer[position++] = (byte) (value >>> 8);
			buffer[position++] = (byte) value;
		}

		// double

		@Override
		public void putDouble(double value) throws RuntimeException {
			putLong(Double.doubleToLongBits(value));
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
