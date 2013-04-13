/**
 * 
 */
package org.gamelib.backends.lwjgl;

import java.nio.ByteBuffer;

import org.gamelib.graphics.Image;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 * @author pwnedary
 * 
 */
public class LWJGLImage implements Image {
	/** The GL target type */
	public int target;
	/** The GL texture */
	public int textureID;
	/** The height of the image */
	private int height;
	/** The width of the image */
	private int width;

	/**
	 * 
	 */
	public LWJGLImage(int target, int textureID) {
		this.target = target = GL11.GL_TEXTURE_2D;
		this.textureID = textureID;
		
		bind();
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);

		// The null at the end must be cast to tell javac which overload to use,
		// even though the last parameter (for the different overloads) isn't
		// even used
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, 800, 600, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
		unbind();
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
	
	public static Image empty() {
		LWJGLImage img = new LWJGLImage(GL11.GL_TEXTURE_2D, GL11.glGenTextures());
		img.width = 100;
		img.height = 100;
		return img;
	}
	
	/**
	 * Binds the specified GL context to a texture.
	 * 
	 * @param gl The GL context to bind to
	 */
	public void bind() {
		GL11.glBindTexture(target, textureID);
	}

	/**
	 * 
	 */
	public void unbind() {
		GL11.glBindTexture(target, 0);
	}

}
