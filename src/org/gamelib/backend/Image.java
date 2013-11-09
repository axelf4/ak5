/**
 * 
 */
package org.gamelib.backend;

/**
 * An generic image, which can be drawn by {@link Graphics#drawImage(Image, int, int, int, int, int, int, int, int)}.
 * @author pwnedary
 */
public interface Image {
	/** @return the width of the image */
	public int getWidth();

	/** Sets the width of the image. */
	public void setWidth(int width);

	/** @return the height of the image */
	public int getHeight();

	/** Sets the height of the image. */
	public void setHeight(int height);
}
