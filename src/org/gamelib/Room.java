/**
 * 
 */
package org.gamelib;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.gamelib.Handler.Event;
import org.gamelib.ui.Component;

/**
 * possible names: Room, Scene, Room,
 * 
 * @author Axel
 */
public class Room {
	public Map<Class<? extends Event>, CopyOnWriteArrayList<Handler>> handlers = new HashMap<Class<? extends Event>, CopyOnWriteArrayList<Handler>>(1);
	private Rectangle size = null;
	/** Whether the handler should receive events */
	public boolean active = true;
	private boolean alwaysActive = false;

	// private List<Scene> rooms = HandlerRegistry.instance().views;

	public Room() {
		// HandlerRegistry.instance().addScene(this);
	}

	public Room(Rectangle size) {
		this();
		this.size = size;
	}

	/**
	 * @return the rectangle
	 */
	public Rectangle getRectangle() {
		return size;
	}

	/**
	 * Sets the rectangle to the specified.
	 * 
	 * @param rectangle
	 * @return the existing rectangle if the given is null
	 */
	public void setSize(Rectangle size) {
		this.size = size;
	}

	public Room setAlwaysActive(boolean b) {
		this.alwaysActive = b;
		return this;
	}

	/**
	 * Registers an handler
	 * 
	 * @param handler {@link Handler} instance
	 */
	public void register(Handler handler) {
		List<Room> rooms = HandlerRegistry.instance().rooms;
		if (!rooms.contains(this))
			rooms.add(this);

		ArrayList<Class<? extends Event>> list = new ArrayList<Class<? extends Event>>();
		handler.handlers(list);
		for (Iterator<Class<? extends Event>> iterator = list.iterator(); iterator.hasNext();) {
			Class<? extends Event> type = iterator.next();
			if (!handlers.containsKey(type))
				handlers.put(type, new CopyOnWriteArrayList<Handler>());
			handlers.get(type).add(handler);
		}
	}

	/* Room utility */

	public void switchTo() {
		for (Room toCheck : HandlerRegistry.instance().rooms) {
			if (!toCheck.alwaysActive)
				toCheck.active = false;
		}
		active = true;
	}

	public static class UIPane extends Room {
		private List<Component> components = new ArrayList<Component>();

		/**
		 * 
		 */
		public UIPane() {
			// TODO Auto-generated constructor stub
		}

		public void add(Component component) {
			components.add(component);
		}
	}
}