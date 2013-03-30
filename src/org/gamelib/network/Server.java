/**
 * 
 */
package org.gamelib.network;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;

/**
 * @author Axel
 * 
 */
public class Server extends Connection {

	private ServerSocket server;

	/**
	 * @throws IOException
	 * 
	 */
	public Server(int port) throws IOException {
		server = new ServerSocket(port);
		System.out.println("Server opened at: " + server.getLocalSocketAddress());
		(thread = new Thread(this)).start();
	}
	
	public Server() throws IOException {
		this(DEFAULT_PORT);
	}

	@Override
	public void run() {
		while (true) {
			final Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					ObjectOutputStream out = null;
					ObjectInputStream in = null;
					BufferedInputStream bin = null;
					try {
						final Socket socket = server.accept();
						System.out.println("Accepted");
						(out = new ObjectOutputStream(socket.getOutputStream())).flush();
						in = new ObjectInputStream(socket.getInputStream());
						bin = new BufferedInputStream(socket.getInputStream());
						
						while (!socket.isClosed()) {
							// Read operations
							if (bin.available() > 0) {
								Object object = in.readObject();
								System.out.println("Server recieved object: " + object);
								for (SocketListener listener : listeners)
									listener.received(object);
							}
							// Write operations
							for (Iterator<Object> iterator = buffer.iterator(); iterator.hasNext();) {
								Object object = (Object) iterator.next();
								out.writeObject(object);
								out.flush();
								iterator.remove();
							}
							Thread.sleep(1000);
						}
					} catch (IOException | ClassNotFoundException | InterruptedException e) {
						e.printStackTrace();
					} finally {
						try {
							out.close();
							in.close();
							bin.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			thread.start();
		}
	}

}
