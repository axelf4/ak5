/**
 * 
 */
package org.gamelib.backend;

/**
 * An generic in-memory image, which can be drawn by {@link Graphics#drawImage(Image, int, int, int, int, int, int, int, int)}.
 * @author pwnedary
 */
public interface Image {
	/**
	 * Returns the width of this image.
	 * @return the width of this image
	 */
	public int getWidth();

	/** Sets the width of this image. */
	public void setWidth(int width);

	/**
	 * Returns the height of this image.
	 * @return the height of this image
	 */
	public int getHeight();

	/** Sets the height of this image. */
	public void setHeight(int height);
}
