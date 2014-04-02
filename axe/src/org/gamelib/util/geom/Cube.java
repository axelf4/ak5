/**
 * 
 */
package org.gamelib.util.geom;

import java.awt.Graphics;

/** @author pwnedary */
public class Cube implements Shape {
	/** The X coordinate of the upper-left corner. */
	public int x;
	/** The Y coordinate of the upper-left corner. */
	public int y;
	/** The Z coordinate of the upper-left corner. */
	public int z;
	/** The width of the {@link Cube}. */
	public int width;
	/** The width of the {@link Cube}. */
	public int height;
	/** The depth of the {@link Cube}. */
	public int depth;

	/**
	 * 
	 */
	public Cube(int x, int y, int z, int width, int height, int depth) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.width = width;
		this.height = height;
		this.depth = depth;
	}

	/**
	 * 
	 */
	public Cube() {
		this(0, 0, 0, 0, 0, 0);
	}

	/* (non-Javadoc)
	 * @see org.gamelib.util.geom.Shape#collides(org.gamelib.util.geom.Shape) */
	@Override
	public boolean collides(Shape shape) {
		if (shape instanceof Cube) {
			Cube a = this;
			Cube b = (Cube) shape;
			return (a.max_x() >= b.min_x() && a.min_x() <= b.max_x()) && (a.max_y() >= b.min_y() && a.min_y() <= b.max_y()) && (a.max_z() >= b.min_z() && a.min_z() <= b.max_z());
		}
		throw new UnsupportedOperationException(getClass().getSimpleName() + " to " + shape.getClass().getSimpleName());
	}

	int min_x() {
		return x;
	}

	int max_x() {
		return x + width;
	}

	int min_y() {
		return y;
	}

	int max_y() {
		return y + height;
	}

	int min_z() {
		return z;
	}

	int max_z() {
		return z + depth;
	}

	/* (non-Javadoc)
	 * @see org.gamelib.util.geom.Shape#translate(int, int) */
	@Override
	public void translate(int deltaX, int deltaY) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.gamelib.util.geom.Shape#rotate(double) */
	@Override
	public void rotate(double theta) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.gamelib.util.geom.Shape#getPoints() */
	@Override
	public int[][] getPoints() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.gamelib.util.geom.Shape#getBounds() */
	@Override
	public Rectangle getBounds() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.gamelib.util.geom.Shape#draw(org.gamelib.backend.Graphics) */
	@Override
	public void draw(Graphics g) {
		//		g.drawCube(x, y, z, width, height, depth);
	}

	/* (non-Javadoc)
	 * @see org.gamelib.util.geom.Shape#fill(org.gamelib.backend.Graphics) */
	@Override
	public void fill(Graphics g) {
		//		g.drawCube(x, y, z, width, height, depth);
	}

	/* (non-Javadoc)
	 * @see org.gamelib.util.geom.Shape#toAWT() */
	@Override
	public java.awt.Shape toAWT() {
		throw new UnsupportedOperationException("AWT haven't implemented " + getClass().getSimpleName() + "s");
	}

}
