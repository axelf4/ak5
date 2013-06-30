/**
 * 
 */
package org.gamelib.backend;

import org.gamelib.util.Color;

/**
 * TODO add methods for starting and stopping drawing
 * 
 * @author pwnedary
 */
public interface Graphics {

	/** Releases any system resources that is used. */
	public void dispose();
	
	/** Sets the current color. */
	public void setColor(Color c);
	
	/** Translates the origin to <i>x</i>,&nbsp;<i>y</i>. */
	public void translate(float x, float y);
	/** Translates the origin to <i>x</i>,&nbsp;<i>y</i>,&nbsp;<i>z</i>. */
	public void translate(float x, float y, float z);
	
	// public void rotate(int theta);

	/**
	 * Draws the specified image to the backend.
	 * 
	 * @param img the image to be drawn
	 * @param dx1
	 * @param dy1
	 * @param dx2
	 * @param dy2
	 * @param sx1
	 * @param sy1
	 * @param sx2
	 * @param sy2
	 */
	public void drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2);

	public void drawLine(int x1, int y1, int x2, int y2);

	public void drawRect(int x, int y, int width, int height);

	public void fillRect(int x, int y, int width, int height);
	
	public void drawCube(int x, int y, int z, int width, int height, int depth);

	/** Clears the screen in the desired color. */
	public void clear();

}
