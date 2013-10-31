/**
 * 
 */
package org.gamelib;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.gamelib.Handler.Event;

/**
 * The registry holding all of the handlers. An handler should be registered with {@link #register(Object)}. TODO make handle return boolean, and unregister from event if return false;
 * @author Axel
 * @since 0.0.1
 */
public class Registry {

	/** Root of group hierarchy */
	public static final Group MAIN_GROUP = new Group().setAlwaysActive(true);
	/** The singleton instance */
	private static Registry instance;

	private Registry() {}

	/**
	 * @return the singleton instance
	 */
	public static Registry instance() {
		return instance == null ? instance = new Registry() : instance;
	}

	/** The *NEW* way of registering handlers. */
	public void register(Group group, Handler handler) {
		if (group == null || handler == null) throw new IllegalArgumentException("arguments cannot be null");
		group.handlers.get(Event.class).add(handler); // wildcard for every handler
	}

	/** The *NEW* way of registering handlers. */
	public void register(Handler handler) {
		register(MAIN_GROUP, handler);
	}

	public void unregister(Group group, Handler handler) {
		for (Iterator<List<Handler>> iterator = group.handlers.values().iterator(); iterator.hasNext();) {
			List<Handler> handlers = (List<Handler>) iterator.next();
			handlers.remove(handler);
		}
	}

	/**
	 * Fires <code>event</code> to every handler listening to it.
	 * @param event the event to fire
	 */
	public void dispatch(Event event) {
		for (Group group : MAIN_GROUP.getHierarchy()) {
			if (group == null || !group.isActive()) continue;

			Class<? extends Event> eventType = event.getClass();
			List<Handler> handlers;
			if (!group.handlers.containsKey(eventType)) group.handlers.put(eventType, handlers = new LinkedList<>(group.handlers.get(Event.class)));
			else handlers = group.handlers.get(eventType);

			for (Iterator<Handler> iterator = handlers.iterator(); iterator.hasNext();) {
				Handler handler = (Handler) iterator.next();
				if (!handler.handle(event) && event.unregisterAfterNoInterrest()) iterator.remove();
				if (event.cancelled) break;
			}
		}
	}

}
