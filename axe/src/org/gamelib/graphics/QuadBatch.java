/**
 * 
 */
package org.gamelib.graphics;

import java.nio.FloatBuffer;

import org.gamelib.graphics.Attrib.Type;
import org.gamelib.util.geom.Matrix4;
import org.lwjgl.BufferUtils;

/** @author pwnedary */
public class QuadBatch implements Batch {
	private final GL10 gl;
	private Mesh mesh;

	private final float[] vertices;
	private int idx;
	private Texture lastTexture;

	private final Matrix4 transformMatrix = new Matrix4();
	public final Matrix4 projectionMatrix = new Matrix4();
	private final Matrix4 combinedMatrix = new Matrix4();
	private ShaderProgram shader;

	public QuadBatch(GL10 gl) {
		this(gl, 1000);
	}

	public QuadBatch(GL10 gl, int size) {
		this(gl, size, null);
	}

	public QuadBatch(GL10 gl, int size, ShaderProgram shader) {
		this.gl = gl;
		mesh = new Mesh(gl, false, size * 4, size * 6, //
		new Attrib(Type.POSITION, 2, ShaderProgram.POSITION_ATTRIBUTE), //
		new Attrib(Type.TEXTURE_COORDINATES, 2, ShaderProgram.TEXCOORD_ATTRIBUTE + '0'));
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

//		if (gl instanceof GL20) this.shader = shader != null ? shader : createDefaultShader((GL20) gl);
	}

	private static ShaderProgram createDefaultShader(GL20 gl) {
		//		String vertexShader = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
		//				+ "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
		//				+ "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
		//				+ "uniform mat4 u_projTrans;\n" //
		//				+ "varying vec4 v_color;\n" //
		//				+ "varying vec2 v_texCoords;\n" //
		//				+ "\n" //
		//				+ "void main()\n" //
		//				+ "{\n" //
		//				+ " v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
		//				+ " v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
		//				+ " gl_Position = u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
		//				+ "}\n";
		//		String fragmentShader = "#ifdef GL_ES\n" //
		//				+ "#define LOWP lowp\n" //
		//				+ "precision mediump float;\n" //
		//				+ "#else\n" //
		//				+ "#define LOWP \n" //
		//				+ "#endif\n" //
		//				+ "varying LOWP vec4 v_color;\n" //
		//				+ "varying vec2 v_texCoords;\n" //
		//				+ "uniform sampler2D u_texture;\n" //
		//				+ "void main()\n"//
		//				+ "{\n" //
		//				+ " gl_FragColor = v_color * texture2D(u_texture, v_texCoords);\n" //
		//				+ "}";
		String vertexShader = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
				+ "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
				+ "uniform mat4 u_projTrans;\n" //
				+ "varying vec2 v_texCoords;\n" //
				+ "\n" //
				+ "void main()\n" //
				+ "{\n" //
				+ " v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
				+ " gl_Position = u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
				+ "}\n";
		String fragmentShader = "#ifdef GL_ES\n" //
				+ "#define LOWP lowp\n" //
				+ "precision mediump float;\n" //
				+ "#else\n" //
				+ "#define LOWP \n" //
				+ "#endif\n" //
				+ "varying vec2 v_texCoords;\n" //
				+ "uniform sampler2D u_texture;\n" //
				+ "void main()\n"//
				+ "{\n" //
				+ " gl_FragColor = texture2D(u_texture, v_texCoords);\n" //
				+ "}";
		return new ShaderProgram(gl, vertexShader, fragmentShader);
	}

	public void begin() {
		gl.glDepthMask(false);
		gl.glEnable(GL10.GL_TEXTURE_2D);

		if (shader != null) shader.begin();
		setupMatrices();
	}

	public void end() {
		if (idx > 0) flush();
		lastTexture = null;

		gl.glDepthMask(true);
		gl.glDisable(GL10.GL_TEXTURE_2D);

		if (shader != null) shader.end();
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

		if (shader == null) {
			mesh.bind();
			mesh.render(GL10.GL_TRIANGLES, 0, count);
			mesh.unbind();
		} else {
			mesh.bind(shader);
			mesh.render(GL10.GL_TRIANGLES, 0, count);
			mesh.unbind(shader);
		}

		idx = 0;
	}

	private void switchTexture(Texture texture) {
		flush();
		lastTexture = texture;
	}

	private void setupMatrices() {
		combinedMatrix.set(projectionMatrix).mul(transformMatrix);
		if (shader != null) {
			shader.setUniformMatrix("u_projTrans", combinedMatrix);
			shader.setUniformi("u_texture", 0);
		} else {
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glLoadMatrix((FloatBuffer) BufferUtils.createFloatBuffer(combinedMatrix.data.length).put(combinedMatrix.data).flip());
			gl.glMatrixMode(GL10.GL_MODELVIEW);
		}
	}
}
