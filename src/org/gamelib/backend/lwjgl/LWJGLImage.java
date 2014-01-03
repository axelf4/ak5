/**
 * 
 */
package org.gamelib.backend.lwjgl;

import org.gamelib.backend.Graphics;
import org.gamelib.backend.Image;
import org.lwjgl.opengl.GL11;

/**
 * Textures are automatically made p^2 for speed and compatibility.
 * @author pwnedary
 */
public class LWJGLImage implements Image {
	/** The GL target type */
	public int target;
	/** The GL texture */
	public int textureID;
	/** The width of the image */
	private int width;
	/** The height of the image */
	private int height;
	public int texWidth;
	public int texHeight;

	/**
	 * 
	 */
	public LWJGLImage(int target, int textureID) {
		this.target = target = GL11.GL_TEXTURE_2D;
		this.textureID = textureID;

		/*
		 * bind(); GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE); GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE); GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR); GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR); // The null at the end must be cast to tell javac which overload to use, // even though the last parameter (for the different overloads) isn't // even used GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, 800, 600, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null); unbind();
		 */
	}

	/**
	 * Binds the specified GL context to a texture.
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

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.graphics.Image#getWidth()
	 */
	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public void setWidth(int width) {
		this.width = width;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.graphics.Image#getHeight()
	 */
	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void setHeight(int height) {
		this.height = height;
	}

	public int getTextureID() {
		return textureID;
	}

	/**
	 * @return the texWidth
	 */
	public int getTexWidth() {
		return texWidth;
	}

	/**
	 * @param texWidth the texWidth to set
	 */
	public void setTexWidth(int texWidth) {
		this.texWidth = texWidth;
	}

	/**
	 * @return the texHeight
	 */
	public int getTexHeight() {
		return texHeight;
	}

	/**
	 * @param texHeight the texHeight to set
	 */
	public void setTexHeight(int texHeight) {
		this.texHeight = texHeight;
	}

	@Override
	public void draw(Graphics g, float delta) {
		g.drawImage(this, 0, 0, 0 + getWidth(), 0 + getHeight(), 0, 0, 0 + getWidth(), 0 + getHeight());
	}

}
