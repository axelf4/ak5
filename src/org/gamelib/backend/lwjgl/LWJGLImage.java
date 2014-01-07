/**
 * 
 */
package org.gamelib.backend.lwjgl;

import static org.lwjgl.opengl.GL11.*;

import org.gamelib.backend.Graphics;
import org.gamelib.backend.Image;

/**
 * Textures are automatically made p^2 for compatibility.
 * @author pwnedary
 */
public class LWJGLImage implements Image {
	/** The GL target type */
	public int target;
	/** The GL texture */
	public int texture;
	/** The width of the image */
	private int width;
	/** The height of the image */
	private int height;
	public int texWidth;
	public int texHeight;

	public LWJGLImage(int target, int texture) {
		this.target = target = GL_TEXTURE_2D;
		this.texture = texture;
	}

	/**
	 * Binds the specified GL context to a texture.
	 * @param gl the GL context to bind to
	 */
	public void bind() {
		glBindTexture(target, texture);
	}

	public void unbind() {
		glBindTexture(target, 0);
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void setHeight(int height) {
		this.height = height;
	}

	public int getTextureID() {
		return texture;
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

	@Override
	public void dispose() {
		glDeleteTextures(texture);
	}

	@Override
	public void setFilter(Filter min, Filter mag) {
		bind();
		glTexParameteri(target, GL_TEXTURE_MIN_FILTER, min.getGLEnum());
		glTexParameteri(target, GL_TEXTURE_MAG_FILTER, mag.getGLEnum());
		unbind();
	}

	@Override
	public void setWrap(Wrap u, Wrap v) {
		bind();
		glTexParameteri(target, GL_TEXTURE_WRAP_S, u.getGLEnum());
		glTexParameteri(target, GL_TEXTURE_WRAP_T, v.getGLEnum());
		unbind();
	}
}
