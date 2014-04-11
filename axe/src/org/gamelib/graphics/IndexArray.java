/**
 * 
 */
package org.gamelib.graphics;

import java.nio.ByteBuffer;

import org.gamelib.util.io.BufferUtil;

/** @author pwnedary */
public class IndexArray implements IndexData {
	private ByteBuffer buffer;

	public IndexArray(int maxIndices) {
		buffer = (ByteBuffer) BufferUtil.newByteBuffer(maxIndices * 2).flip();
	}

	@Override
	public int getNumIndices() {
		return buffer.limit();
	}

	@Override
	public void setIndices(byte[] indices, int offset, int length) {
		((ByteBuffer) buffer.clear()).put(indices, offset, length).flip();
	}

	@Override
	public ByteBuffer getBuffer() {
		return buffer;
	}

	@Override
	public void bind() {}

	@Override
	public void unbind() {}

	@Override
	public void dispose() {}
}
