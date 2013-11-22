/**
 * 
 */
package org.gamelib.network;

import java.io.IOException;
import java.net.SocketException;

/**
 * A connection between a {@link Client} and a {@link Server}.
 * 
 * @author pwnedary
 */
public class Connection {

	SocketListener listener;
	TCP tcp;
	UDP udp;
	volatile boolean isConnected;

	public Connection() {
		tcp = new TCP();
	}

	public void sendTCP(Object object) throws IOException {
		if (object == null) throw new IllegalArgumentException("object cannot be null");
		tcp.send(object);
	}

	public void sendUDP(Object object) throws IOException {
		if (object == null) throw new IllegalArgumentException("object cannot be null");
		if (udp.connectedAddress == null) throw new SocketException("Connection is closed.");
		udp.send(object, udp.connectedAddress);
	}

	public void close() throws IOException {
		boolean wasConnected = isConnected;
		isConnected = false;
		tcp.close();
		udp.close();
		if (wasConnected) {
			notifyDisconnected(null); // notify disconnected
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
