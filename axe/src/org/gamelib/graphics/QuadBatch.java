/**
 * 
 */
package org.gamelib.graphics;

import org.gamelib.graphics.VertexAttrib.Type;

/** @author pwnedary */
public class QuadBatch implements Batch {
	private final GL10 gl;
	private Mesh mesh;
	private int idx;
	private final float[] vertices;

	private Texture lastTexture;

	public QuadBatch(GL10 gl) {
		this(gl, 1000);
	}

	public QuadBatch(GL10 gl, int size) {
		this.gl = gl;
		mesh = new Mesh(gl, false, size * 4, size * 6, new VertexAttrib(Type.POSITION, 2), new VertexAttrib(Type.TEXTURE_COORDINATES, 2));
		//		mesh = new Mesh(gl, false, size * 4, 0, new VertexAttrib(Type.POSITION, 2), new VertexAttrib(Type.TEXTURE_COORDINATES, 2));
		vertices = new float[size * 4];

		byte[] indices = new byte[size * 6];
		byte j = 0;
		for (int i = 0; i < indices.length; i += 6, j += 4) {
			indices[i + 0] = j; // Left bottom triangle
			indices[i + 1] = (byte) (j + 1);
			indices[i + 2] = (byte) (j + 2);
			indices[i + 3] = (byte) (j + 2); // Right top triangle
			indices[i + 4] = (byte) (j + 3);
			indices[i + 5] = j;
		}
		mesh.setIndices(indices, 0, indices.length);
	}

	public void begin() {
		gl.glDepthMask(false);
		gl.glEnable(GL10.GL_TEXTURE_2D);

		idx = 0;
		lastTexture = null;
	}

	public void end() {
		if (idx > 0) flush();

		gl.glDepthMask(true);
		gl.glDisable(GL10.GL_TEXTURE_2D);
	}

	public void draw(Texture texture, float x1, float y1, float x2, float y2) {
		if (texture != lastTexture) switchTexture(texture);
		else if (idx == vertices.length) flush();

		final float u = 0.0f;
		final float v = 0.0f;
		final float u2 = 1.0f;
		final float v2 = 1.0f;
		
		vertices[idx++] = x1;
		vertices[idx++] = y1;
		vertices[idx++] = u;
		vertices[idx++] = v;

		vertices[idx++] = x1;
		vertices[idx++] = y2;
		vertices[idx++] = u;
		vertices[idx++] = v2;

		vertices[idx++] = x2;
		vertices[idx++] = y2;
		vertices[idx++] = u2;
		vertices[idx++] = v2;

		vertices[idx++] = x2;
		vertices[idx++] = y1;
		vertices[idx++] = u2;
		vertices[idx++] = v;
	}

	public void flush() {
		if (idx == 0) return;
		int spritesInBatch = idx / 16;
		int count = spritesInBatch * 6;

		lastTexture.bind();
		mesh.setVertices(vertices, 0, idx);
		mesh.getIndices().getBuffer().position(0).limit(count);

		mesh.bind();
		mesh.render(GL10.GL_TRIANGLES, 0, count);
		mesh.unbind();

		idx = 0;
	}

	private void switchTexture(Texture texture) {
		flush();
		lastTexture = texture;
	}
}
