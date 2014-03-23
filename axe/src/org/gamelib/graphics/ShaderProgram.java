/**
 * 
 */
package org.gamelib.graphics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import org.gamelib.util.Disposable;

/** @author pwnedary */
public class ShaderProgram implements Disposable {
	private GL20 gl;
	/** The OpenGL handle for this shader program object. */
	private int program;
	private int vert;
	private int frag;

	private IntBuffer buffer = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).asIntBuffer();

	public ShaderProgram(GL20 gl, String vertexShader, String fragmentShader) {
		this.gl = gl;
		if (vertexShader == null) throw new IllegalArgumentException("vertexShader can't be null");
		if (fragmentShader == null) throw new IllegalArgumentException("fragmentShader can't be null");

		vert = compileShader(GL20.GL_VERTEX_SHADER, vertexShader);
		frag = compileShader(GL20.GL_FRAGMENT_SHADER, fragmentShader);
		if (vert == 0 || frag == 0) return;

		program = gl.glCreateProgram();
		if (program == 0) return;

		gl.glAttachShader(program, vert);
		gl.glAttachShader(program, frag);
		gl.glLinkProgram(program);

		gl.glGetProgramiv(program, GL20.GL_LINK_STATUS, buffer);
		if (buffer.get(0) == 0) return;

		gl.glValidateProgram(program);
		gl.glGetProgramiv(program, GL20.GL_VALIDATE_STATUS, buffer);
		if (buffer.get(0) == 0) return;
	}

	private int compileShader(int type, String source) {
		int shader = gl.glCreateShader(type);

		gl.glShaderSource(shader, 0, source, null);
		gl.glCompileShader(shader);

		gl.glGetShaderiv(shader, GL20.GL_COMPILE_STATUS, buffer);
		if (buffer.get(0) == 0) return -1;

		return shader;
	}

	public String getLog() {
		// return gl.glGetProgramInfoLog(program, 0, null, null); // FIXME
		return null;
	}

	public void begin() {
		gl.glUseProgram(program);
	}

	public void end() {
		gl.glUseProgram(0);
	}

	@Override
	public void dispose() {
		gl.glUseProgram(0);
		gl.glDeleteShader(vert);
		gl.glDeleteShader(frag);
		gl.glDeleteProgram(program);
	}
}
