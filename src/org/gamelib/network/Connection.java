/**
 * 
 */
package org.gamelib.network;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Axel
 * 
 */
public abstract class Connection implements Runnable {

	public static final short DEFAULT_PORT = 2222;

	protected List<SocketListener> listeners = new ArrayList<SocketListener>();
	public final List<Object> buffer = new ArrayList<Object>();
	protected Thread thread;

	/**
	 * 
	 */
	public Connection() {
		// TODO Auto-generated constructor stub
	}

	public Connection addSocketListener(SocketListener listener) {
		listeners.add(listener);
		return this;
	}
	
	public void send(Object object) {
		// TODO Check if socket is closed.
		buffer.add(object);
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		Thread moribund = thread;
		thread = null;
		// Thread.currentThread().interrupt();
		moribund.interrupt();
	}

}
