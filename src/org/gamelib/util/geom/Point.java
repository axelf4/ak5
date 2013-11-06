/**
 * 
 */
package org.gamelib.util.geom;

import org.gamelib.backend.Graphics;

/**
 * @author pwnedary
 */
public class Point implements Shape {

	/** The X coordinate. */
	public int x;
	/** The Y coordinate. */
	public int y;

	private int deltaX, deltaY;

	/**
	 * 
	 */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * 
	 */
	public Point() {
		this(0, 0);
	}

	/** {@inheritDoc} */
	@Override
	public boolean collides(Shape shape) {
		throw new UnsupportedOperationException();
	}

	/** {@inheritDoc} */
	@Override
	public void translate(int deltaX, int deltaY) {
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}

	/** {@inheritDoc} */
	@Override
	public void rotate(double theta) {}

	/**
	 * @return the x
	 */
	public int getX() {
		return x + deltaX;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y + deltaY;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/** {@inheritDoc} */
	@Override
	public void draw(Graphics g) {
		g.drawLine(getX(), getY(), getX(), getY());
	}

	/** {@inheritDoc} */
	@Override
	public void fill(Graphics g) {
		draw(g);
	}
	
	/** {@inheritDoc} */
	@Override
	public int[][] getPoints() {
		return new int[][] { { x }, { y } };
	}
	
	/** {@inheritDoc} */
	@Override
	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), 1, 1);
	}

	/** {@inheritDoc} */
	@Override
	public java.awt.Shape toAWT() {
		return new java.awt.Rectangle(getX(), getY(), 1, 1);
	}

}
