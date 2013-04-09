/**
 * 
 */
package org.gamelib;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
			for (Iterator<Entity> iterator = entities.iterator(); iterator.hasNext();) {
				Entity entity = (Entity) iterator.next();
				entity.setLastX(entity.getX());
				entity.setLastY(entity.getY());

				// entity.setX(entity.getX() + entity.getHSpeed());
				// entity.setY(entity.getY() + entity.getVSpeed());

				entity.update();
			}
		} else if (event instanceof Event.Draw) {
			Graphics g = ((Event.Draw) event).graphics;
			float interpolation = ((Event.Draw) event).interpolation;
			for (Iterator<Entity> iterator = entities.iterator(); iterator.hasNext();) {
				Entity entity = (Entity) iterator.next();
				entity.draw(g, interpolation);
			}
		}
	}

	@Override
	public void handlers(List<Class<? extends Event>> list) {
		list.add(Event.Tick.class);
		list.add(Event.Draw.class);
	}

}
