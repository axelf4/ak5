/**
 * 
 */
package org.gamelib.util.geom;

import org.gamelib.backend.Graphics;

/** @author pwnedary
 * @see java.awt.Polygon */
public class Polygon implements Shape {
	/** The vertices in x, y order */
	private int[] vertices;
	/** Vertices of the transformed polygon */
	private int[] transformed;
	private int x, y;
	public int xCenter;
	public int yCenter;
	private double angle;

	/** Creates an empty polygon. */
	public Polygon() {
		this.transformed = this.vertices = new int[0];
	}

	/** Constructs and initializes a <code>Polygon</code> from the specified parameters.
	 * 
	 * @param vertices the vertices in x, y order */
	public Polygon(int[] vertices) {
		this.transformed = this.vertices = vertices;
		findCenter();
	}

	@Override
	public boolean collides(Shape shape) {
		if (shape instanceof Polygon) {
			Polygon polygon = (Polygon) shape;
			// test separation axes of current polygon
			for (int j = getLength() - 1, i = 0; i < getLength(); j = i, i++) {
				Point axis = new Point(getPointY(i) - getPointY(j), getPointX(i) - getPointX(j)); // Separate axis is perpendicular to the edge

				if (separatedByAxis(axis, polygon)) return false;
			}

			// test separation axes of other polygon
			for (int j = polygon.getLength() - 1, i = 0; i < polygon.getLength(); j = i, i++) {
				Point axis = new Point(-polygon.getPointY(i) - polygon.getPointY(j), polygon.getPointX(i) - polygon.getPointX(j)); // Separate axis is perpendicular to the edge

				if (separatedByAxis(axis, polygon)) return false;
			}
			return true;
		} else throw new UnsupportedOperationException();
	}

	private int min, max, mina, maxa, minb, maxb;

	void calculateInterval(Point axis) {
		this.min = this.max = getPointX(0) * axis.getX() + getPointY(0) * axis.getY();

		for (int i = 1; i < getLength(); i++) {
			int d = getPointX(i) * axis.getX() + getPointY(i) * axis.getY();
			if (d < this.min) this.min = d;
			else if (d > this.max) this.max = d;
		}
	}

	boolean separatedByAxis(Point axis, Polygon polygon) {
		calculateInterval(axis);
		mina = min;
		maxa = max;
		polygon.calculateInterval(axis);
		minb = polygon.min;
		maxb = polygon.max;
		return (mina > maxb) || (minb > maxa); // test if intervals are separated
	}

	/** Appends the specified coordinates to this <code>Polygon</code>.
	 * 
	 * @param x the specified X coordinate
	 * @param y the specified Y coordinate */
	public void addPoint(int x, int y) {
		int[] tmp = vertices;
		vertices = new int[vertices.length + 2];
		System.arraycopy(tmp, 0, vertices, 0, tmp.length);
		vertices[vertices.length - 1 - 1] = x;
		vertices[vertices.length - 1 - 0] = y;
		findCenter();
		transformed = vertices.clone();
		rotate(0);
	}

	public int getPointX(int id) {
		// return xpoints[id] + x;
		return transformed[id * 2] + x;
	}

	public int getPointY(int id) {
		// return ypoints[id] + y;
		return transformed[id * 2 + 1] + y;
	}

	public int getPointX2(int id) {
		// return xpoints[id] + x;
		return vertices[id * 2] + x;
	}

	public int getPointY2(int id) {
		// return ypoints[id] + y;
		return vertices[id * 2 + 1] + y;
	}

	public int getLength() {
		return vertices.length / 2;
	}

	@Override
	public void translate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void rotate(double theta) {
		this.angle += theta;
		for (int i = 0; i < vertices.length; i += 2) {
			int oldX = vertices[i] - xCenter;
			int oldY = vertices[i + 1] - yCenter;
			transformed[i] = xCenter + (int) (oldX * Math.cos(angle) - oldY * Math.sin(angle) + 0.5f); // x coordinate
			transformed[i + 1] = yCenter + (int) (oldX * Math.sin(angle) + oldY * Math.cos(angle) + 0.5f); // y coordinate
		}
	}

