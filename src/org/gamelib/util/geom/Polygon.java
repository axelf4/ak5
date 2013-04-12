/**
 * 
 */
package org.gamelib.util.geom;

import java.awt.Point;
import java.util.Arrays;

import org.gamelib.graphics.Graphics;

/**
 * @author pwnedary
 * @see java.awt.Polygon
 */
public class Polygon implements Shape {

	/** The total number of points */
	private int npoints;
	/** The array of x coordinates. */
	private int[] xpoints;
	/** The array of y coordinates. */
	private int[] ypoints;

	private int deltaX, deltaY;
	public int xCenter, yCenter;
	private double angle;
	private Polygon rotated;

	/**
	 * Creates an empty polygon.
	 */
	public Polygon() {
		xpoints = new int[4];
		ypoints = new int[4];
		npoints = 4;
		rotated = this;
	}

	/**
	 * Constructs and initializes a <code>Polygon</code> from the specified
	 * parameters.
	 * 
	 * @param xpoints an array of X coordinates
	 * @param ypoints an array of Y coordinates
	 * @param npoints the total number of points in the <code>Polygon</code>
	 */
	public Polygon(int[] xpoints, int[] ypoints, int npoints) {
		if (npoints > xpoints.length || npoints > ypoints.length)
			throw new IndexOutOfBoundsException("npoints > xpoints.length || " + "npoints > ypoints.length");
		if (npoints < 0) throw new NegativeArraySizeException("npoints < 0");
		this.xpoints = xpoints;
		this.ypoints = ypoints;
		this.npoints = npoints;
		findCenter();
		rotated = this;
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.util.Shape#collides(Shape) */
	@Override
	public boolean collides(Shape shape) {
		Polygon polygon = (Polygon) shape;
		// test separation axes of current polygon
		for (int j = npoints - 1, i = 0; i < npoints; j = i, i++) {
			// Separate axis is perpendicular to the edge
			Point axis = new Point(getPointY(i) - getPointY(j), getPointX(i) - getPointX(j));

			if (separatedByAxis(axis, polygon)) return false;
		}

		// test separation axes of other polygon
		for (int j = polygon.npoints - 1, i = 0; i < polygon.npoints; j = i, i++) {
			// Separate axis is perpendicular to the edge
			Point axis = new Point(-polygon.getPointY(i) - polygon.getPointY(j), polygon.getPointX(i) - polygon.getPointX(j));

			if (separatedByAxis(axis, polygon)) return false;
		}
		return true;
	}

	float min, max, mina, maxa, minb, maxb;

	public void calculateInterval(Point axis) {
		this.min = this.max = (float) getPointX(0) * axis.x + getPointY(0) * axis.y;

		for (int i = 1; i < npoints; i++) {
			float d = (float) getPointX(i) * axis.x + getPointY(i) * axis.y;
			if (d < this.min) this.min = d;
			else if (d > this.max) this.max = d;
		}
	}

	public boolean intervalsSeparated(float mina, float maxa, float minb, float maxb) {
		return (mina > maxb) || (minb > maxa);
	}

	public boolean separatedByAxis(Point axis, Polygon polygon) {
		calculateInterval(axis);
		mina = min;
		maxa = max;
		polygon.calculateInterval(axis);
		minb = polygon.min;
		maxb = polygon.max;
		return intervalsSeparated(mina, maxa, minb, maxb);
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.util.Shape#draw(java.awt.Graphics2D) */
	@Override
	public void draw(Graphics g) {
		/* java.awt.Polygon polygon = new java.awt.Polygon(xpoints, ypoints,
		 * npoints); polygon.translate(deltaX, deltaY); g2d.draw(polygon); */
		int prevX = 0, prevY = 0;
		for (int i = 0; i < npoints; i++) {
			if (i <= 0) {
				prevX = getPointX(i);
				prevY = getPointY(i);
				continue;
			}
			g.drawLine(prevX, prevY, prevX = getPointX(i), prevY = getPointY(i));
		}
		g.drawLine(getPointX(0), getPointY(0), prevX, prevY);
	}

	/**
	 * Appends the specified coordinates to this <code>Polygon</code>.
	 * 
	 * @param x the specified X coordinate
	 * @param y the specified Y coordinate
	 */
	public void addPoint(int x, int y) {
		if (npoints >= xpoints.length || npoints >= ypoints.length) {
			int newLength = Integer.highestOneBit(npoints * 2);
			xpoints = Arrays.copyOf(xpoints, newLength);
			ypoints = Arrays.copyOf(ypoints, newLength);
		}
		xpoints[npoints] = x;
		ypoints[npoints] = y;
		npoints++;
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.util.Shape#translate(int, int) */
	@Override
	public void translate(int deltaX, int deltaY) {
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.util.Shape#getBounds() */
	@Override
	public Rectangle getBounds() {
		int boundsMinX = Integer.MAX_VALUE;
		int boundsMinY = Integer.MAX_VALUE;
		int boundsMaxX = Integer.MIN_VALUE;
		int boundsMaxY = Integer.MIN_VALUE;

		for (int i = 0; i < npoints; i++) {
			int x = xpoints[i];
			boundsMinX = Math.min(boundsMinX, x + deltaX);
			boundsMaxX = Math.max(boundsMaxX, x + deltaX);
			int y = ypoints[i];
			boundsMinY = Math.min(boundsMinY, y + deltaY);
			boundsMaxY = Math.max(boundsMaxY, y + deltaY);
		}
		return new Rectangle(boundsMinX, boundsMinY, boundsMaxX - boundsMinX, boundsMaxY - boundsMinY);
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.util.Shape#rotate(double) */
	@Override
	public void rotate(double theta) {
		// double increment = angle - theta;
		// angle += increment;
		// theta -= increment;
		this.angle = theta;
		rotated.xpoints = xpoints;
		rotated.ypoints = ypoints;
		rotated.npoints = npoints;
		rotated = new Polygon(xpoints.clone(), ypoints.clone(), npoints);
		rotated.translate(deltaX, deltaY);
		for (int i = 0; i < npoints; i++) {
			double px = rotated.xpoints[i];
			double py = rotated.ypoints[i];
			rotated.xpoints[i] = (int) (rotated.xCenter + ((px - rotated.xCenter) * Math.cos(angle) - (py - rotated.yCenter) * Math.sin(angle)));
			rotated.ypoints[i] = (int) (rotated.yCenter + ((px - rotated.xCenter) * Math.sin(angle) + (py - rotated.yCenter) * Math.cos(angle)));
		}
	}
	
	public Polygon getRotated() {
		return rotated;
	}

	public void findCenter() {
		double area = 0;
		int xSum = 0, ySum = 0;

		// find Area
		for (int i = 0; i < npoints - 1; i++) {
			area += xpoints[i] * ypoints[i + 1] - xpoints[i + 1] * ypoints[i];
			xSum += (xpoints[i] + xpoints[i + 1]) * (xpoints[i] * ypoints[i + 1] - xpoints[i + 1] * ypoints[i]);
			ySum += (ypoints[i] + ypoints[i + 1]) * (xpoints[i] * ypoints[i + 1] - xpoints[i + 1] * ypoints[i]);
		}
		area += xpoints[npoints - 1] * ypoints[0] - xpoints[0] * ypoints[npoints - 1];
		xSum += (xpoints[npoints - 1] + xpoints[0]) * (xpoints[npoints - 1] * ypoints[0] - xpoints[0] * ypoints[npoints - 1]);
		ySum += (ypoints[npoints - 1] + ypoints[0]) * (xpoints[npoints - 1] * ypoints[0] - xpoints[0] * ypoints[npoints - 1]);

		area = area / 2;

		xCenter = (int) (xSum / (6 * area)) + deltaX;
		yCenter = (int) (ySum / (6 * area)) + deltaY;
	}

	public int getPointX(int id) {
		return xpoints[id] + deltaX;
	}

	public int getPointY(int id) {
		return ypoints[id] + deltaY;
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.util.geom.Shape#toAWT() */
	@Override
	public java.awt.Shape toAWT() {
		return new java.awt.Polygon(xpoints, ypoints, npoints);
	}

}
