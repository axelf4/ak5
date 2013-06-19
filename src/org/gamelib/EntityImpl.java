/**
 * 
 */
package org.gamelib;

import org.gamelib.Entity;

/**
 * @author pwnedary
 */
public abstract class EntityImpl implements Entity {

	protected float x, y, lastX, lastY, dx, dy;

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Entity#getX()
	 */
	@Override
	public float getX() {
		return x;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Entity#setX(float)
	 */
	@Override
	public void setX(float x) {
		this.x = x;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Entity#getY()
	 */
	@Override
	public float getY() {
		return y;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Entity#setY(float)
	 */
	@Override
	public void setY(float y) {
		this.y = y;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Entity#getLastX()
	 */
	@Override
	public float getLastX() {
		return lastX;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Entity#setLastX(float)
	 */
	@Override
	public void setLastX(float lastX) {
		this.lastX = lastX;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Entity#getLastY()
	 */
	@Override
	public float getLastY() {
		return lastY;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Entity#setLastY(float)
	 */
	@Override
	public void setLastY(float lastY) {
		this.lastY = lastY;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Entity#DX()
	 */
	@Override
	public float getDX() {
		return dx;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Entity#setDX(float)
	 */
	@Override
	public void setDX(float hspeed) {
		this.dx = hspeed;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Entity#getDY()
	 */
	@Override
	public float getDY() {
		return dy;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Entity#setDY(float)
	 */
	@Override
	public void setDY(float vspeed) {
		this.dy = vspeed;
	}

}
