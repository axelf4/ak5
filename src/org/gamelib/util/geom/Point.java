/**
 * 
 */
package org.gamelib.util.geom;

import org.gamelib.Graphics;

/**
 * @author pwnedary
 *
 */
public class Point implements Shape {
	
	/** The X coordinate. */
	private int x;
	/** The Y coordinate. */
	private int y;
	
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

	/* (non-Javadoc)
	 * @see org.gamelib.util.geom.Shape#collides(org.gamelib.util.geom.Shape)
	 */
	@Override
	public boolean collides(Shape shape) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.gamelib.util.geom.Shape#translate(int, int)
	 */
	@Override
	public void translate(int deltaX, int deltaY) {
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}

	/* (non-Javadoc)
	 * @see org.gamelib.util.geom.Shape#rotate(double)
	 */
	@Override
	public void rotate(double theta) {
	}

	/* (non-Javadoc)
	 * @see org.gamelib.util.geom.Shape#getBounds()
	 */
	@Override
	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), 1, 1);
	}

	/* (non-Javadoc)
	 * @see org.gamelib.util.geom.Shape#draw(org.gamelib.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub

	}

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

	/* (non-Javadoc)
	 * @see org.gamelib.util.geom.Shape#toAWT()
	 */
	@Override
	public java.awt.Shape toAWT() {
		return new java.awt.Rectangle(getX(), getY(), 1, 1);
	}

}
