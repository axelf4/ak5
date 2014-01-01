/**
 * 
 */
package org.gamelib;

import org.gamelib.Handler.Event;

/**
 * The registry holding all of the handlers. An handler should be registered with {@link #register(Object)}. <br />
 * TODO add singleton instance in {@link Game}
 * @author pwnedary
 * @since 0.0.1
 */
public class EventBus {
	/** The default instance */
	private static EventBus instance;
	/** Root of group hierarchy */
	public final Group main = new Group(null).setAlwaysActive(true);

	// private EventBus() {}

	/**
	 * Returns the default instance
	 * @return the default instance
	 */
	public static EventBus instance() {
		return instance == null ? instance = new EventBus() : instance;
	}

	/** Registers an <code>handler</code> for handling events. */
	public void register(Handler handler) {
		main.register(handler);
	}

	public void unregister(Handler handler) {
		main.unregister(handler);
	}

	/**
	 * Fires <code>event</code> to all handlers listening for it.
	 * @param event the event to fire / public void dispatch2(Event event) { for (Group group : MAIN_GROUP.getChildren()) { if (group == null || !group.isActive()) continue; Class<? extends Event> eventType = event.getClass(); List<Handler> handlers; if (!group.handlers.containsKey(eventType)) group.handlers.put(eventType, handlers = new ArrayList<>(group.handlers.get(Event.class))); else handlers = group.handlers.get(eventType); // for (Iterator<Handler> iterator = handlers.iterator(); iterator.hasNext() && !event.cancelled();) // if (!iterator.next().handle(event)) iterator.remove(); for (int i = 0; i < handlers.size() && !event.cancelled(); i++) if (!handlers.get(i).handle(event)) handlers.remove(i); } }
	 */

	/**
	 * Fires <code>event</code> to all handlers listening for it.
	 * @param event the event to fire
	 */
	public void dispatch(Event event) {
		main.handle(event);
	}

}
