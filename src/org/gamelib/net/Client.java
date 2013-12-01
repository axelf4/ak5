/**
 * 
 */
package org.gamelib.net;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author pwnedary
 */
public class Client extends Connection implements EndPoint {
	Selector selector;

	public Client(final SocketListener listener) throws IOException {
		super(listener);
		selector = Selector.open();
	}

	public void open(InetSocketAddress tcpPort, InetSocketAddress udpPort)
			throws IOException {
		selector.wakeup();
		try {
			if (tcpPort != null) {
				(tcp = new TCP()).connect(selector, this.tcpHost = tcpPort);
			}
			if (udpPort != null) {
				selector.wakeup();
				(udp = new UDP()).connect(selector, this.udpHost = udpPort);
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
				int ops = selectionKey.readyOps();
				try {
					if ((ops & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
						Object object;
						if (selectionKey.attachment() == tcp) {
							while ((object = tcp.readObject()) != null)
								notifyReceived(object);
						} else {
							if (udp.readFromAddress() != null) {
								object = udp.readObject();
								if (object != null) notifyReceived(object);
							}
						}
					} else if ((ops & SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE) tcp.writeOperation();
					else if ((ops & SelectionKey.OP_CONNECT) == SelectionKey.OP_CONNECT) {
						if (((SocketChannel) selectionKey.channel()).finishConnect()) {
							selectionKey.interestOps(SelectionKey.OP_READ);
							notifyConnected(this);
						}
					}
				} catch (CancelledKeyException e) { // Connection is closed
				} catch (ConnectException e) {
					close();
					throw new IOException("Unable to connect to: " + tcpHost, e);
				}
			}
		}
	}

	public void close() throws IOException {
		super.close();

		selector.wakeup();
		selector.selectNow(); // Select one last time to complete closing the socket.
	}
}
