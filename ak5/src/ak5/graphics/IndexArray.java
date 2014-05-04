/**
 * 
 */
package ak5.graphics;

import java.nio.ShortBuffer;

import ak5.util.io.BufferUtil;

/** @author pwnedary */
public class IndexArray implements IndexData {
	private ShortBuffer buffer;

	public IndexArray(int maxIndices) {
		buffer = (ShortBuffer) BufferUtil.newShortBuffer(maxIndices).flip();
	}

	@Override
	public int getNumIndices() {
		return buffer.limit();
	}

	@Override
	public void setIndices(short[] indices, int offset, int length) {
		((ShortBuffer) buffer.clear()).put(indices, offset, length).flip();
	}

	@Override
	public ShortBuffer getBuffer() {
		return buffer;
	}

	@Override
	public void bind() {}

	@Override
	public void unbind() {}

	@Override
	public void dispose() {}
}
