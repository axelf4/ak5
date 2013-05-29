/**
 * 
 */
package org.gamelib.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.gamelib.network.protocol.TCPConnection;
import org.gamelib.network.protocol.TCPServer;

/**
 * @author Axel
 */
public class Server extends EndPointImpl implements EndPoint, Runnable {

	Protocol protocol;
	private ServerSocket server;

	/**
	 * @throws IOException
	 */
	public Server() throws IOException {
		Class<? extends Protocol> preffered = getPrefferedProtocol();
		if (preffered.equals(TCP.class)) {
			protocol = new TCPServer();
		}
	}

	/** @param address must always localhost for server */
	@Override
	public void open(InetAddress address, short port) throws IOException {
		if (address != null)
			throw new IllegalArgumentException("parameter: address must be null");
		// server = new ServerSocket(port);
		// System.out.println("Server opened at: " + server.getLocalSocketAddress());
		//  (thread = new Thread(this)).start();
		// protocol = getPrefferedProtocol();
		
		protocol.open(address, port);
	}

	@Override
	public void close() throws IOException {
		// server.close();
		protocol.close();
	}

	@Override
	public void run() {
		while (!server.isClosed()) {
			try {
				final Socket socket = server.accept();
				Connection connection = new TCPConnection(socket);
				connection.setListener(listener);
				// notifyConnected(connection);
				/*final Thread thread = new Thread(new Runnable() {
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

				thread.start();*/
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
