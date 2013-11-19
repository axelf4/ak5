/**
 * 
 */
package org.gamelib.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author pwnedary
 */
public class Server implements EndPoint {

	Selector selector;
	ServerSocketChannel serverChannel;
	SocketListener listener;

	SelectionKey selectionKey;
	SocketChannel socketChannel;
	TCP tcp;

	public Server() throws IOException {
		selector = Selector.open();
	}

	public void open(InetSocketAddress tcpPort) throws IOException {
		selector.wakeup();
		try {
			serverChannel = selector.provider().openServerSocketChannel();
			serverChannel.bind(tcpPort);
			serverChannel.configureBlocking(false);
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			e.printStackTrace();
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

				if ((ops & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
					Object object;
					while ((object = fromConnection.tcp.readObject()) != null)
						notifyReceived(object);
				} else if ((ops & SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE) fromConnection.tcp.writeOperation();
				else if ((ops & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
					try {
						Connection connection = new Connection();
						SocketChannel socketChannel = serverChannel.accept();
						if (socketChannel != null) {
							SelectionKey key = connection.tcp.accept(selector, socketChannel);
							key.attach(connection);
							notifyConnected(connection);
						}
					} catch (CancelledKeyException e) {} // connection is closed
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
