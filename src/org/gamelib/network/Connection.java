/**
 * 
 */
package org.gamelib.network;

import java.io.IOException;

/**
 * A connection between a {@link Client} and a {@link Server}.
 * @author pwnedary
 */
public class Connection {

	SocketListener listener;
	TCP tcp;
	UDP udp;
	boolean isConnected;

	public Connection() {
		tcp = new TCP();
	}

	public void sendTCP(Object object) throws IOException {
		tcp.send(object);
	}

	public void close() throws IOException {
		boolean wasConnected = isConnected;
		isConnected = false;
		tcp.close();
		// close udp
		if (wasConnected) {
			// notify disconnected
		}
	}
	
	public boolean closed() {
		return !isConnected;
	}

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
}
