/**
 * 
 */
package ak5.util.io;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import ak5.util.io.ByteBuf.ByteBufImpl;

/** A {@link ByteBuf} implementation that uses a Java NIO {@link ByteBuffer} as it's internal buffer.
 * 
 * @author pwnedary */
public class NIOByteBuf extends ByteBufImpl implements ByteBuf {
	protected ByteBuffer buffer;

	/** Constructs a new Buf backed by buffer. */
	public NIOByteBuf(ByteBuffer buffer) {
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
	public ByteBuf position(int newPosition) {
		buffer.position(newPosition);
		return this;
	}

	@Override
	public int limit() {
		return buffer.limit();
	}

	@Override
	public ByteBuf limit(int newLimit) {
		buffer.limit(newLimit);
		return this;
	}

	@Override
	public ByteBuf mark() {
		buffer.mark();
		return this;
	}

	@Override
	public ByteBuf reset() {
		buffer.reset();
		return this;
	}

	@Override
	public ByteBuf clear() {
		buffer.clear();
		return this;
	}

	@Override
	public ByteBuf flip() {
		buffer.flip();
		return this;
	}

	@Override
	public ByteBuf rewind() {
		buffer.rewind();
		return this;
	}

	@Override
	public ByteBuffer nio() {
		return buffer;
	}

	@Override
	public byte get() {
		return buffer.get();
	}

	@Override
	public void get(byte[] dst, int off, int len) {
		buffer.get(dst, off, len);
	}

	@Override
	public void put(byte b) {
		buffer.put(b);
	}

	@Override
	public void put(byte[] src, int off, int len) {
		buffer.put(src, off, off);
	}

	@Override
	public int getInt() {
		return buffer.getInt();
	}

	@Override
	public void putInt(int value) {
		buffer.putInt(value);
	}

	@Override
	public float getFloat() {
		return buffer.getFloat();
	}

	@Override
	public void putFloat(float value) {
		buffer.putFloat(value);
	}

	@Override
	public short getShort() {
		return buffer.getShort();
	}

	@Override
	public void putShort(short value) {
		buffer.putShort(value);
	}

	@Override
	public long getLong() {
		return buffer.getLong();
	}

	@Override
	public void putLong(long value) {
		buffer.putLong(value);
	}

	@Override
	public boolean getBoolean() {
		return buffer.get() == 1;
	}

	@Override
	public void putBoolean(boolean value) {
		buffer.put((byte) (value ? 1 : 0));
	}

	@Override
	public char getChar() {
		return buffer.getChar();
	}

	@Override
	public void putChar(char value) {
		buffer.putChar(value);
	}

	@Override
	public double getDouble() {
		return buffer.getDouble();
	}

	@Override
	public void putDouble(double value) {
		buffer.putDouble(value);
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
		buffer.put(value.getBytes(StandardCharsets.UTF_8));
	}
}
