/**
 * 
 */
package org.gamelib.backend;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.*;

import org.gamelib.Drawable;
import org.gamelib.util.Disposable;

/**
 * A generic in-memory image, which can be drawn by {@link Graphics#drawImage(Image, int, int, int, int, int, int, int, int)}.
 * 
 * @author pwnedary
 */
public interface Image extends Drawable, Disposable {
	enum Filter {
		NEAREST(GL_NEAREST), LINEAR(GL_LINEAR), MIPMAP(GL_LINEAR_MIPMAP_LINEAR), LINEAR_MIPMAP_LINEAR(
				GL_LINEAR_MIPMAP_LINEAR), NEAREST_MIPMAP_NEAREST(
				GL_NEAREST_MIPMAP_NEAREST), LINEAR_MIPMAP_NEAREST(
				GL_LINEAR_MIPMAP_NEAREST), NEAREST_MIPMAP_LINEAR(
				GL_NEAREST_MIPMAP_LINEAR);

		final int glEnum;

		private Filter(int glEnum) {
			this.glEnum = glEnum;
		}

		public int getGLEnum() {
			return glEnum;
		}

		public boolean isMipMap() {
			return glEnum != GL_NEAREST && glEnum != GL_LINEAR;
		}
	}

	enum Wrap {
		CLAMP(GL_CLAMP), REPEAT(GL_REPEAT), MIRRORED_REPEAT(GL_MIRRORED_REPEAT);

		final int glEnum;

		Wrap(int glEnum) {
			this.glEnum = glEnum;
		}

		public int getGLEnum() {
			return glEnum;
		}
	}

	/**
	 * Returns the width of this image.
	 * 
	 * @return the width of this image
	 */
	int getWidth();

	/** Sets the width of this image. */
	void setWidth(int width);

	/**
	 * Returns the height of this image.
	 * 
	 * @return the height of this image
	 */
	int getHeight();

	/** Sets the height of this image. */
	void setHeight(int height);

	/**
	 * Sets the {@link Filter} for minification and magnification.
	 * 
	 * @param minFilter the minification filter
	 * @param magFilter the magnification filter
	 */
	void setFilter(Filter min, Filter mag);

	/**
	 * Sets the {@link Wrap} on the u and v axis.
	 * 
	 * @param u the u wrap
	 * @param v the v wrap
	 */
	void setWrap(Wrap u, Wrap v);

	/**
	 * Returns a 32-bit RGBA value of the pixel at (<code>x,y</code>), (<code>0,0</code> being top-left).
	 * 
	 * @param x the x-coordinate of the pixel
	 * @param y the y-coordinate of the pixel
	 * @return the color at x,y
	 */
	int getPixel(int x, int y);

	/**
	 * Returns a subimage as defined by the specified region, that shares the same content as this {@link Image}.
	 * 
	 * @param x the x-coordinate of the upper-left corner of the rectangular region
	 * @param y the y-coordinate of the upper-left corner of the rectangular region
	 * @param width the width of the rectangular region
	 * @param height the height of the rectangular region
	 * @return an Image that is a subimage of this Image
	 */
	Image region(int x, int y, int width, int height);
}
