/**
 * 
 */
package org.gamelib;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.gamelib.Handler.Event;

/**
 * The registry holding all of the handlers. An handler should be registered
 * with {@link #register(Object)}.
 * TODO make handle return boolean, and unregister from event if return false;
 * 
 * @author Axel
 * @since 0.0.1
 * 
 */
public class Registry {

	/** Used of all undefined handlers. */
	public static final Group DEFAULT_VIEW = new Group().setAlwaysActive(true);

	private static Registry instance;
	private Map<Class<? extends Event>, CopyOnWriteArrayList<Handler>> handlers;
	public List<Group> groups;

	/**
	 * 
	 */
	private Registry() {
		// instance = this; // remove this line
		handlers = new WeakHashMap<Class<? extends Event>, CopyOnWriteArrayList<Handler>>(1);
		(groups = new ArrayList<Group>()).add(DEFAULT_VIEW);
		// (groups = new Node<Group>(null)).add(new Node<Group>(DEFAULT_VIEW));
	}

	/**
	 * @return the singleton instance
	 */
	public static Registry instance() {
		return instance == null ? instance = new Registry() : instance;
	}

	/**
	 * Registers an handler
	 * 
	 * @param handler {@link Handler} instance
	 */
	/*public void register(Handler handler, Group group) {
		if (group == null)
			throw new IllegalArgumentException("group cannot be null");
		/* if (!groups.contains(group)) throw new
		 * RuntimeException("must add the group first"); *3/
		if (!groups.contains(group))
			groups.add(group);
		Map<Class<? extends Event>, CopyOnWriteArrayList<Handler>> handlers = group.handlers;
		ArrayList<Class<? extends Event>> list = new ArrayList<Class<? extends Event>>();
		handler.handlers(list);
		for (Iterator<Class<? extends Event>> iterator = list.iterator(); iterator.hasNext();) {
			Class<? extends Event> type = iterator.next();
			if (!handlers.containsKey(type))
				handlers.put(type, new CopyOnWriteArrayList<Handler>());
			handlers.get(type).add(handler);
		}
	}*/
	public void register(Handler handler, Group group) {
		if (group == null)
			throw new IllegalArgumentException("group cannot be null");
		/* if (!groups.contains(group)) throw new
		 * RuntimeException("must add the group first"); */
		// register group
		/*if (!groups.contains(group)) {
			groups.add(group);
			/*Node<Group> node = new Node<Group>(group);
			groups.add(node);*2/
		}*/
		Map<Class<? extends Event>, CopyOnWriteArrayList<Handler>> handlers = group.handlers;
		ArrayList<Class<? extends Event>> list = new ArrayList<Class<? extends Event>>(); // get events to register to
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
			for (Group group : groups) {
				for (CopyOnWriteArrayList<Handler> list : group.handlers.values()) {
					list.remove(handler);
				}
			}
		} else if (!handlers.get(type).remove(handler))
			throw new RuntimeException("no handler of type " + type + " in class " + handler.getClass());
	}

	@SuppressWarnings("unchecked")
	public synchronized void dispatch(Event event) {
		// Log.startProfiling("invoke");
		/*for (int i = 0; i < groups.size(); i++) {
			Group group = groups.get(i);
			if (group == null || !group.active)
				continue;
			Map<Class<? extends Event>, CopyOnWriteArrayList<Handler>> handlers = group.handlers;

			if (!handlers.containsKey(event.getClass()))
				continue;
			for (Iterator<Handler> iterator = handlers.get(event.getClass()).iterator(); iterator.hasNext();) {
				Handler handler = (Handler) iterator.next();
				handler.handle(event);
			}
		}*/
		/*for (int i = 0; i < groups.size(); i++) {
			Group group = groups.get(i);*/
		for (Group group : DEFAULT_VIEW.getHierarchy()) { // groups
			if (group == null || !group.isActive())
				continue;
			Map<Class<? extends Event>, CopyOnWriteArrayList<Handler>> handlers = group.handlers;

			/*if (!handlers.containsKey(event.getClass()))
				continue;
			for (Iterator<Handler> iterator = handlers.get(event.getClass()).iterator(); iterator.hasNext();) {
				Handler handler = (Handler) iterator.next();
				handler.handle(event);
			}*/
			
			Class<? extends Event> class1 = event.getClass();
			CopyOnWriteArrayList<Handler>[] values = (CopyOnWriteArrayList[]) handlers.values().toArray(new CopyOnWriteArrayList[handlers.values().size()]);
			Class<? extends Event>[] keys = (Class<? extends Event>[]) handlers.keySet().toArray(new Class[handlers.keySet().size()]);
			for (int i = 0; i < keys.length; i++) {
				Class<? extends Event> class2 = keys[i];
				if (class2.isAssignableFrom(class1)) {
					for (Iterator<Handler> iterator = values[i].iterator(); iterator.hasNext();) {
						Handler handler = (Handler) iterator.next();
						handler.handle(event);
					}
				}
			}
		}
		// Log.debug("invoked " + event + ": time: " +
		// Log.stopProfiling("invoke"));
	}

	@SuppressWarnings("unchecked")
	public synchronized void dispatch(Event event, Group group) {
		if (group == null || !group.isActive())
			return;
		Map<Class<? extends Event>, CopyOnWriteArrayList<Handler>> handlers = group.handlers;

		/*if (!handlers.containsKey(event.getClass()))
			return;
		for (Iterator<Handler> iterator = handlers.get(event.getClass()).iterator(); iterator.hasNext();) {
			Handler handler = (Handler) iterator.next();
			handler.handle(event);
		}*/

		/*if (!handlers.containsKey(event.getClass()))
			continue;
		for (Iterator<Handler> iterator = handlers.get(event.getClass()).iterator(); iterator.hasNext();) {
			Handler handler = (Handler) iterator.next();
			handler.handle(event);
		}*/
		
		Class<? extends Event> class1 = event.getClass();
		CopyOnWriteArrayList<Handler>[] values = (CopyOnWriteArrayList[]) handlers.values().toArray(new CopyOnWriteArrayList[handlers.values().size()]);
		Class<? extends Event>[] keys = (Class<? extends Event>[]) handlers.keySet().toArray(new Class[handlers.keySet().size()]);
		for (int i = 0; i < keys.length; i++) {
			Class<? extends Event> class2 = keys[i];
			if (class2.isAssignableFrom(class1)) {
				for (Iterator<Handler> iterator = values[i].iterator(); iterator.hasNext();) {
					Handler handler = (Handler) iterator.next();
					handler.handle(event);
				}
			}
		}
	}
	
	public void calculateViews() {
		List<Group> list = groups;
		for (int i = 0; i < list.size(); i++) {
			Group view1 = list.get(i);
			Rectangle rectangle1 = view1.getRectangle();
			if (rectangle1 == null)
				continue;
			for (int j = i - 1; j > 0; j--) {
				Group view2 = list.get(j);
				Rectangle rectangle2 = view2.getRectangle();
				if (rectangle2 == null)
					continue;
				view2.setActive(rectangle1.contains(rectangle2));
			}
		}
	}

}
