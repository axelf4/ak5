/**
 * 
 */
package org.gamelib.graphics;

import java.nio.IntBuffer;

/** @author Axel */
public class FrameBufferObject {
	private GL20 gl;
	private int framebuffer;

	public FrameBufferObject(GL20 gl, Texture texture) {
		this.gl = gl;

		IntBuffer buffer = IntBuffer.allocate(1);
		gl.glGenFramebuffers(1, buffer);
		framebuffer = buffer.get(0);

		bind();
		// texture.bind();
		gl.glFramebufferTexture2D(GL20.GL_FRAMEBUFFER, GL20.GL_COLOR_ATTACHMENT0, texture.getTarget(), texture.getTexture(), 0);
		// texture.unbind();
		unbind();
	}

	public void bind() {
		gl.glBindFramebuffer(GL20.GL_FRAMEBUFFER, framebuffer);
	}

	public void unbind() {
		gl.glBindFramebuffer(GL20.GL_FRAMEBUFFER, 0);
	}
}
