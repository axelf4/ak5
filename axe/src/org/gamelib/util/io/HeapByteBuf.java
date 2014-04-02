/**
 * 
 */
package org.gamelib.util.io;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.gamelib.util.io.ByteBuf.ByteBufImpl;

/** A {@link ByteBuf} implementation that stores it's internal buffer on the Java heap.
 * 
 * @author pwnedary */
public class HeapByteBuf extends ByteBufImpl implements ByteBuf {
	private final byte[] array;
	private int mark = -1;
	private int position = 0;
	private int limit;
	private int capacity;

	public HeapByteBuf(int capacity) {
		array = new byte[limit = this.capacity = capacity];
	}

	@Override
	public int capacity() {
		return capacity;
	}

	@Override
	public int position() {
		return capacity;
	}

	@Override
	public ByteBuf position(int newPosition) {
		position = newPosition;
		return this;
	}

	@Override
	public int limit() {
		return limit;
	}

	@Override
	public ByteBuf limit(int newLimit) {
		limit = newLimit;
		return this;
	}

	@Override
	public ByteBuf mark() {
		mark = position;
		return this;
	}

	@Override
	public ByteBuf reset() {
		position = mark;
		mark = -1;
		return this;
	}

	@Override
	public ByteBuf clear() {
		position = 0;
		limit = capacity;
		return this;
	}

	@Override
	public ByteBuf flip() {
		limit = position;
		position = 0;
		mark = -1;
		return this;
	}

	@Override
	public ByteBuf rewind() {
		position = 0;
		mark = -1;
		return this;
	}

	@Override
	public ByteBuffer nio() {
		return ByteBuffer.wrap(array);
	}

	protected int require(int required) {
		int remaining = limit - position;
		if (remaining >= required) return required;
		throw new RuntimeException("Buffer too small: capacity: " + capacity + ", required: " + required);
	}

	@Override
	public byte get() {
		require(1);
		return array[position++];
	}

	@Override
	public void get(byte[] dst, int off, int len) {
		require(len);
		System.arraycopy(array, position, dst, off, len);
		position -= len;
	}

	@Override
	public void put(byte b) {
		require(1);
		array[position++] = b;
	}

	@Override
	public void put(byte[] src, int off, int len) {
		require(len);
		System.arraycopy(src, off, array, position, len);
		position += len;
	}

	@Override
	public int getInt() {
		require(4);
		return (array[position++] & 0xFF) << 24 | //
		(array[position++] & 0xFF) << 16 | //
		(array[position++] & 0xFF) << 8 | //
		(array[position++] & 0xFF);
	}

	@Override
	public void putInt(int value) {
		require(4);
		array[position++] = (byte) (value >> 24);
		array[position++] = (byte) (value >> 16);
		array[position++] = (byte) (value >> 8);
		array[position++] = (byte) value;
	}

	@Override
	public float getFloat() {
		return Float.intBitsToFloat(getInt());
	}

	@Override
	public void putFloat(float value) throws RuntimeException {
		putInt(Float.floatToIntBits(value));
	}

	@Override
	public short getShort() {
		require(2);
		return (short) (((array[position++] & 0xFF) << 8) | (array[position++] & 0xFF));
	}

	@Override
	public void putShort(short value) throws RuntimeException {
		require(2);
		array[position++] = (byte) (value >>> 8);
		array[position++] = (byte) value;
	}

	@Override
	public long getLong() {
		require(8);
		byte[] array = this.array;
		return (long) array[position++] << 56 //
				| (long) (array[position++] & 0xFF) << 48 //
				| (long) (array[position++] & 0xFF) << 40 //
				| (long) (array[position++] & 0xFF) << 32 //
				| (long) (array[position++] & 0xFF) << 24 //
				| (array[position++] & 0xFF) << 16 //
				| (array[position++] & 0xFF) << 8 //
				| array[position++] & 0xFF;
	}

	@Override
	public void putLong(long value) throws RuntimeException {
		require(8);
		byte[] array = this.array;
		array[position++] = (byte) (value >>> 56);
		array[position++] = (byte) (value >>> 48);
		array[position++] = (byte) (value >>> 40);
		array[position++] = (byte) (value >>> 32);
		array[position++] = (byte) (value >>> 24);
		array[position++] = (byte) (value >>> 16);
		array[position++] = (byte) (value >>> 8);
		array[position++] = (byte) value;
	}

	@Override
	public boolean getBoolean() {
		require(1);
		return array[position++] == 1;
	}

	@Override
	public void putBoolean(boolean value) throws RuntimeException {
		if (position == capacity) require(1);
		array[position++] = (byte) (value ? 1 : 0);
	}

	@Override
	public char getChar() {
		require(2);
		return (char) (((array[position++] & 0xFF) << 8) | (array[position++] & 0xFF));
	}

	@Override
	public void putChar(char value) throws RuntimeException {
		require(2);
		array[position++] = (byte) (value >>> 8);
		array[position++] = (byte) value;
	}

	@Override
	public double getDouble() {
		return Double.longBitsToDouble(getLong());
	}

	@Override
	public void putDouble(double value) throws RuntimeException {
		putLong(Double.doubleToLongBits(value));
	}

	@Override
	public String readString() {
		byte[] bytes = new byte[getInt()]; // UTF_8 1byte/ch
		get(bytes, 0, bytes.length);
		return new String(bytes, StandardCharsets.UTF_8);
	}

	@Override
	public void writeString(String value) {
		putInt(value.length());
		for (int i = 0; i < value.length(); i++)
			putChar(value.charAt(i));
	}
}
