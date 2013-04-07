/**
 * 
 */
package org.gamelib.util.geom;

import java.awt.Graphics2D;

/**
 * TODO
 * 
 * @author pwnedary
 * 
 */
public interface Shape {
	/**
	 * Tests if the specified {@link Shape} is inside the boundary of this
	 * {@link Shape}.
	 * 
	 * @param shape the {@link Shape} to test against
	 * @return if it's a collision
	 */
	public boolean collides(Shape shape);

	/**
	 * Translates the vertices of this {@link Shape}.
	 * 
	 * @param deltaX the amount to translate along the X axis
	 * @param deltaY the amount to translate along the Y axis
	 */
	public void translate(int deltaX, int deltaY);

	/**
	 * Rotates the vertices of this {@link Shape} around the center.
	 * 
	 * @param theta amount in degrees
	 */
	public void rotate(double theta);

	/** @return the bounding box of this {@link Shape}. */
	public Rectangle getBounds();

	/**
	 * Draws the shape.
	 * 
	 * @param g2d the {@link Graphics2D} reference
	 */
	public void draw(Graphics2D g2d);
}
