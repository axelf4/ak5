/**
 * 
 */
package org.gamelib.util.io.net;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.gamelib.Handler;

/** @author pwnedary */
public class Client extends Connection implements EndPoint {
	Selector selector;
	private Object udpRegistrationLock = new Object();
	boolean udpRegistered = false;

	public Client(final Handler handler) throws IOException {
		super(handler);
		selector = Selector.open();
	}

	public void open(InetAddress host, int tcpPort, int udpPort)
			throws IOException {
		open(new InetSocketAddress(host, tcpPort), new InetSocketAddress(host, udpPort));
	}

	public void open(InetSocketAddress tcpAddress, InetSocketAddress udpAddress)
			throws IOException {
		int timeout = 5000;
		final long endTime = System.currentTimeMillis() + timeout;
		try {
			if (tcpAddress != null) if ((tcp = new TCP(8192, 2048)).connect(selector, this.tcpAddress = tcpAddress)) {
				notifyConnected(this);
				// tcp.selectionKey.interestOps(SelectionKey.OP_READ);
			}
			if (udpAddress != null) {
				(udp = new UDP()).connect(selector, this.udpRemoteAddress = this.udpAddress = udpAddress);

				ExecutorService e = Executors.newSingleThreadExecutor();
				e.execute(new Runnable() {
					@Override
					public void run() {
						synchronized (udpRegistrationLock) {
							while (!udpRegistered && System.currentTimeMillis() < endTime) {
								try {
									sendUDP(new RegisterUDP(new InetSocketAddress(2222)));
									udpRegistrationLock.wait(500);
								} catch (Throwable e) {
									e.printStackTrace();
								}
							}
						}
					}
				});
				e.shutdown();
			}
		} catch (IOException e) {
			close();
			throw e;
		}
	}

	public void update() throws IOException {
		if (selector.selectNow() != 0) {
			Set<SelectionKey> keys = selector.selectedKeys();
			for (Iterator<SelectionKey> iterator = keys.iterator(); iterator.hasNext();) {
				SelectionKey selectionKey = iterator.next();
				iterator.remove();
				if (!selectionKey.isValid()) continue;
				int ops = selectionKey.readyOps();
				try {
					if ((ops & SelectionKey.OP_READ) != 0) {
						if (selectionKey.channel() == tcp.socketChannel) {
							Object object;
							while ((object = tcp.readObject()) != null)
								notifyReceived(object);
						} else if (udp == null) selectionKey.channel().close();
						else if (selectionKey.channel() == udp.datagramChannel) {
							if (udp.readFromAddress() != null) {
								Object object = udp.readObject();
								if (object instanceof RegisterUDP) {
									synchronized (udpRegistrationLock) {
										udpRegistered = true;
										udpRegistrationLock.notifyAll();
									}
									notifyConnected(this);
								} else if (object != null) notifyReceived(object);
							}
						}
					}
					if ((ops & SelectionKey.OP_WRITE) != 0) tcp.writeOperation();
					if ((ops & SelectionKey.OP_CONNECT) != 0) {
						if (((SocketChannel) selectionKey.channel()).finishConnect()) {
							selectionKey.interestOps(SelectionKey.OP_READ);
							notifyConnected(this);
						}
					}
				} catch (CancelledKeyException e) { // Connection is closed
				} catch (ConnectException e) {
					close();
					throw new IOException("Unable to connect to: " + tcpAddress, e);
				} catch (IOException e) {
					close();
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
