/**
 * 
 */
package org.gamelib.graphics;

import java.nio.ByteBuffer;

/** @author pwnedary */
public class Mesh {
	private final GL10 gl;
	private final VertexData vertices;
	private final IndexData indices;

	public Mesh(GL10 gl, boolean isStatic, int maxVertices, int maxIndices, Attrib... attributes) {
		this.gl = gl;
		// if (gl instanceof GL11 || gl instanceof GL20)){
		// 	vertices = null; // VBO
		// } else {
		vertices = new VertexArray(gl, maxVertices, attributes); // VAO
		indices = new IndexArray(maxIndices);
		// }
	}

	public void setVertices(float[] vertices, int offset, int length) {
		this.vertices.setVertices(vertices, offset, length);
	}

	public IndexData getIndices() {
		return indices;
	}

	public void setIndices(byte[] indices, int offset, int length) {
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
		if (vertices instanceof VertexArray) if (indices.getNumIndices() > 0) {
			ByteBuffer buffer = indices.getBuffer();
			int oldPosition = buffer.position();
			int oldLimit = buffer.limit();
			buffer.position(first);
			buffer.limit(first + count);
			gl.glDrawElements(mode, count, GL10.GL_UNSIGNED_BYTE, buffer);
			buffer.position(oldPosition);
			buffer.limit(oldLimit);
		} else gl.glDrawArrays(mode, first, count);
	}

	public enum VertexDataType {
		VERTEX_ARRAY, VERTEX_BUFFER_OBJECT;
	}
}
