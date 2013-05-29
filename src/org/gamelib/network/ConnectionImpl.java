/**
 * 
 */
package org.gamelib.network;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;


/**
 * @author Axel
 *
 */
public abstract class ConnectionImpl implements Connection {

	public final Queue<Object> buffer = new LinkedList<>();
	SocketListener listener;
	
	@Override
	public void setListener(SocketListener listener) {
		this.listener = listener;
	}
	
	protected void notifyConnected(Connection connection) {
		if (listener != null) listener.connected(connection);
	}
	
	protected void notifyDisconnected(Connection connection) {
		if (listener != null) listener.disconnected(connection);
	}
	
	protected void notifyReceived(Object object) {
		if (listener != null) listener.received(object);
	}
	
	protected void notifySent(Object object) {
		if (listener != null) listener.sent(object);
	}
	
	@Override
	public void send(Serializable obj) {
		if (obj == null) throw new IllegalArgumentException("object can't be null");
		buffer.add(obj);
	}
	
}
