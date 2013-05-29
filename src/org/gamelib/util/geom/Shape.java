/**
 * 
 */
package org.gamelib.util.geom;

import org.gamelib.backend.Graphics;

/**
 * Interface for geometric shapes. TODO add getX and getY that return the upper hand left corner
 * @author pwnedary
 */
public interface Shape {
	/**
	 * Tests if the specified {@link Shape} is inside the boundary of this {@link Shape}. <br />
	 * supported:<br />
	 * rectangle -> rectangle<br />
	 * rectangle -> point<br />
	 * polygon -> polygon
	 * @param shape the {@link Shape} to test against
	 * @return if it's a collision
	 */
	public boolean collides(Shape shape);

	/**
	 * Translates the vertices of this {@link Shape}.
	 * @param deltaX the amount to translate along the X axis
	 * @param deltaY the amount to translate along the Y axis
	 */
	public void translate(int deltaX, int deltaY);

	/**
	 * Rotates the vertices of this {@link Shape} around the center.
	 * @param theta amount in radians
	 */
	public void rotate(double theta);

	/** @return array of each point */
	public int[][] getPoints();

	/** @return the bounding box of this {@link Shape}. */
	public Rectangle getBounds();

	/**
	 * Draws the outline of this {@link Shape}.
	 * @param g the {@link Graphics} reference
	 */
	public void draw(Graphics g);

	/**
	 * Fills this {@link Shape}.
	 * @param g the {@link Graphics} reference
	 */
	public void fill(Graphics g);

	/** @return AWT's representation of this shape. */
	public java.awt.Shape toAWT();
}
