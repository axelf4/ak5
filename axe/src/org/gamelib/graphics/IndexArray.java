/**
 * 
 */
package org.gamelib.graphics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/** @author pwnedary */
public class IndexArray implements IndexData {
	private ByteBuffer buffer;

	public IndexArray(int maxIndices) {
		buffer = (ByteBuffer) ByteBuffer.allocateDirect(maxIndices * 2).order(ByteOrder.nativeOrder()).flip();
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
}
