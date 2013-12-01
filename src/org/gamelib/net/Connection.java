/**
 * 
 */
package org.gamelib.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 * A connection between a {@link Client} and a {@link Server}.
 * 
 * @author pwnedary
 */
public class Connection {

	final SocketListener listener;
	TCP tcp;
	UDP udp;
	volatile boolean isConnected;
	InetSocketAddress tcpHost, udpHost;
	
	public Connection(final SocketListener listener) {
		this.tcp = new TCP();
		this.listener = listener;
	}

	public Connection() {
		this(null);
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
		if (tcp != null) tcp.close();
		if (udp != null && udp.connectedAddress != null) udp.close();
		if (wasConnected) {
			notifyDisconnected(null);
		}
	}

	public boolean closed() {
		return !isConnected;
	}

	protected void notifyConnected(Connection connection) {
		isConnected = true;
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
