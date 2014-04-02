/**
 * 
 */
package org.gamelib.util.geom;

import java.awt.Graphics;
import java.awt.geom.Line2D;

/** A <code>Line</code> represents a line segment in {@code (x,y)} coordinate space.
 * 
 * @author pwnedary
 * @see Line2D */
public class Line implements Shape {
	/** The X coordinate of the start point of the line segment. */
	private int x1;
	/** The Y coordinate of the start point of the line segment. */
	private int y1;
	/** The X coordinate of the end point of the line segment. */
	private int x2;
	/** The Y coordinate of the end point of the line segment. */
	private int y2;
	private int x, y;

	public Line(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	@Override
	public boolean collides(Shape shape) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void translate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void rotate(double theta) {
		Point center = getCenter();
		int xCenter = center.getX();
		int yCenter = center.getY();
		x1 = (int) (xCenter + Math.cos(theta) * (x1 - xCenter) - Math.sin(theta) * (y1 - yCenter));
		y1 = (int) (yCenter + Math.sin(theta) * (x1 - xCenter) + Math.cos(theta) * (y1 - yCenter));
		x2 = (int) (xCenter + Math.cos(theta) * (x2 - xCenter) - Math.sin(theta) * (y2 - yCenter));
		y2 = (int) (yCenter + Math.sin(theta) * (x2 - xCenter) + Math.cos(theta) * (y2 - yCenter));
	}

	@Override
	public void draw(Graphics g) {
		g.drawLine(getX1(), getY1(), getX2(), getY2());
	}

	@Override
	public void fill(Graphics g) {
		draw(g);
	}

	@Override
	public int[][] getPoints() {
		return new int[][] { { getX1(), getY1() }, { getX2(), getY2() } };
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
	}

	public Point getCenter() {
		return new Point((x1 + x2) / 2, (y1 + y2) / 2);
	}

	@Override
	public java.awt.Shape toAWT() {
		return new Line2D.Float(x1, y1, x2, y2);
	}

	public double length() {
		return Math.sqrt((getX1() - getX2()) * (getX1() - getX2()) + (getY1() - getY2()) * (getY1() - getY2()));
	}

	/** Returns the X coordinate of the start point of this {@code Line} object.
	 * 
	 * @return the X coordinate of the start point */
	public int getX1() {
		return x1 + x;
	}

	/** Returns the Y coordinate of the start point of this {@code Line} object.
	 * 
	 * @return the Y coordinate of the start point */
	public int getY1() {
		return y1 + y;
	}

	/** Returns the X coordinate of the end point of this {@code Line} object.
	 * 
	 * @return the X coordinate of the end point */
	public int getX2() {
		return x2 + x;
	}

	/** Returns the Y coordinate of the end point of this {@code Line} object.
	 * 
	 * @return the Y coordinate of the end point */
	public int getY2() {
		return y2 + y;
	}
}
