/**
 * 
 */
package org.gamelib.util.net;

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
	InetSocketAddress tcpAddress, udpAddress;
	public InetSocketAddress udpRemoteAddress;

	public Connection(final SocketListener listener) {
		this.listener = listener;
	}

	public Connection() {
		this(null);
	}

	public int sendTCP(Object object) throws IOException {
		if (object == null) throw new IllegalArgumentException("object cannot be null");
		int length = tcp.send(object);
		if (length == 0) throw new IOException(this + " TCP had nothing to send.");
		return length;
	}

	public int sendUDP(Object object) throws IOException {
		if (object == null) throw new IllegalArgumentException("object cannot be null");
		if (udp == null) throw new IllegalStateException("UDP is not connected.");
		if (udpRemoteAddress == null) throw new SocketException("Connection is closed.");
		int length = udp.send(object, udpRemoteAddress);
		if (length == 0) throw new IOException(this + " UDP had nothing to send.");
		else if (length == -1) throw new IOException(this + " was unable to send.");
		return length;
	}

	public void close() throws IOException {
		boolean wasConnected = isConnected;
		isConnected = false;
		if (tcp != null) tcp.close();
		if (udp != null && udp.connectedAddress != null) udp.close();
		if (wasConnected) notifyDisconnected(this);
	}

	/** Returns the IP address and port of the remote end of the TCP connection, or null if this connection is not connected. */
	public InetSocketAddress getRemoteTCPAddress() {
		return tcpConnected() ? (InetSocketAddress) tcp.socketChannel.socket().getRemoteSocketAddress() : null;
	}

	/** Returns the IP address and port of the remote end of the UDP connection, or null if this connection is not connected. */
	public InetSocketAddress getRemoteUDPAddress() {
		return udpConnected() ? udpRemoteAddress : null;
	}

	public boolean closed() {
		return !isConnected;
	}

	public boolean tcpConnected() {
		return tcp != null && tcp.socketChannel.isConnected();
	}

	public boolean udpConnected() {
		return udp != null && udpRemoteAddress != null;
	}

	protected void notifyConnected(Connection connection) throws IOException {
		isConnected = true;
		if (listener != null) listener.connected(connection);
	}

	protected void notifyDisconnected(Connection connection) throws IOException {
		if (listener != null) listener.disconnected(connection);
	}

	protected void notifyReceived(Object object) throws IOException {
		if (listener != null) listener.received(object);
	}

	protected void notifySent(Object object) throws IOException {
		if (listener != null) listener.sent(object);
	}
}
