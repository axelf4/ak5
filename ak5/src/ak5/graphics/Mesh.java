/**
 * 
 */
package ak5.graphics;

import java.nio.ShortBuffer;

import ak5.util.Disposable;

/** @author pwnedary */
public class Mesh implements Disposable {
	private final GL10 gl;
	private final VertexData vertices;
	private final IndexData indices;

	public Mesh(GL10 gl, boolean isStatic, int maxVertices, int maxIndices, VertexAttribute... attributes) {
		this.gl = gl;
		if (gl instanceof GL20) { // VertexBufferObject
			vertices = new VertexBufferObject((GL20) gl, isStatic, maxVertices, attributes);
			indices = new IndexBufferObject((GL20) gl, isStatic, maxIndices);
		} else { // Vertex array
			vertices = new VertexArray(gl, maxVertices, attributes);
			indices = new IndexArray(maxIndices);
		}
	}

	public void setVertices(float[] vertices, int offset, int length) {
		this.vertices.setVertices(vertices, offset, length);
	}

	public IndexData getIndices() {
		return indices;
	}

	public void setIndices(short[] indices, int offset, int length) {
		this.indices.setIndices(indices, offset, length);
	}

	public void bind() {
		vertices.bind();
		if (!(vertices instanceof VertexArray) && indices.getNumIndices() > 0) indices.bind();
	}

	/** Binds the underlying {@link VertexBufferObject} and {@link IndexBufferObject} if indices where given. Use this
	 * with OpenGL ES 2.0.
	 *
	 * @param shader the shader (does not bind the shader) */
	public void bind(final ShaderProgram shader) {
		bind(shader, null);
	}

	/** Binds the underlying {@link VertexBufferObject} and {@link IndexBufferObject} if indices where given. Use this
	 * with OpenGL ES 2.0.
	 *
	 * @param shader the shader (does not bind the shader)
	 * @param locations array containing the attribute locations. */
	public void bind(final ShaderProgram shader, final int[] locations) {
		vertices.bind(shader, locations);
		if (indices.getNumIndices() > 0) indices.bind();
	}

	public void unbind() {
		vertices.unbind();
		if (!(vertices instanceof VertexArray) && indices.getNumIndices() > 0) indices.unbind();
	}

	/** Unbinds the underlying {@link VertexBufferObject} and {@link IndexBufferObject} is indices were given. Use this
	 * with OpenGL ES 1.x.
	 *
	 * @param shader the shader (does not unbind the shader) */
	public void unbind(final ShaderProgram shader) {
		unbind(shader, null);
	}

	/** Unbinds the underlying {@link VertexBufferObject} and {@link IndexBufferObject} is indices were given. Use this
	 * with OpenGL ES 1.x.
	 *
	 * @param shader the shader (does not unbind the shader)
	 * @param locations array containing the attribute locations. */
	public void unbind(final ShaderProgram shader, final int[] locations) {
		vertices.unbind(shader, locations);
		if (indices.getNumIndices() > 0) indices.unbind();
	}

	public void render(int mode, int first, int count) {
		if (indices.getNumIndices() > 0) {
			if (vertices instanceof VertexArray) {
				ShortBuffer buffer = indices.getBuffer();
				int oldPosition = buffer.position();
				int oldLimit = buffer.limit();
				buffer.position(first);
				buffer.limit(first + count);
				gl.glDrawElements(mode, count, GL10.GL_UNSIGNED_BYTE, buffer);
				buffer.position(oldPosition);
				buffer.limit(oldLimit);
			} else ((GL11) gl).glDrawElements(mode, count, GL20.GL_UNSIGNED_SHORT, first * 2);
		} else gl.glDrawArrays(mode, first, count);
	}

	public enum VertexDataType {
		VERTEX_ARRAY, VERTEX_BUFFER_OBJECT;
	}

	@Override
	public void dispose() {
		vertices.dispose();
		indices.dispose();
	}
}
