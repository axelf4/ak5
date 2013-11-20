/**
 * 
 */
package org.gamelib.network;

import java.io.IOException;
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
public class Client extends Connection {
	Selector selector;

	public Client() throws IOException {
		selector = Selector.open();
	}

	public void open(InetSocketAddress address) throws IOException {
		try {
			selector.wakeup();
			tcp.connect(selector, address);
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
				try {
					int ops = selectionKey.readyOps();
					if ((ops & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
						if (selectionKey.attachment() == tcp) {
							Object object;
							while ((object = tcp.readObject()) != null) {
								notifyReceived(object);
							}
						}
					} else if ((ops & SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE) tcp.writeOperation();
					else if ((ops & SelectionKey.OP_CONNECT) == SelectionKey.OP_CONNECT) {
						((SocketChannel) selectionKey.channel()).finishConnect();
						selectionKey.interestOps(SelectionKey.OP_READ);
						Object attachment = selectionKey.attachment();
						System.out.println("Client connected to Server, attachment: " + attachment);
						notifyConnected(this);
					}
				} catch (CancelledKeyException e) {} // connection is closed
			}
		}
	}

	public void close() throws IOException {
		super.close();

		selector.wakeup();
		selector.selectNow(); // Select one last time to complete closing the socket.
	}
}
