/**
 * 
 */
package org.gamelib.network;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Axel
 */
public class Server extends ConnectionImpl implements Connection {

	private ServerSocket server;

	/**
	 * @throws IOException
	 */
	public Server() throws IOException {
	}

	/** @param address must always localhost for server */
	@Override
	public void open(InetAddress address, short port) throws IOException {
		if (address != null)
			throw new IllegalArgumentException("parameter: address must be null");

		server = new ServerSocket(port);
		// System.out.println("Server opened at: " + server.getLocalSocketAddress());
		(thread = new Thread(this)).start();
	}

	@Override
	public void close() throws IOException {
		server.close();
	}

	@Override
	public void run() {
		while (true) {
			try {
				final Socket socket = server.accept();
				for (SocketListener listener : listeners)
					listener.connected(socket);
				final Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						ObjectOutputStream out = null;
						ObjectInputStream in = null;
						BufferedInputStream bin = null;
						try {
							(out = new ObjectOutputStream(socket.getOutputStream())).flush();
							in = new ObjectInputStream(socket.getInputStream());
							bin = new BufferedInputStream(socket.getInputStream());

							while (!socket.isClosed()) {
								// Write operations
								{
									Object object = null;
									while (!buffer.isEmpty()) {
										object = buffer.poll();
										out.writeObject(object);
									}
									if (object != null) out.flush();
								}
								// Read operations
								if (bin.available() > 0) {
									Object object = in.readObject();
									// System.out.println("Server recieved object: " + object);
									for (SocketListener listener : listeners)
										listener.received(object);
								}
								Thread.sleep(1000);
							}
						} catch (IOException | ClassNotFoundException
								| InterruptedException e) {
							e.printStackTrace();
						} finally {
							try {
								out.close();
								in.close();
								bin.close();
								
								for (SocketListener listener : listeners)
									listener.disconnected(socket);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				});

				thread.start();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public boolean closed() {
		return server.isClosed();
	}

}
