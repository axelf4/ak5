/**
 * 
 */
package org.gamelib.util.io.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;

import org.gamelib.Handler;
import org.gamelib.Handler.Event;
import org.gamelib.Handler.Event.EventImpl;

/** A connection between a {@link Client} and a {@link Server}.
 * 
 * @author pwnedary */
public class Connection {
	final Handler handler;
	TCP tcp;
	UDP udp;
	volatile boolean isConnected;
	InetSocketAddress tcpAddress, udpAddress;
	public InetSocketAddress udpRemoteAddress;

	public Connection(final Handler handler) {
		this.handler = handler;
	}

	public Connection() {
		this(null);
	}

	public int sendTCP(Object object) throws IOException {
		if (object == null) throw new IllegalArgumentException("object cannot be null");
		if (tcp == null) throw new IllegalStateException("TCP is not connected.");
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

	/** Returns the IP address and port of the remote end of the TCP connection, or null if this connection is not
	 * connected. */
	public InetSocketAddress getRemoteTCPAddress() throws IOException {
		return tcpConnected() ? (InetSocketAddress) tcp.socketChannel.socket().getRemoteSocketAddress() : null;
	}

	/** Returns the IP address and port of the remote end of the UDP connection, or null if this connection is not
	 * connected. */
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
		handler.handle(new Connection.Connected(connection));
	}

	protected void notifyDisconnected(Connection connection) throws IOException {
		handler.handle(new Connection.Disconnected(connection));
	}

	protected void notifyReceived(Object object) throws IOException {
		handler.handle(new Connection.Received(object));
	}

	/** Dispatched when the remote end has been connected. This method should not block for long periods as other network
	 * activity will not be processed until it returns. */
	public static class Connected extends EventImpl implements Event {
		public final Connection connection;

		public Connected(Connection connection) {
			this.connection = connection;
		}
	}

	/** Dispatched when the remote end is no longer connected. This method should not block for long periods as other
	 * network activity will not be processed until it returns. */
	public static class Disconnected extends EventImpl implements Event {
		public final Connection connection;

		public Disconnected(Connection connection) {
			this.connection = connection;
		}
	}

	/** Dispatched when an object has been received from the remote end of the connection. This method should not block
	 * for long periods as other network activity will not be processed until it returns. */
	public static class Received extends EventImpl implements Event {
		public final Object object;

		public Received(Object object) {
			this.object = object;
		}
	}
}
