/**
 * 
 */
package org.gamelib.net;

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
 * TODO add unified method/s for sending/sending to all clients using TCP/UDP - design issue
 * 
 * @author pwnedary
 */
public class Server implements EndPoint {
	private final Selector selector;
	private ServerSocketChannel serverChannel;
	private final SocketListener listener;
	private UDP udp;

	public Server(final SocketListener listener) throws IOException {
		this.listener = listener;
		selector = Selector.open();
	}

	public void open(InetSocketAddress tcpHost, InetSocketAddress udpHost)
			throws IOException {
		close();
		selector.wakeup();
		try {
			if (tcpHost != null) {
				serverChannel = selector.provider().openServerSocketChannel();
				serverChannel.configureBlocking(false);
				serverChannel.register(selector, SelectionKey.OP_ACCEPT);
				serverChannel.bind(tcpHost);
			}
			if (udpHost != null) {
				(udp = new UDP()).connectedAddress = udpHost;
				udp.bind(selector, udpHost);
			}
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
					if (fromConnection != null) {
						if ((ops & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
							try {
								Object object;
								while ((object = fromConnection.tcp.readObject()) != null)
									notifyReceived(object);
							} catch (IOException e) {
								fromConnection.close();
							}
						} else if ((ops & SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE) try {
							fromConnection.tcp.writeOperation();
						} catch (IOException e) {
							fromConnection.close();
						}
					} else if ((ops & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
						SocketChannel socketChannel = serverChannel.accept();
						if (socketChannel != null) {
							Connection connection = new Connection();
							connection.tcp.accept(selector, socketChannel).attach(connection); // Attach connection to accepted key
							notifyConnected(connection);
						}
					} else if (udp != null) {
						InetSocketAddress fromAddress = udp.readFromAddress();
						Object object = udp.readObject();
						notifyReceived(object);
					} else if (udp == null) selectionKey.channel().close();
				} catch (CancelledKeyException e) {
					if (fromConnection != null) fromConnection.close();
					else selectionKey.channel().close();
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
