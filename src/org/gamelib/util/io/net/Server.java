/**
 * 
 */
package org.gamelib.util.io.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * TODO add unified method/s for sending/sending to all clients using TCP/UDP - design issue
 * 
 * @author pwnedary
 */
public class Server implements EndPoint {
	private final Selector selector;
	private ServerSocketChannel serverChannel;
	private final SocketListener listener;
	private UDP udp;
	// private final Map<Integer, Connection> connections = new HashMap<>();
	private final List<Connection> connections = new LinkedList<>();

	public Server(final SocketListener listener) throws IOException {
		this.listener = listener;
		selector = Selector.open();
	}

	public void open(int tcpPort, int udpPort) throws IOException {
		open(new InetSocketAddress(tcpPort), new InetSocketAddress(udpPort));
	}

	public void open(InetSocketAddress tcpPort, InetSocketAddress udpPort)
			throws IOException {
		close();
		selector.wakeup();
		try {
			if (tcpPort != null) {
				serverChannel = selector.provider().openServerSocketChannel();
				serverChannel.configureBlocking(false);
				serverChannel.register(selector, SelectionKey.OP_ACCEPT);
				serverChannel.bind(tcpPort);
			}
			if (udpPort != null) (udp = new UDP()).bind(selector, udpPort);
		} catch (IOException e) {
			close();
			throw e;
		}
	}

	public void update() throws IOException {
		int select = selector.selectNow();
		if (select != 0) {
			Set<SelectionKey> keys = selector.selectedKeys();
			for (Iterator<SelectionKey> iterator = keys.iterator(); iterator.hasNext();) {
				SelectionKey selectionKey = iterator.next();
				iterator.remove();
				Connection fromConnection = (Connection) selectionKey.attachment();
				int ops = selectionKey.readyOps();
				try {
					if ((ops & SelectionKey.OP_READ) == SelectionKey.OP_READ) if (fromConnection != null && selectionKey.channel() == fromConnection.tcp.socketChannel) {
						Object object;
						while ((object = fromConnection.tcp.readObject()) != null)
							notifyReceived(fromConnection, object);
					} else if (udp == null) selectionKey.channel().close();
					else if (selectionKey.channel() == udp.datagramChannel) {
						InetSocketAddress fromAddress = udp.readFromAddress();
						Object object = udp.readObject();
						if (fromConnection == null) {
							for (Connection connection : connections)
								if ((connection.getRemoteUDPAddress() != null && fromAddress.equals(connection.getRemoteUDPAddress())) || (connection.getRemoteTCPAddress() != null && fromAddress.getAddress().equals(connection.getRemoteTCPAddress().getAddress()))) {
									fromConnection = connection;
									break;
								}
							if (fromConnection == null) connections.add(fromConnection = new Connection());
						}
						if (object instanceof RegisterUDP) fromConnection.udpRemoteAddress = fromAddress;
						notifyReceived(fromConnection, object);
					} else if ((ops & SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE && fromConnection != null) fromConnection.tcp.writeOperation();
					else if ((ops & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
						SocketChannel socketChannel = serverChannel.accept();
						if (socketChannel != null) {
							for (Connection connection : connections)
								if (connection.getRemoteUDPAddress() != null && fromConnection.getRemoteTCPAddress().getAddress().equals(connection.getRemoteUDPAddress().getAddress())) {
									fromConnection = connection; // Found previous udp connection from peer
									break;
								}
							if (fromConnection == null) connections.add(fromConnection = new Connection());
							(fromConnection.tcp = new TCP()).accept(selector, socketChannel).attach(fromConnection); // Attach connection to accepted key
							notifyConnected(fromConnection);
						}
					}
				} catch (CancelledKeyException e) {
					if (fromConnection != null) {
						connections.remove(fromConnection);
						fromConnection.close();
					} else selectionKey.channel().close();
				} catch (IOException e) {
					e.printStackTrace();
					fromConnection.close();
				}
			}
		}
	}

	@Override
	public void close() throws IOException {
		if (serverChannel != null) serverChannel.close();

		selector.wakeup();
		selector.selectNow(); // Select one last time to complete closing the socket.
	}

	protected void notifyConnected(Connection connection) throws IOException {
		if (listener != null) listener.connected(connection);
	}

	protected void notifyDisconnected(Connection connection) throws IOException {
		if (listener != null) listener.disconnected(connection);
	}

	protected void notifyReceived(Connection connection, Object object)
			throws IOException {
		if (!(object instanceof FrameworkMessage)) {
			if (listener != null) listener.received(object);
		} else if (object instanceof RegisterUDP) {
			// InetSocketAddress udpRemoteAddress = ((RegisterUDP) object).udpRemoteAddress;
			/*
			 * for (Connection connection2 : connections) { if (connection2.tcpAddress.getHostString() == udpRemoteAddress.getHostString()) { connection = connection2; break; } } if (connection == null) { connections.add(connection = new Connection()); connection.udp = udp; } notifyConnected(connection);
			 */
			connection.udp = udp;
			connection.sendUDP(new RegisterUDP(new InetSocketAddress(9999)));
			notifyConnected(connection);
		} else if (object instanceof DiscoverHost) {}
	}

	protected void notifySent(Object object) throws IOException {
		if (listener != null) listener.sent(object);
	}
}
