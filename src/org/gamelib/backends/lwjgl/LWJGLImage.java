/**
 * 
 */
package org.gamelib.backends.lwjgl;

import org.gamelib.graphics.Image;
import org.lwjgl.opengl.GL11;

/**
 * @author pwnedary
 * 
 */
public class LWJGLImage implements Image {
	/** The GL target type */
	public int target;
	/** The GL texture */
	public int texture;
	/** The height of the image */
	private int height;
	/** The width of the image */
	private int width;

	/**
	 * 
	 */
	public LWJGLImage(int target, int texture) {
		this.target = target = GL11.GL_TEXTURE_2D;
		this.texture = texture;
	}

	/**
	 * Binds the specified GL context to a texture.
	 * 
	 * @param gl The GL context to bind to
	 */
	public void bind() {
		GL11.glBindTexture(target, texture);
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.graphics.Image#getWidth() */
	@Override
	public int getWidth() {
		return width;
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.graphics.Image#getHeight() */
	@Override
	public int getHeight() {
		return height;
	}

}
