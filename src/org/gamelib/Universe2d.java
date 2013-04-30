/**
 * 
 */
package org.gamelib;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.gamelib.backend.Graphics;

/**
 * @author Axel
 * 
 */
public class Universe2d implements Handler {

	public final CopyOnWriteArrayList<Entity> entities = new CopyOnWriteArrayList<Entity>();

	/**
	 * 
	 */
	public Universe2d() {
		HandlerRegistry.instance().register(this);
	}

	/**
	 * 
	 */
	public Universe2d(View view) {
		HandlerRegistry.instance().register(this, view);
	}

	public void add(Entity entity) {
		entities.add(entity);
	}

	public void remove(Entity entity) {
		entities.remove(entity);
	}

	@Override
	public void handle(Event event) {
		if (event instanceof Event.Tick) {
			float delta = ((Event.Tick) event).delta;
			for (int i = 0; i < entities.size(); i++) {
				Entity entity = entities.get(i);
				entity.setLastX(entity.getX());
				entity.setLastY(entity.getY());

				// entity.setX(entity.getX() + entity.getHSpeed());
				// entity.setY(entity.getY() + entity.getVSpeed());

				entity.update(delta);
			}
		} else if (event instanceof Event.Draw) {
			Graphics g = ((Event.Draw) event).graphics;
			float delta = ((Event.Draw) event).delta;
			for (int i = 0; i < entities.size(); i++) {
				Entity entity = entities.get(i);
				entity.draw(g, delta);
			}
		}
	}

	@Override
	public void handlers(List<Class<? extends Event>> list) {
		list.add(Event.Tick.class);
		list.add(Event.Draw.class);
	}

}
