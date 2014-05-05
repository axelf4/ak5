/**
 * 
 */
package ak5.graphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import ak5.graphics.VertexAttribute.Type;
import ak5.util.io.BufferUtil;

/** @author pwnedary */
public class VertexBufferObject implements VertexData {
	private static final IntBuffer tmpHandle = BufferUtil.newIntBuffer(1);

	private final GL20 gl;
	private final VertexAttribute[] attributes;
	private final int vertexSize;
	private final FloatBuffer buffer;
	private final int bufferHandle;
	private final int type;
	private boolean isBound = false;
	private boolean isDirty = false;

	/** Constructs a new interleaved VertexBufferObject.
	 * 
	 * @param isStatic whether the vertex data is static.
	 * @param numVertices the maximum number of vertices
	 * @param attributes the {@link VertexAttribute}s. */
	public VertexBufferObject(GL20 gl, boolean isStatic, int numVertices, VertexAttribute... attributes) {
		this.gl = gl;
		vertexSize = VertexAttribute.calculateOffsets(this.attributes = attributes);

		buffer = (FloatBuffer) BufferUtil.newByteBuffer(vertexSize * numVertices).asFloatBuffer().flip();
		gl.glGenBuffers(1, tmpHandle);
		bufferHandle = tmpHandle.get(0);
		type = isStatic ? GL20.GL_STATIC_DRAW : GL20.GL_DYNAMIC_DRAW;
	}

	@Override
	public int getNumVertices() {
		return buffer.limit() * 4 / vertexSize;
	}

	@Override
	public void setVertices(float[] vertices, int offset, int count) {
		if (isBound) throw new RuntimeException("Already bound.");
		buffer.clear();
		((FloatBuffer) buffer.clear()).put(vertices, offset, count).flip();
		isDirty = true;
	}

	//	@Override
	//	public void updateVertices(int targetOffset, float[] vertices, int sourceOffset, int count) {
	//		isDirty = true;
	//		final int pos = byteBuffer.position();
	//		byteBuffer.position(targetOffset * 4);
	//		BufferUtils.copy(vertices, sourceOffset, count, byteBuffer);
	//		byteBuffer.position(pos);
	//		buffer.position(0);
	//		bufferChanged();
	//	}

	@Override
	public void bind() {
		throw new UnsupportedOperationException();
	}

	/** Binds this VertexBufferObject for rendering via glDrawArrays or glDrawElements
	 * 
	 * @param shader the shader */
	@Override
	public void bind(ShaderProgram shader, int[] locations) {
		gl.glBindBuffer(GL20.GL_ARRAY_BUFFER, bufferHandle);
		if (isDirty) {
			gl.glBufferData(GL20.GL_ARRAY_BUFFER, buffer.limit() * 4, buffer, type);
			isDirty = false;
		}

		for (int i = 0; i < attributes.length; i++) {
			final VertexAttribute attribute = attributes[i];
			final int location = locations == null ? shader.getAttribLocation(attribute.name) : locations[i];
			if (location < 0) continue;
			gl.glEnableVertexAttribArray(location);

			if (attribute.type == Type.COLOR_PACKED) gl.glVertexAttribPointer(location, attribute.numComponents, GL20.GL_UNSIGNED_BYTE, true, vertexSize, attribute.location);
			else gl.glVertexAttribPointer(location, attribute.numComponents, GL20.GL_FLOAT, false, vertexSize, attribute.location);
		}
		isBound = true;
	}

	@Override
	public void unbind() {
		throw new UnsupportedOperationException();
	}

	/** Unbinds this VertexBufferObject.
	 * 
	 * @param shader the shader */
	@Override
	public void unbind(final ShaderProgram shader, final int[] locations) {
		for (int i = 0; i < attributes.length; i++)
			if (locations == null) ((GL20) gl).glEnableVertexAttribArray(shader.getAttribLocation(attributes[i].name));
			else gl.glDisableVertexAttribArray(locations[i]);
		gl.glBindBuffer(GL20.GL_ARRAY_BUFFER, 0);
		isBound = false;
	}

	/** Disposes of all resources this VertexBufferObject uses. */
	@Override
	public void dispose() {
		((IntBuffer) tmpHandle.clear()).put(bufferHandle).flip();
		gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
		gl.glDeleteBuffers(1, tmpHandle);
	}
}
