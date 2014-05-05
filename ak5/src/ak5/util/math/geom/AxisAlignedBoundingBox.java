/**
 * 
 */
package ak5.util.math.geom;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * A bounding box which edges are parallel to the coordinate axes. (AABB)
 * 
 * @author pwnedary
 */
public class AxisAlignedBoundingBox {
	private int x, y, /** width of the box */
	width, /** height of the box */
	height;

	/**
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param width width of the box
	 * @param height height of the box
	 */
	public AxisAlignedBoundingBox(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * @param width width of the box
	 * @param height height of the box
	 */
	public AxisAlignedBoundingBox(int width, int height) {
		this(0, 0, width, height);
	}

	/**
	 * @param x the new x coordinate
	 * @param y the new y coordinate
	 * @return a new {@link Rectangle} reference at the given coordinates
	 */
	public Rectangle get(int x, int y) {
		this.x = x;
		this.y = y;
		return new Rectangle(x, y, width, height);
	}

	/**
	 * @return a new {@link Rectangle} reference
	 */
	public Rectangle get() {
		return new Rectangle(x, y, width, height);
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the new width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the new height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * draws an outline around the box, for debuggin'.
	 * 
	 * @param g2d the {@link Graphics2D} reference to draw on
	 */
	public void draw(Graphics g) {
		g.drawRect(x, y, width, height);
	}
	
	public void draw(Graphics g, int x, int y) {
		g.drawRect(x, y, width, height);
	}

}
