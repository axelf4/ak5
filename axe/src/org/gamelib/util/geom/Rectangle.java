/**
 * 
 */
package org.gamelib.util.geom;

import java.awt.Graphics;

/** A <code>Rectangle</code> specifies an area in a coordinate space that is enclosed by the <code>Rectangle</code>
 * object's upper-left point {@code (x,y)} in the coordinate space, its width, and its height.
 * 
 * @author pwnedary
 * @see java.awt.Rectangle */
public class Rectangle implements Shape {
	/** The X coordinate of the upper-left corner. */
	private int x;
	/** The Y coordinate of the upper-left corner. */
	private int y;
	/** The width of the <code>Rectangle</code>. */
	private int width;
	/** The width of the <code>Rectangle</code>. */
	private int height;

	public Rectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Rectangle() {
		this(0, 0, 0, 0);
	}

	@Override
	public boolean collides(Shape shape) {
		if (shape instanceof Rectangle) {
			Rectangle r = (Rectangle) shape;
			int w1 = x + width, w2 = r.x + r.width, h1 = y + height, h2 = r.y + r.height;
			return !(r.x > w1 || w2 < x || r.y > h1 || h2 < y);
		} else if (shape instanceof Point) {
			Point p = (Point) shape;
			return p.getX() >= x && p.getX() <= x + width && p.getY() >= y && p.getY() <= y + height;
		}
		return false;
	}

	@Override
	public void draw(Graphics g) {
		g.drawRect(x, y, width, height);
	}

	@Override
	public void translate(int deltaX, int deltaY) {
		// TODO Auto-generated method stub

	}

	@Override
	public Rectangle getBounds() {
		return this;
	}

	@Override
	public void rotate(double theta) {
		// TODO Auto-generated method stub

	}

	@Override
	public java.awt.Shape toAWT() {
		return new java.awt.Rectangle(x, y, width, height);
	}

	@Override
	public int[][] getPoints() {
		return new int[][] { { x, x + width }, { y, y + height } };
	}

	@Override
	public void fill(Graphics g) {
		g.fillRect(x, y, width, height);
	}

	/** @return the x */
	public int getX() {
		return x;
	}

	/** @param x the x to set */
	public void setX(int x) {
		this.x = x;
	}

	/** @return the y */
	public int getY() {
		return y;
	}

	/** @param y the y to set */
	public void setY(int y) {
		this.y = y;
	}

	/** @return the width */
	public int getWidth() {
		return width;
	}

	/** @param width the width to set */
	public void setWidth(int width) {
		this.width = width;
	}

	/** @return the height */
	public int getHeight() {
		return height;
	}

	/** @param height the height to set */
	public void setHeight(int height) {
		this.height = height;
	}

}
