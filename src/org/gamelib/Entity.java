/**
 * 
 */
package org.gamelib;

/**
 * An object with coordinates in a 2d space.
 * @author pwnedary
 */
public interface Entity extends Updateable, Drawable {

	/** @return the x-axis coordinate. */
	public float getX();

	/** @param the new x-axis coordinate. */
	public void setX(float x);

	/** @return the y-axis coordinate. */
	public float getY();

	/** @param the new y-axis coordinate. */
	public void setY(float y);

	/** @return the last x-axis coordinate. */
	public float getLastX();

	/** @param the last new x-axis coordinate. */
	public void setLastX(float lastX);

	/** @return the last x-axis coordinate. */
	public float getLastY();

	/** @param the last new x-axis coordinate. */
	public void setLastY(float lastY);

	/** @return the speed traveling the x-axis coordinate. */
	public float getDX();

	/** @param the new speed traveling the x-axis coordinate */
	public void setDX(float hspeed);

	/** @return the speed traveling the y-axis coordinate. */
	public float getDY();

	/** @param the new speed traveling the y-axis coordinate. */
	public void setDY(float vspeed);
}
