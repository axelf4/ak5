/**
 * 
 */
package org.gamelib.network;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Axel
 * 
 */
public abstract class ConnectionImpl implements Runnable {

	protected List<SocketListener> listeners = new ArrayList<SocketListener>();
	public final Queue<Object> buffer = new LinkedList<>();
	public Thread thread;

	public void addSocketListener(SocketListener listener) {
		listeners.add(listener);
	}
	
	public void send(Object object) {
		buffer.add(object);
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		/*Thread moribund = thread;
		thread = null;
		// Thread.currentThread().interrupt();
		moribund.interrupt();*/
	}

}
