/**
 * 
 */
package org.gamelib;

/**
 * An object with coordinates and movement in a 2d space.
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

	/** An implementation for Entity with overriden getters and setters. */
	public static abstract class EntityImpl implements Entity {
		protected float x, y, lastX, lastY, dx, dy;

		/** {@inheritDoc} */
		@Override
		public float getX() {
			return x;
		}

		/** {@inheritDoc} */
		@Override
		public void setX(float x) {
			this.x = x;
		}

		/** {@inheritDoc} */
		@Override
		public float getY() {
			return y;
		}

		/** {@inheritDoc} */
		@Override
		public void setY(float y) {
			this.y = y;
		}

		/** {@inheritDoc} */
		@Override
		public float getLastX() {
			return lastX;
		}

		/** {@inheritDoc} */
		@Override
		public void setLastX(float lastX) {
			this.lastX = lastX;
		}

		/** {@inheritDoc} */
		@Override
		public float getLastY() {
			return lastY;
		}

		/** {@inheritDoc} */
		@Override
		public void setLastY(float lastY) {
			this.lastY = lastY;
		}

		/** {@inheritDoc} */
		@Override
		public float getDX() {
			return dx;
		}

		/** {@inheritDoc} */
		@Override
		public void setDX(float hspeed) {
			this.dx = hspeed;
		}

		/** {@inheritDoc} */
		@Override
		public float getDY() {
			return dy;
		}

		/** {@inheritDoc} */
		@Override
		public void setDY(float vspeed) {
			this.dy = vspeed;
		}
	}
}
