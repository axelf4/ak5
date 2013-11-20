/**
 * 
 */
package org.gamelib.network;

import java.io.IOException;
import java.io.InputStream;
import java.nio.BufferUnderflowException;

/**
 * @author Axel
 */
public class Input {

	protected byte[] buffer;
	protected int position;
	protected int capacity;
	protected int limit;
	protected int total;
	protected InputStream inputStream;

	/**
	 * Creates a new Input for reading from a byte array.
	 * 
	 * @param bufferSize The size of the buffer. An exception is thrown if more bytes than this are read.
	 */
	public Input(int bufferSize) {
		this.capacity = bufferSize;
		buffer = new byte[bufferSize];
	}

	/** Creates a new Input for reading from an InputStream with a buffer size of 4096. */
	public Input(InputStream inputStream) {
		this(4096);
		if ((this.inputStream = inputStream) == null) throw new IllegalArgumentException("inputStream cannot be null.");
	}

	/**
	 * Fills the buffer with more bytes. Can be overridden to fill the bytes from a source other than the InputStream.
	 * 
	 * @return -1 if there are no more bytes.
	 */
	protected int fill(byte[] buffer, int offset, int count)
			throws RuntimeException {
		if (inputStream == null) return -1;
		try {
			return inputStream.read(buffer, offset, count);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * @param required Must be > 0. The buffer is filled until it has at least this many bytes.
	 * @return the number of bytes remaining.
	 * @throws RuntimeException if EOS is reached before required bytes are read (buffer underflow).
	 */
	protected int require(int required) throws RuntimeException {
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

	// byte

	/** Reads a single byte. */
	public byte readByte() throws RuntimeException {
		require(1);
		return buffer[position++];
	}

	/** Reads a byte as an int from 0 to 255. */
	public int readByteUnsigned() throws RuntimeException {
		require(1);
		return buffer[position++] & 0xFF;
	}

	/** Reads the specified number of bytes into a new byte[]. */
	public byte[] readBytes(int length) throws RuntimeException {
		byte[] bytes = new byte[length];
		readBytes(bytes, 0, length);
		return bytes;
	}

	/** Reads bytes.length bytes and writes them to the specified byte[], starting at index 0. */
	public void readBytes(byte[] bytes) throws RuntimeException {
		readBytes(bytes, 0, bytes.length);
	}

	/** Reads count bytes and writes them to the specified byte[], starting at offset. */
	public void readBytes(byte[] bytes, int offset, int count)
			throws RuntimeException {
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

	// int

	/** Reads a 4 byte int. */
	public int readInt() {
		require(4);
		return (buffer[position++] & 0xFF) << 24 | //
		(buffer[position++] & 0xFF) << 16 | //
		(buffer[position++] & 0xFF) << 8 | //
		(buffer[position++] & 0xFF);
	}

	// string

	public String readString() {
		int length = readInt();
		StringBuilder builder = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			builder.append(readChar());
		}
		return builder.toString();
	}

	// float

	/** Reads a 4 byte float. */
	public float readFloat() throws RuntimeException {
		return Float.intBitsToFloat(readInt());
	}

	// short

	/** Reads a 2 byte short. */
	public short readShort() {
		require(2);
		return (short) (((buffer[position++] & 0xFF) << 8) | (buffer[position++] & 0xFF));
	}

	/** Reads a 2 byte short as an int from 0 to 65535. */
	public int readShortUnsigned() throws RuntimeException {
		require(2);
		return ((buffer[position++] & 0xFF) << 8) | (buffer[position++] & 0xFF);
	}

	// long

	/** Reads an 8 byte long. */
	public long readLong() throws RuntimeException {
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

	/** Reads a 1 byte boolean. */
	public boolean readBoolean() throws RuntimeException {
		require(1);
		return buffer[position++] == 1;
	}

	// char

	/** Reads a 2 byte char. */
	public char readChar() throws RuntimeException {
		require(2);
		return (char) (((buffer[position++] & 0xFF) << 8) | (buffer[position++] & 0xFF));
	}

	// double

	/** Reads an 8 bytes double. */
	public double readDouble() throws RuntimeException {
		return Double.longBitsToDouble(readLong());
	}

}
