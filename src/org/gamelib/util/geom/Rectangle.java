/**
 * 
 */
package org.gamelib.util.geom;

import java.awt.Graphics2D;

/**
 * A <code>Rectangle</code> specifies an area in a coordinate space that is
 * enclosed by the <code>Rectangle</code> object's upper-left point
 * {@code (x,y)} in the coordinate space, its width, and its height.
 * 
 * @author pwnedary
 * @see java.awt.Rectangle
 */
public class Rectangle implements Shape {

	public int /** The X coordinate of the upper-left corner. */
	x, /** The Y coordinate of the upper-left corner. */
	y, /** The width of the <code>Rectangle</code>. */
	width, /** The width of the <code>Rectangle</code>. */
	height;

	/**
	 * 
	 */
	public Rectangle() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public Rectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.util.Shape#collides(org.gamelib.util.Shape) */
	@Override
	public boolean collides(Shape shape) {
		Rectangle r = (Rectangle) shape;
		int w1 = x + width, w2 = r.x + r.width, h1 = y + height, h2 = r.y + r.height;
		return !(r.x > w1 || w2 < x || r.y > h1 || h2 < y);
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.util.Shape#draw(java.awt.Graphics2D) */
	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.util.Shape#translate(int, int) */
	@Override
	public void translate(int deltaX, int deltaY) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.util.Shape#getBounds() */
	@Override
	public Rectangle getBounds() {
		return this;
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.util.Shape#rotate(double) */
	@Override
	public void rotate(double theta) {
		// TODO Auto-generated method stub

	}

}
