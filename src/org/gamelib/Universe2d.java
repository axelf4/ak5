/**
 * 
 */
package org.gamelib;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.gamelib.backend.Graphics;

/**
 * @author Axel
 */
public class Universe2d implements Handler {

	static final int MAX_ENTITIES = 100;

	// public final CopyOnWriteArrayList<Entity> entities = new CopyOnWriteArrayList<Entity>();
	final Entity[] entities = new Entity[MAX_ENTITIES];
	int living;

	/**
	 * 
	 */
	public Universe2d() {
		Registry.instance().register(this);
	}

	/**
	 * 
	 */
	public Universe2d(Group group) {
		Registry.instance().register(this, group);
	}

	public void spawn(Entity entity) {
		entities[living++] = entity;
	}

	@Deprecated
	public void kill(Entity entity) {
		for (int i = 0; i < entities.length; i++)
			if (entities[i].equals(entity)) remove(i);
	}

	public void kill(int i) {
		if (living <= 0) return;
		living--;
		entities[i] = entities[living];
	}

	@Deprecated
	public void add(Entity entity) {
		// entities.add(entity);
		entities[living++] = entity;
	}

	// TODO rename to kill
	@Deprecated
	public void remove(Entity entity) {
		// entities.remove(entity);
		for (int i = 0; i < entities.length; i++)
			if (entities[i].equals(entity)) remove(i);
	}

	@Deprecated
	public void remove(int i) {
		// entities.remove(entity);
		if (living <= 0) return;
		living--;
		entities[i] = entities[living];
	}

	@Override
	public void handle(Event event) {
		if (event instanceof Event.Tick) {
			float delta = ((Event.Tick) event).delta;
			// for (int i = 0; i < entities.size(); i++) {
			// Entity entity = entities.get(i);
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
			// for (int i = 0; i < entities.size(); i++) {
			// Entity entity = entities.get(i);
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

	public Entity[] getEntities() {
		// return Arrays.copyOf(entities, living);
		return entities;
	}

	/** for use with {@link #getEntities()} */
	public int getNrEntities() {
		return living;
	}
	
	public int getNextEntityId() {
		return living;
	}

}
