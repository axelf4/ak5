/**
 * 
 */
package org.gamelib.util.geom;

import java.awt.Graphics;

/** A point representing a location in {@code (x,y)} coordinate space.
 * 
 * @author pwnedary
 * @see java.awt.Point */
public class Point implements Shape {
	/** The X coordinate of this <code>Point</code>. */
	private int x;
	/** The Y coordinate of this <code>Point</code>. */
	private int y;

	private int deltaX, deltaY;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point() {
		this(0, 0);
	}

	@Override
	public boolean collides(Shape shape) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void translate(int deltaX, int deltaY) {
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}

	@Override
	public void rotate(double theta) {}

	/** Returns the X coordinate of this {@link Point}.
	 * 
	 * @return the X coordinate */
	public int getX() {
		return x + deltaX;
	}

	/** Returns the Y coordinate of this {@link Point}.
	 * 
	 * @return the Y coordinate */
	public int getY() {
		return y + deltaY;
	}

	@Override
	public void draw(Graphics g) {
		g.drawLine(getX(), getY(), getX(), getY());
	}

	@Override
	public void fill(Graphics g) {
		draw(g);
	}

	@Override
	public int[][] getPoints() {
		return new int[][] { { getX() }, { getY() } };
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), 1, 1);
	}

	@Override
	public java.awt.Shape toAWT() {
		return new java.awt.Rectangle(getX(), getY(), 1, 1); // return new java.awt.Point(getX(), getY());
	}

}
