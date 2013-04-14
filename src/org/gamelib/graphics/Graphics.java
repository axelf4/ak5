/**
 * 
 */
package org.gamelib.graphics;

import java.awt.Color;

/**
 * TODO add methods for starting and stopping drawing
 * @author pwnedary
 * 
 */
public interface Graphics {

	public void setColor(Color c);

	/**
	 * Draws the specified image to the backend.
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
	
	/* replace with fonts and delete */
	public void drawString(String str, int x, int y);
	
	// public void clear();
	
	public void begin();
	public void end();

}
