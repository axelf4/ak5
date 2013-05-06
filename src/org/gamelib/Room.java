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
 * possible names: View, Scene, Room,
 * 
 * @author Axel
 */
public class Room {
	public Map<Class<? extends Event>, CopyOnWriteArrayList<Handler>> handlers = new HashMap<Class<? extends Event>, CopyOnWriteArrayList<Handler>>(1);
	private Rectangle size = null;
	/** Whether the handler should receive events */
	boolean active = true;
	private boolean alwaysActive = false;
	Room parent;
	List<Room> children = new ArrayList<Room>();

	// private List<Scene> rooms = Registry.instance().views;

	public Room() {
		if (parent == null)
			parent = Registry.DEFAULT_VIEW;
		if (parent == null)
			return; // this is the default
		parent.children.add(this);
	}

	public Room(Room parent) {
		this.parent = parent;
		parent.children.add(this);
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

	/** @return if active */
	public boolean isActive() {
		return active;
	}

	/** Sets if this and every underlying room is active. */
	public void setActive(boolean active) {
		this.active = active;
		for (Iterator<Room> iterator = children.iterator(); iterator.hasNext();) {
			Room room = (Room) iterator.next();
			room.setActive(active);
		}
	}

	/**
	 * Registers an handler
	 * 
	 * @param handler {@link Handler} instance
	 */
	public void register(Handler handler) {
		List<Room> rooms = Registry.instance().rooms;
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

	List<Room> getHierarchy() {
		List<Room> list = new ArrayList<>();
		list.add(this);
		for (int i = 0; i < children.size(); i++)
			list.addAll(children.get(i).getHierarchy());
		return list;
	}
	
	String getHierarchy2() {
		String s = "";
		List<Room> list = new ArrayList<>();
		list.add(this);
		s += ", " + getClass();
		for (int i = 0; i < children.size(); i++) {
			if (i <= 0) s += "{";
			list.addAll(children.get(i).getHierarchy());
			s += children.get(i).getHierarchy2();
			if (i < children.size()) s += "}";
		}
		return s;
	}

	/* Room utility */

	/** Deactivates every other room except for this one and it's children. */
	public void focus() {
		for (Room toCheck : Registry.DEFAULT_VIEW.getHierarchy()) {
			if (!toCheck.alwaysActive)
				toCheck.setActive(false);
		}
		active = true;
		setActive(true);
	}

	@Deprecated
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