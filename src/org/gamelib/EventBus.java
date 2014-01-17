/**
 * 
 */
package org.gamelib;

/**
 * The registry holding all of the handlers. An handler should be registered with {@link #register(Object)}. <br />
 * TODO add singleton instance in {@link Game}
 * 
 * @author pwnedary
 * @since 0.0.1
 */
public class EventBus extends Group {
	/** The default instance */
	private static EventBus instance;

	/* * Root of group hierarchy */
	// public final Group main = new Group(null).setAlwaysActive(true);

	// private EventBus() {}
	public EventBus() {
		setAlwaysActive(true);
	}

	/**
	 * Returns the default instance
	 * 
	 * @return the default instance
	 */
	public static EventBus instance() {
		return instance == null ? instance = new EventBus() : instance;
	}

	/*
	 *  * Registers an <code>handler</code> for handling events. * / public void register(Handler handler) { main.register(handler); } public void unregister(Handler handler) { main.unregister(handler); }
	 */

	/**
	 * Fires <code>event</code> to all handlers listening for it.
	 * 
	 * @param event the event to fire
	 */
	public void dispatch(Event event) {
		handle(event); // main.handle(event);
	}

}
