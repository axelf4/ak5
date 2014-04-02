/**
 * 
 */
package org.gamelib.util;

import org.gamelib.Drawable;
import org.gamelib.Updatable;
import org.gamelib.graphics.GL10;

/** An object with coordinates and movement in a 2d space.
 * 
 * @author pwnedary */
public interface Entity extends Updatable, Drawable {
	/** Returns the x-coordinate.
	 * 
	 * @return the x-coordinate */
	public float getX();

	/** Sets the x-coordinate to <code>x</code>
	 * 
	 * @param x the new x-axis coordinate. */
	public void setX(float x);

	/** Returns the y-coordinate.
	 * 
	 * @return the y-coordinate */
	public float getY();

	/** Sets the y-coordinate to <code>y</code>
	 * 
	 * @param y the new y-axis coordinate. */
	public void setY(float y);

	/** Returns this {@link Entity}'s x-coordinate as during the last frame.
	 * 
	 * @return the last x-coordinate */
	public float getLastX();

	/** Sets this {@link Entity}'s x-coordinate from the last frame to <code>lastX</code>.
	 * 
	 * @param lastX the new last x-coordinate */
	public void setLastX(float lastX);

	/** Returns this {@link Entity}'s y-coordinate as during the last frame.
	 * 
	 * @return the last y-coordinate */
	public float getLastY();

	/** Sets this {@link Entity}'s y-coordinate from the last frame to <code>lastY</code>.
	 * 
	 * @param lastY the new last y-coordinate */
	public void setLastY(float lastY);

	/** Returns the speed at which traveling the x-axis.
	 * 
	 * @return the speed at which traveling the x-axis */
	public float getDX();

	/** Sets the speed at which to travel the x-axis.
	 * 
	 * @param dx the speed at which to travel the x-axis */
	public void setDX(float dx);

	/** Returns the speed at which traveling the y-axis.
	 * 
	 * @return the speed at which traveling the y-axis */
	public float getDY();

	/** Sets the speed at which to travel the y-axis.
	 * 
	 * @param dy the speed at which to travel the y-axis */
	public void setDY(float dy);

	/** An implementation for an {@link Entity} with overridden getters and setters. */
	public static abstract class EntityImpl implements Entity {
		protected float x, y, lastX, lastY, dx, dy;

		@Override
		public float getX() {
			return x;
		}

		@Override
		public void setX(float x) {
			this.x = x;
		}

		@Override
		public float getY() {
			return y;
		}

		@Override
		public void setY(float y) {
			this.y = y;
		}

		@Override
		public float getLastX() {
			return lastX;
		}

		@Override
		public void setLastX(float lastX) {
			this.lastX = lastX;
		}

		@Override
		public float getLastY() {
			return lastY;
		}

		@Override
		public void setLastY(float lastY) {
			this.lastY = lastY;
		}

		@Override
		public float getDX() {
			return dx;
		}

		@Override
		public void setDX(float hspeed) {
			this.dx = hspeed;
		}

		@Override
		public float getDY() {
			return dy;
		}

		@Override
		public void setDY(float vspeed) {
			this.dy = vspeed;
		}
	}

	/** System for collecting and updating entities. */
	public static class EntitySystem implements Updatable, Drawable {
		/** Default size. */
		static final int INITIAL_SIZE = 10000;

		/** Array of entities. */
		final Entity[] entities;
		/** Number of active entities. */
		int living;

		public EntitySystem(int size) {
			entities = new Entity[size];
		}

		/** Notifies the entity about updates and draws.
		 * 
		 * @return the entity id */
		public int spawn(Entity entity) {
			if (living >= entities.length) System.arraycopy(entities, 0, entities, 0, entities.length * 2);
			entities[living++] = entity;
			return living - 1;
		}

		/** Stops notifying the entity. */
		public void kill(Entity entity) {
			for (int i = 0; i < living; i++)
				if (entities[i].equals(entity)) kill(i);
		}

		/** Stops notifying the entity with id <code>id</code>. */
		public void kill(int id) {
			if (living <= 0) return;
			entities[id] = entities[--living];
		}

		/** Stops notifying all entities. */
		public void clear() {
			this.living = 0;
		}

		/** @return the array of entities, values CAN be null */
		public Entity[] getEntities() {
			// return Arrays.copyOf(entities, living);
			return entities;
		}

		/** Amount of actual used identifiers in {@link #getEntities()}.
		 * 
		 * @return amount of living entities */
		public int getLiving() {
			return living;
		}

		/** @return the next entity's id */
		@Deprecated
		public int getNextEntityId() {
			return living;
		}

		@Override
		public void update(float delta) {
			for (int i = 0; i < living; i++) {
				Entity entity = entities[i];
				entity.setLastX(entity.getX());
				entity.setLastY(entity.getY());

				// entity.setX(entity.getX() + entity.getHSpeed());
				// entity.setY(entity.getY() + entity.getVSpeed());

				entity.update(delta);
			}
		}

		@Override
		public void draw(GL10 gl, float delta) {
			for (int i = 0; i < living; i++) {
				Entity entity = entities[i];
				entity.draw(gl, delta);
			}
		}
	}
}
