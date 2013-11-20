/**
 * 
 */
package org.gamelib.network;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Axel
 */
public class Output {
	protected int maxCapacity;
	protected int position;
	protected int capacity;
	protected byte[] buffer;
	protected OutputStream outputStream;

	/**
	 * Creates a new Output for writing to a byte array.
	 * 
	 * @param bufferSize The initial size of the buffer.
	 * @param maxBufferSize The buffer is doubled as needed until it exceeds maxBufferSize and an exception is thrown. Can be -1 for no maximum.
	 */
	public Output(int bufferSize, int maxBufferSize) {
		if (maxBufferSize < -1) throw new IllegalArgumentException("maxBufferSize cannot be < -1: " + maxBufferSize);
		this.capacity = bufferSize;
		this.maxCapacity = maxBufferSize == -1 ? Integer.MAX_VALUE : maxBufferSize;
		buffer = new byte[bufferSize];
	}

	/** Creates a new Output for writing to an OutputStream. A buffer size of 4096 is used. */
	public Output(OutputStream outputStream) {
		this(4096, 4096);
		if ((this.outputStream = outputStream) == null) throw new IllegalArgumentException("outputStream cannot be null.");
	}

	/** @return true if the buffer has been resized. */
	protected boolean require(int required) throws RuntimeException {
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

	/** Writes the buffered bytes to the underlying OutputStream, if any. */
	public void flush() throws RuntimeException {
		if (outputStream == null) return;
		try {
			outputStream.write(buffer, 0, position);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		position = 0;
	}

	// byte

	/** Writes a byte. */
	public void writeByte(byte value) {
		if (position == capacity) require(1);
		buffer[position++] = value;
	}

	/** Writes the bytes. Note the byte[] length is not written. */
	public void writeBytes(byte[] bytes) {
		if (bytes == null) throw new IllegalArgumentException("bytes cannot be null.");
		writeBytes(bytes, 0, bytes.length);
	}

	/** Writes the bytes. Note the byte[] length is not written. */
	public void writeBytes(byte[] bytes, int offset, int length) {
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

	// int

	/** Writes a 4 byte int. Uses BIG_ENDIAN byte order. */
	public void writeInt(int value) {
		require(4);
		buffer[position++] = (byte) (value >> 24);
		buffer[position++] = (byte) (value >> 16);
		buffer[position++] = (byte) (value >> 8);
		buffer[position++] = (byte) value;
	}

	// string

	/** Writes a UTF-8 string. */
	public void writeString(String value) {
		writeInt(value.length());
		for (int i = 0; i < value.length(); i++) {
			writeChar(value.charAt(i));
		}
	}

	// float

	/** Writes a 4 byte float. */
	public void writeFloat(float value) throws RuntimeException {
		writeInt(Float.floatToIntBits(value));
	}

	// short

	/** Writes a 2 byte short. Uses BIG_ENDIAN byte order. */
	public void writeShort(int value) throws RuntimeException {
		require(2);
		buffer[position++] = (byte) (value >>> 8);
		buffer[position++] = (byte) value;
	}

	// long

	/** Writes an 8 byte long. Uses BIG_ENDIAN byte order. */
	public void writeLong(long value) throws RuntimeException {
		require(8);
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

	/** Writes a 1 byte boolean. */
	public void writeBoolean(boolean value) throws RuntimeException {
		if (position == capacity) require(1);
		buffer[position++] = (byte) (value ? 1 : 0);
	}

	// char

	/** Writes a 2 byte char. Uses BIG_ENDIAN byte order. */
	public void writeChar(char value) throws RuntimeException {
		require(2);
		buffer[position++] = (byte) (value >>> 8);
		buffer[position++] = (byte) value;
	}

	// double

	/** Writes an 8 byte double. */
	public void writeDouble(double value) throws RuntimeException {
		writeLong(Double.doubleToLongBits(value));
	}

}
