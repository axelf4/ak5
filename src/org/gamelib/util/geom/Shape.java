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
	/* /** Tests if the specified rectangle is inside the boundary of this
	 * <code>Shape</code>.
	 * 
	 * @param x
	 * 
	 * @param y
	 * 
	 * @param width
	 * 
	 * @param height
	 * 
	 * @return4/ public boolean collides(int x, int y, int width, int height); */
	/**
	 * Tests if the specified {@link Shape} is inside the boundary of this
	 * {@link Shape}.
	 * 
	 * @param shape the {@link Shape} to test against
	 * @return if it's a collision
	 */
	public boolean collides(Shape shape);

	/**
	 * Translates the vertices of this <code>Shape</code>.
	 * 
	 * @param deltaX the amount to translate along the X axis
	 * @param deltaY the amount to translate along the Y axis
	 */
	public void translate(int deltaX, int deltaY);
	
	public void rotate(double theta);

	/** Gets the bounding box of this <code>Shape</code>. */
	public Rectangle getBounds();

	/**
	 * Draws the shape.
	 * 
	 * @param g2d the {@link Graphics2D} reference
	 */
	public void draw(Graphics2D g2d);
}
