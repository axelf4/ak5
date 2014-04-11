/**
 * 
 */
package org.gamelib.graphics;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.gamelib.util.io.BufferUtil;

/** @author pwnedary */
public class IndexBufferObject implements IndexData {
	final static IntBuffer tmpHandle = BufferUtil.newIntBuffer(1);

	final GL11 gl;
	ByteBuffer buffer;
	int bufferHandle;
	final boolean isDirect;
	boolean isDirty = true;
	boolean isBound = false;
	final int usage;

	/** Creates a new IndexBufferObject.
	 * 
	 * @param isStatic whether the index buffer is static
	 * @param maxIndices the maximum number of indices this buffer can hold */
	public IndexBufferObject(GL11 gl, boolean isStatic, int maxIndices) {
		this.gl = gl;
		buffer = (ByteBuffer) BufferUtil.newByteBuffer(maxIndices * 2).flip();
		isDirect = true;
		gl.glGenBuffers(1, tmpHandle);
		bufferHandle = tmpHandle.get(0);
		usage = isStatic ? GL11.GL_STATIC_DRAW : GL11.GL_DYNAMIC_DRAW;
	}

	@Override
	public int getNumIndices() {
		return buffer.limit();
	}

	@Override
	public void setIndices(byte[] indices, int offset, int length) {
		isDirty = true;
		buffer.clear();
		buffer.put(indices, offset, length);
		buffer.flip();

		if (isBound) gl.glBufferData(GL11.GL_ELEMENT_ARRAY_BUFFER, buffer.limit(), buffer, usage);
		isDirty = false;
	}

	@Override
	public ByteBuffer getBuffer() {
		isDirty = true;
		return buffer;
	}

	@Override
	public void bind() {
		if (bufferHandle == 0) throw new RuntimeException("No buffer allocated!");

		gl.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, bufferHandle);
		if (isDirty) {
			buffer.limit(buffer.limit() * 2);
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
