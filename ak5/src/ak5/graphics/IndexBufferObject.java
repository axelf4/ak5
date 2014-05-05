/**
 * 
 */
package ak5.graphics;

import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import ak5.util.io.BufferUtil;

/** @author pwnedary */
public class IndexBufferObject implements IndexData {
	final static IntBuffer tmpHandle = BufferUtil.newIntBuffer(1);

	final GL11 gl;
	ShortBuffer buffer;
	int bufferHandle;
	boolean isDirty = true;
	boolean isBound = false;
	final int usage;

	/** Creates a new IndexBufferObject.
	 * 
	 * @param isStatic whether the index buffer is static
	 * @param maxIndices the maximum number of indices this buffer can hold */
	public IndexBufferObject(GL11 gl, boolean isStatic, int maxIndices) {
		this.gl = gl;
		buffer = (ShortBuffer) BufferUtil.newShortBuffer(maxIndices).flip();
		gl.glGenBuffers(1, tmpHandle);
		bufferHandle = tmpHandle.get(0);
		usage = isStatic ? GL11.GL_STATIC_DRAW : GL11.GL_DYNAMIC_DRAW;
	}

	@Override
	public int getNumIndices() {
		return buffer.limit();
	}

	@Override
	public void setIndices(short[] indices, int offset, int length) {
		((ShortBuffer)buffer.clear()).put(indices, offset, length).flip();
		isDirty = true;
	}

	@Override
	public ShortBuffer getBuffer() {
		isDirty = true;
		return buffer;
	}

	@Override
	public void bind() {
		gl.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, bufferHandle);
		if (isDirty) {
			gl.glBufferData(GL11.GL_ELEMENT_ARRAY_BUFFER, buffer.limit(), buffer, usage);
			isDirty = false;
		}
		isBound = true;
	}

	@Override
	public void unbind() {
		gl.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 0);
		isBound = false;
	}

	@Override
	public void dispose() {
		gl.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 0);
		gl.glDeleteBuffers(1, (IntBuffer) ((IntBuffer) tmpHandle.clear()).put(bufferHandle).flip());
		bufferHandle = 0;
	}
}
