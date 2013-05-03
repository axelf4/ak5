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

/**
 * The registry holding all of the handlers. An handler should be registered
 * with {@link #register(Object)}.
 * 
 * @author Axel
 * @since 0.0.1
 * 
 */
public class HandlerRegistry {

	/** Used of all undefined handlers. */
	public static final Room DEFAULT_VIEW = new Room().setAlwaysActive(true);

	private static HandlerRegistry instance;
	private Map<Class<? extends Event>, CopyOnWriteArrayList<Handler>> handlers;
	public List<Room> rooms;

	/**
	 * 
	 */
	private HandlerRegistry() {
		// instance = this; // remove this line
		handlers = new HashMap<Class<? extends Event>, CopyOnWriteArrayList<Handler>>(1);
		(rooms = new ArrayList<Room>()).add(DEFAULT_VIEW);
		// (rooms = new Node<Room>(null)).add(new Node<Room>(DEFAULT_VIEW));
	}

	/**
	 * @return the singleton instance
	 */
	public static HandlerRegistry instance() {
		return instance == null ? instance = new HandlerRegistry() : instance;
	}

	/**
	 * Registers an handler
	 * 
	 * @param handler {@link Handler} instance
	 */
	/*public void register(Handler handler, Room room) {
		if (room == null)
			throw new IllegalArgumentException("room cannot be null");
		/* if (!rooms.contains(room)) throw new
		 * RuntimeException("must add the room first"); *3/
		if (!rooms.contains(room))
			rooms.add(room);
		Map<Class<? extends Event>, CopyOnWriteArrayList<Handler>> handlers = room.handlers;
		ArrayList<Class<? extends Event>> list = new ArrayList<Class<? extends Event>>();
		handler.handlers(list);
		for (Iterator<Class<? extends Event>> iterator = list.iterator(); iterator.hasNext();) {
			Class<? extends Event> type = iterator.next();
			if (!handlers.containsKey(type))
				handlers.put(type, new CopyOnWriteArrayList<Handler>());
			handlers.get(type).add(handler);
		}
	}*/
	public void register(Handler handler, Room room) {
		if (room == null)
			throw new IllegalArgumentException("room cannot be null");
		/* if (!rooms.contains(room)) throw new
		 * RuntimeException("must add the room first"); */
		// register room
		if (!rooms.contains(room)) {
			rooms.add(room);
			/*Node<Room> node = new Node<Room>(room);
			rooms.add(node);*/
		}
		Map<Class<? extends Event>, CopyOnWriteArrayList<Handler>> handlers = room.handlers;
		ArrayList<Class<? extends Event>> list = new ArrayList<Class<? extends Event>>();
		handler.handlers(list);
		for (Iterator<Class<? extends Event>> iterator = list.iterator(); iterator.hasNext();) {
			Class<? extends Event> type = iterator.next();
			if (!handlers.containsKey(type))
				handlers.put(type, new CopyOnWriteArrayList<Handler>());
			handlers.get(type).add(handler);
		}
	}

	/**
	 * Registers an handler.
	 * 
	 * @param handler {@link Handler} instance
	 */
	public void register(Handler handler) {
		/* ArrayList<Class<? extends Event>> list = new ArrayList<Class<?
		 * extends Event>>(); handler.handlers(list); for (Iterator<Class<?
		 * extends Event>> iterator = list.iterator(); iterator.hasNext();) {
		 * Class<? extends Event> type = iterator.next(); if
		 * (!handlers.containsKey(type)) handlers.put(type, new
		 * CopyOnWriteArrayList<Handler>()); handlers.get(type).add(handler); } */
		register(handler, DEFAULT_VIEW);
	}

	/**
	 * Unregisters an handler. NOTE: for now only works if type == null
	 * 
	 * @param handler {@link Handler} instance
	 * @param type the event to unregister for, if null remove all
	 */
	public void unregister(Handler handler, Class<? extends Event> type) {
		if (type == null) {
			for (Room room : rooms) {
				for (CopyOnWriteArrayList<Handler> list : room.handlers.values()) {
					list.remove(handler);
				}
			}
		} else if (!handlers.get(type).remove(handler))
			throw new RuntimeException("no handler of type " + type + " in class " + handler.getClass());
	}

	public synchronized void dispatch(Event event) {
		// Log.startProfiling("invoke");
		/*for (int i = 0; i < rooms.size(); i++) {
			Room room = rooms.get(i);
			if (room == null || !room.active)
				continue;
			Map<Class<? extends Event>, CopyOnWriteArrayList<Handler>> handlers = room.handlers;

			if (!handlers.containsKey(event.getClass()))
				continue;
			for (Iterator<Handler> iterator = handlers.get(event.getClass()).iterator(); iterator.hasNext();) {
				Handler handler = (Handler) iterator.next();
				handler.handle(event);
			}
		}*/
		/*for (int i = 0; i < rooms.size(); i++) {
			Room room = rooms.get(i);*/
		for (Room room : rooms) {
			if (room == null || !room.active)
				continue;
			Map<Class<? extends Event>, CopyOnWriteArrayList<Handler>> handlers = room.handlers;

			if (!handlers.containsKey(event.getClass()))
				continue;
			for (Iterator<Handler> iterator = handlers.get(event.getClass()).iterator(); iterator.hasNext();) {
				Handler handler = (Handler) iterator.next();
				handler.handle(event);
			}
		}
		// Log.debug("invoked " + event + ": time: " +
		// Log.stopProfiling("invoke"));
	}

	public synchronized void invokeHandlers(Event event, Room room) {
		if (room == null || !room.active)
			return;
		/*Map<Class<? extends Event>, CopyOnWriteArrayList<Handler>> handlers = room.handlers;

		if (!handlers.containsKey(event.getClass()))
			return;
		for (Iterator<Handler> iterator = handlers.get(event.getClass()).iterator(); iterator.hasNext();) {
			Handler handler = (Handler) iterator.next();
			handler.handle(event);
		}*/
	}
	
	public void calculateViews() {
		List<Room> list = rooms;
		for (int i = 0; i < list.size(); i++) {
			Room view1 = list.get(i);
			Rectangle rectangle1 = view1.getRectangle();
			if (rectangle1 == null)
				continue;
			for (int j = i - 1; j > 0; j--) {
				Room view2 = list.get(j);
				Rectangle rectangle2 = view2.getRectangle();
				if (rectangle2 == null)
					continue;
				view2.active = rectangle1.contains(rectangle2);
			}
		}
	}

}
