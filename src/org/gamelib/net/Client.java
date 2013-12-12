/**
 * 
 */
package org.gamelib.net;

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

/**
 * @author pwnedary
 */
public class Client extends Connection implements EndPoint {
	Selector selector;
	private Object udpRegistrationLock = new Object();
	boolean udpRegistered = false;

	public Client(final SocketListener listener) throws IOException {
		super(listener);
		selector = Selector.open();
	}

	public void open(InetAddress host, int tcpPort, int udpPort)
			throws IOException {
		open(new InetSocketAddress(host, tcpPort), new InetSocketAddress(host, udpPort));
	}

	public void open(InetSocketAddress tcpAddress, InetSocketAddress udpAddress)
			throws IOException {
		// selector.wakeup();
		int timeout = 5000;
		long endTime = System.currentTimeMillis() + timeout;
		try {
			if (tcpAddress != null) (tcp = new TCP(8192, 2048)).connect(selector, this.tcpAddress = tcpAddress);
			if (udpAddress != null) {
				(udp = new UDP()).connect(selector, this.udpRemoteAddress = this.udpAddress = udpAddress);

				synchronized (udpRegistrationLock) {
					while (!udpRegistered && System.currentTimeMillis() < endTime) {
						sendUDP(new RegisterUDP(new InetSocketAddress(2222)));
						try {
							udpRegistrationLock.wait(5000); // 500
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
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
						if (selectionKey.channel() == tcp.socketChannel) {
							while ((object = tcp.readObject()) != null)
								notifyReceived(object);
						} else if (selectionKey.channel() == udp.datagramChannel) {
							if (udp.readFromAddress() != null) {
								object = udp.readObject();
								if (object instanceof RegisterUDP) {
									System.out.println("client received registerudp");
									synchronized (udpRegistrationLock) {
										udpRegistered = true;
										udpRegistrationLock.notifyAll();
									}
									notifyConnected(this);
								} else if (object != null) notifyReceived(object);
							}
						}
					} else if ((ops & SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE) tcp.writeOperation();
					else if ((ops & SelectionKey.OP_CONNECT) == SelectionKey.OP_CONNECT) {
						if (((SocketChannel) selectionKey.channel()).finishConnect()) {
							selectionKey.interestOps(SelectionKey.OP_READ);
							if (udp != null) {
								// sendTCP(new RegisterUDP((InetSocketAddress) udp.datagramChannel.getLocalAddress()));
							}
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
