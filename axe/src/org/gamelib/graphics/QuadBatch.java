/**
 * 
 */
package org.gamelib.graphics;

import java.nio.FloatBuffer;

import org.gamelib.graphics.Texture.GLTexture;
import org.gamelib.graphics.VertexAttribute.Type;
import org.gamelib.util.geom.Matrix4;
import org.gamelib.util.io.BufferUtil;

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
		new VertexAttribute(Type.POSITION, 2, ShaderProgram.POSITION_ATTRIBUTE), //
		new VertexAttribute(Type.TEXTURE_COORDINATES, 2, ShaderProgram.TEXCOORD_ATTRIBUTE + '0'));
		vertices = new float[size * 4];

		short[] indices = new short[size * 6];
		short j = 0;
		for (int i = 0; i < indices.length; i += 6, j += 4) {
			indices[i + 0] = j; // Left bottom triangle
			indices[i + 1] = (short) (j + 1);
			indices[i + 2] = (short) (j + 2);
			indices[i + 3] = (short) (j + 2); // Right top triangle
			indices[i + 4] = (short) (j + 3);
			indices[i + 5] = j;
		}
		mesh.setIndices(indices, 0, indices.length);

		this.shader = shader != null ? shader : createDefaultShader(gl);
	}

	private static ShaderProgram createDefaultShader(GL10 gl) {
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
		String vertexShader, fragmentShader;
		//		if (gl instanceof GL30) {
		//			vertexShader = "layout(location = 0) in vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
		//					+ "layout(location = 1) in vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
		//					+ "out vec2 UV;\n" //
		//					+ "uniform mat4 u_projTrans;\n" //
		//					+ "void main(){\n" //
		//					+ " gl_Position = u_projTrans * position;\n" //
		//					+ " UV = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
		//					+ "}\n";
		//			fragmentShader = "in vec2 UV;\n" //
		//					+ "out vec3 color;\n" //
		//					+ "uniform sampler2D u_texture;\n" //
		//					+ "void main(){\n" //
		//					+ " color = texture(texture, UV).rgb\n" //
		//					+ "}\n";
		//		} else
		if (gl instanceof GL20) {
			vertexShader = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
					+ "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
					+ "uniform mat4 u_projTrans;\n" //
					+ "varying vec2 v_texCoords;\n" //
					+ "void main(){\n" //
					+ " gl_Position = u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
					+ " v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
					+ "}\n";
			fragmentShader = "#ifdef GL_ES\n" //
					+ "precision highp float;\n" //
					+ "#endif\n" //
					+ "uniform sampler2D u_texture;\n" //
					+ "varying vec2 v_texCoords;\n" //
					+ "void main(){\n"//
					+ " gl_FragColor = texture2D(u_texture, v_texCoords);\n" //
					+ "}";
		} else return null;
		//		String vertexShader = "varying vec4 vertColor;\n" //
		//				+ "uniform mat4 u_projTrans;\n" //
		//				+ "\n" //
		//				+ "void main(){\n" //
		//				+ " gl_Position = u_projTrans * gl_Vertex;\n" //
		//				+ " vertColor = vec4(0.2, 0.6, 0.6, 1.0);\n" //
		//				+ "}\n";
		//		String fragmentShader = "varying vec4 vertColor;\n" //
		//				+ "void main(){\n"//
		//				+ " gl_FragColor = vertColor;\n" //
		//				+ "}";
		return new ShaderProgram((GL20) gl, vertexShader, fragmentShader);
	}

	@Override
	public void begin() {
		gl.glDepthMask(false);
		if (shader != null) shader.begin();
		else gl.glEnable(GL10.GL_TEXTURE_2D);
		setupMatrices();
	}

	@Override
	public void end() {
		if (idx > 0) flush();
		lastTexture = null;

		gl.glDepthMask(true);
		if (shader != null) shader.end();
		else gl.glDisable(GL10.GL_TEXTURE_2D);
	}

	@Override
	public void draw(Texture texture, float x1, float y1, float x2, float y2) {
		if (texture != lastTexture) switchTexture(texture);
		else if (idx == vertices.length) flush();
		vertices[idx++] = x1;
		vertices[idx++] = y2;
		vertices[idx++] = texture.getU();
		vertices[idx++] = texture.getV2();

		vertices[idx++] = x1;
		vertices[idx++] = y1;
		vertices[idx++] = texture.getU();
		vertices[idx++] = texture.getV();

		vertices[idx++] = x2;
		vertices[idx++] = y1;
		vertices[idx++] = texture.getU2();
		vertices[idx++] = texture.getV();

		vertices[idx++] = x2;
		vertices[idx++] = y2;
		vertices[idx++] = texture.getU2();
		vertices[idx++] = texture.getV2();
	}

	@Override
	public void draw(Texture texture, float dx1, float dy1, float dx2, float dy2, float sx1, float sy1, float sx2, float sy2) {
		if (texture != lastTexture) switchTexture(texture);
		else if (idx == vertices.length) flush();

		final float u = (float) sx1 / ((GLTexture) texture).getTexWidth() + texture.getU();
		final float v = (float) sy1 / ((GLTexture) texture).getTexHeight() + texture.getV();
		final float u2 = (float) sx2 / ((GLTexture) texture).getTexWidth() + texture.getU();
		final float v2 = (float) sy2 / ((GLTexture) texture).getTexHeight() + texture.getV();

		vertices[idx++] = dx1;
		vertices[idx++] = dy1;
		vertices[idx++] = u;
		vertices[idx++] = v;

		vertices[idx++] = dx1;
		vertices[idx++] = dy2;
		vertices[idx++] = u;
		vertices[idx++] = v2;

		vertices[idx++] = dx2;
		vertices[idx++] = dy2;
		vertices[idx++] = u2;
		vertices[idx++] = v2;

		vertices[idx++] = dx2;
		vertices[idx++] = dy1;
		vertices[idx++] = u2;
		vertices[idx++] = v;
	}

	@Override
	public void flush() {
		if (idx == 0) return;
		int spritesInBatch = idx / 16;
		int count = spritesInBatch * 6;

		gl.glActiveTexture(GL10.GL_TEXTURE0);
		lastTexture.bind();
		mesh.setVertices(vertices, 0, idx);
		mesh.getIndices().getBuffer().position(0).limit(count);

		if (shader != null) {
			mesh.bind(shader);
			mesh.render(GL10.GL_TRIANGLES, 0, count);
			mesh.unbind(shader);
		} else {
			mesh.bind();
			mesh.render(GL10.GL_TRIANGLES, 0, count);
			mesh.unbind();
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
			gl.glLoadMatrixf((FloatBuffer) BufferUtil.newFloatBuffer(combinedMatrix.data.length * 4).put(combinedMatrix.data).flip());
			gl.glMatrixMode(GL10.GL_MODELVIEW);
		}
	}
}
