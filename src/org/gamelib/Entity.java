/**
 * 
 */
package org.gamelib;

import org.gamelib.backend.Graphics;

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

	/** System for collecting and updating entities. */
	public static class EntitySystem implements Updateable, Drawable {

		/** Default size */
		static final int INITIAL_SIZE = 10000;

		/** Array of entities */
		final Entity[] entities;
		/** Number of active entities */
		int living;

		public EntitySystem(int size) {
			entities = new Entity[size];
		}

		/**
		 * Notifies the entity about updates and draws.
		 * @return the entity id
		 */
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

		/**
		 * Amount of actual used identifiers in {@link #getEntities()}.
		 * @return amount of living entities
		 */
		public int getLiving() {
			return living;
		}

		/** @return the next entity's id */
		@Deprecated
		public int getNextEntityId() {
			return living;
		}
		
		/** {@inheritDoc} */
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

		/** {@inheritDoc} */
		@Override
		public void draw(Graphics g, float delta) {
			for (int i = 0; i < living; i++) {
				Entity entity = entities[i];
				entity.draw(g, delta);
			}
		}

	}
}
