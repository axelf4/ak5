/**
 * 
 */
package org.gamelib.network;

/**
 * Used to be notified about connection events.
 * 
 * @author Axel
 */
public class SocketListener {
	/**
	 * Called when the remote end has been connected. This will be invoked
	 * before any objects are received by {@link #received(Connection, Object)}.
	 * This will be invoked on the same thread as {@link Client#update(int)} and
	 * {@link Server#update(int)}. This method should not block for long periods
	 * as other network activity will not be processed until it returns.
	 */
	public void connected(Connection connection) {
	}

	/**
	 * Called when the remote end is no longer connected. There is no guarantee
	 * as to what thread will invoke this method.
	 */
	public void disconnected(Connection connection) {
	}

	/**
	 * Called when an object has been received from the remote end of the
	 * connection. This will be invoked on the same thread as
	 * {@link Client#update(int)} and {@link Server#update(int)}. This method
	 * should not block for long periods as other network activity will not be
	 * processed until it returns.
	 */
	public void received(Object object) {
	}
	
	/**
	 * Called when an object has been sent from the remote end of the
	 * connection. This will be invoked on the same thread as
	 * {@link Client#update(int)} and {@link Server#update(int)}. This method
	 * should not block for long periods as other network activity will not be
	 * processed until it returns.
	 */
	public void sent(Object object) {
	}
}
