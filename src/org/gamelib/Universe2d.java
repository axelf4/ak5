/**
 * 
 */
package org.gamelib;

import java.util.List;

import org.gamelib.backend.Graphics;

/**
 * @author Axel
 */
public class Universe2d implements Handler {

	static final int INITIAL_SIZE = 10000;

	// public final CopyOnWriteArrayList<Entity> entities = new CopyOnWriteArrayList<Entity>();
	/** Array of entities */
	final Entity[] entities = new Entity[INITIAL_SIZE];
	/** Number of active entities */
	int living;

	public Universe2d() {
		Registry.instance().register(this);
	}

	public Universe2d(Group group) {
		Registry.instance().register(this, group);
	}

	/**
	 * Notifies the entity about updates and draws.
	 * @return the entity id
	 */
	public int spawn(Entity entity) {
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

	@Override
	public void handle(Event event) {
		if (event instanceof Event.Tick) {
			float delta = ((Event.Tick) event).delta;
			for (int i = 0; i < living; i++) {
				Entity entity = entities[i];
				entity.setLastX(entity.getX());
				entity.setLastY(entity.getY());

				// entity.setX(entity.getX() + entity.getHSpeed());
				// entity.setY(entity.getY() + entity.getVSpeed());

				entity.update(delta);
			}
		} else if (event instanceof Event.Draw) {
			Graphics g = ((Event.Draw) event).graphics;
			float delta = ((Event.Draw) event).delta;
			for (int i = 0; i < living; i++) {
				Entity entity = entities[i];
				entity.draw(g, delta);
			}
		}
	}

	@Override
	public void handlers(List<Class<? extends Event>> list) {
		list.add(Event.Tick.class);
		list.add(Event.Draw.class);
	}

	/** @return the array of entities, values CAN be null */
	public Entity[] getEntities() {
		// return Arrays.copyOf(entities, living);
		return entities;
	}

	/**
	 * for use with {@link #getEntities()}
	 * @return nr of living entities
	 */
	public int getNrEntities() {
		return living;
	}

	/** @return the next entity's id */
	public int getNextEntityId() {
		return living;
	}

}
