/**
 * 
 */
package org.gamelib.util.geom;

import org.gamelib.backend.Graphics;

/**
 * @author Axel
 */
public class Circle implements Shape {

	private int radius;
	private int x, y;

	/**
	 * 
	 */
	public Circle(int radius) {
		this.radius = radius;
	}

	/** {@inheritDoc} */
	@Override
	public boolean collides(Shape shape) {
		if (shape instanceof Circle) {
			Circle circle = (Circle) shape;
			return Math.sqrt((circle.x - x) * (circle.x - x) + (circle.y - y) * (circle.y - y)) < radius + circle.radius;
		}
		throw new UnsupportedOperationException();
	}

	/** {@inheritDoc} */
	@Override
	public void translate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/** {@inheritDoc} */
	@Override
	public void rotate(double theta) {}

	/** {@inheritDoc} */
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
	}

	/** {@inheritDoc} */
	@Override
	public void fill(Graphics g) {
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	@Override
	public int[][] getPoints() {
		return new int[Integer.MAX_VALUE][Integer.MAX_VALUE]; // lol
	}

	/** {@inheritDoc} */
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x - radius, y - radius, radius * 2, radius * 2);
	}

	/** {@inheritDoc} */
	@Override
	public java.awt.Shape toAWT() {
		return null;
	}

}
