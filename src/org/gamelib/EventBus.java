/**
 * 
 */
package org.gamelib;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.gamelib.Handler.Event;

/**
 * The registry holding all of the handlers. An handler should be registered with {@link #register(Object)}. <br />
 * TODO add singleton instance in {@link Game}
 * @author pwnedary
 * @since 0.0.1
 */
public class EventBus {
	/** Root of group hierarchy */
	public static final Group MAIN_GROUP = new Group(null).setAlwaysActive(true);
	/** The singleton instance */
	private static EventBus instance;

	// private EventBus() {}

	/**
	 * @return the singleton instance
	 */
	public static EventBus instance() {
		return instance == null ? instance = new EventBus() : instance;
	}

	/** Registers an <code>handler</code> for handling events for <code>group</code>. */
	public void register(Group group, Handler handler) {
		if (group == null || handler == null) throw new IllegalArgumentException("arguments cannot be null");
		// group.handlers.get(Event.class).add(handler); // wildcard for every handler
		for (Iterator<List<Handler>> iterator = group.handlers.values().iterator(); iterator.hasNext();)
			iterator.next().add(handler);
	}

	/** Registers an <code>handler</code> for handling events. */
	public void register(Handler handler) {
		register(MAIN_GROUP, handler);
	}

	public void unregister(Group group, Handler handler) {
		for (Iterator<List<Handler>> iterator = group.handlers.values().iterator(); iterator.hasNext();) {
			List<Handler> handlers = (List<Handler>) iterator.next();
			handlers.remove(handler);
		}
	}

	public void unregister(Handler handler) {
		for (Group group : MAIN_GROUP.getHierarchy())
			unregister(group, handler);
	}

	/**
	 * Fires <code>event</code> to all handlers listening for it.
	 * @param event the event to fire
	 */
	public void dispatch(Event event) {
		for (Group group : MAIN_GROUP.getHierarchy()) {
			if (group == null || !group.isActive()) continue;

			Class<? extends Event> eventType = event.getClass();
			List<Handler> handlers;
			if (!group.handlers.containsKey(eventType)) group.handlers.put(eventType, handlers = new LinkedList<>(group.handlers.get(Event.class)));
			else handlers = group.handlers.get(eventType);

			for (Iterator<Handler> iterator = handlers.iterator(); iterator.hasNext() && !event.cancelled();)
				if (!iterator.next().handle(event)) iterator.remove();
		}
	}

}