	public void findCenter() {
		/* int greatestX = 0, greatestY = 0; for (int i = 0; i < getLength(); i++) if (i % 2 == 0) greatestX =
		 * Math.max(greatestX, vertices[i]); // x coordinate else greatestY = Math.max(greatestY, vertices[i]); // y
		 * coordinate xCenter = greatestX / 2; yCenter = greatestY / 2; */

		double area = 0;
		int xSum = 0, ySum = 0; // find Area
		for (int i = 0; i < getLength() - 1; i++) {
			area += getPointX2(i) * getPointY2(i + 1) - getPointX2(i + 1) * getPointY2(i);
			xSum += (getPointX2(i) + getPointX2(i + 1)) * (getPointX2(i) * getPointY2(i + 1) - getPointX2(i + 1) * getPointY2(i));
			ySum += (getPointY2(i) + getPointY2(i + 1)) * (getPointX2(i) * getPointY2(i + 1) - getPointX2(i + 1) * getPointY2(i));
		}
		area += getPointX2(getLength() - 1) * getPointY2(0) - getPointX2(0) * getPointY2(getLength() - 1);
		xSum += (getPointX2(getLength() - 1) + getPointX2(0)) * (getPointX2(getLength() - 1) * getPointY2(0) - getPointX2(0) * getPointY2(getLength() - 1));
		ySum += (getPointY2(getLength() - 1) + getPointY2(0)) * (getPointX2(getLength() - 1) * getPointY2(0) - getPointX2(0) * getPointY2(getLength() - 1));
		area = area / 2;
		xCenter = (int) (xSum / (6 * area));
		yCenter = (int) (ySum / (6 * area));
	}

	@Override
	public void draw(Graphics g) {
		// java.awt.Polygon polygon = new java.awt.Polygon(xpoints, ypoints, npoints); polygon.translate(deltaX, deltaY); g2d.draw(polygon);
		int prevX = getPointX(0), prevY = getPointY(0);
		for (int i = 0; i < vertices.length / 2; i++)
			g.drawLine(prevX, prevY, prevX = getPointX(i), prevY = getPointY(i));
		g.drawLine(getPointX(0), getPointY(0), prevX, prevY);
	}

	@Override
	public void fill(Graphics g) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int[][] getPoints() {
		int[][] value = new int[getLength()][2];
		for (int i = 0; i < getLength(); i++) {
			value[i][0] = getPointX(i);
			value[i][1] = getPointY(i);
		}
		return value;
	}

	@Override
	public Rectangle getBounds() {
		int boundsMinX = Integer.MAX_VALUE;
		int boundsMinY = Integer.MAX_VALUE;
		int boundsMaxX = Integer.MIN_VALUE;
		int boundsMaxY = Integer.MIN_VALUE;

		for (int i = 0; i < vertices.length / 2; i++) {
			int x = getPointX(i);
			boundsMinX = Math.min(boundsMinX, x + x);
			boundsMaxX = Math.max(boundsMaxX, x + x);
			int y = getPointY(i);
			boundsMinY = Math.min(boundsMinY, y + y);
			boundsMaxY = Math.max(boundsMaxY, y + y);
		}
		return new Rectangle(boundsMinX, boundsMinY, boundsMaxX - boundsMinX, boundsMaxY - boundsMinY);
	}

	@Override
	public java.awt.Shape toAWT() {
		int[] xpoints = new int[getLength()];
		int[] ypoints = new int[getLength()];
		for (int i = 0; i < vertices.length / 2; i++) {
			xpoints[i] = getPointX(i);
			ypoints[i] = getPointY(i);
		}
		return new java.awt.Polygon(xpoints, ypoints, getLength());
	}

}
