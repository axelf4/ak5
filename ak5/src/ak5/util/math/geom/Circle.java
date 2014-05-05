/**
 * 
 */
package ak5.util.math.geom;

import java.awt.Graphics;

/** @author pwnedary */
public class Circle implements Shape {
	private int radius;
	private int x, y;

	public Circle(int radius) {
		this.radius = radius;
	}

	@Override
	public boolean collides(Shape shape) {
		if (shape instanceof Circle) {
			Circle circle = (Circle) shape;
			return Math.sqrt((circle.x - x) * (circle.x - x) + (circle.y - y) * (circle.y - y)) < radius + circle.radius;
		}
		throw new UnsupportedOperationException();
	}

	@Override
	public void translate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void rotate(double theta) {}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
	}

	@Override
	public void fill(Graphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	public int[][] getPoints() {
		return new int[Integer.MAX_VALUE][Integer.MAX_VALUE]; // lol
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x - radius, y - radius, radius * 2, radius * 2);
	}

	@Override
	public java.awt.Shape toAWT() {
		return null;
	}

}
