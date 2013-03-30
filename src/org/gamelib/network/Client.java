/**
 * 
 */
package org.gamelib.network;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Iterator;

/**
 * @author Axel
 *
 */
public class Client extends Connection {
	
	private Socket socket;

	/**
	 * 
	 */
	public Client() {
		// TODO Auto-generated constructor stub
	}
	
	public Connection open(InetAddress address, short port) throws IOException {
		socket = new Socket(address, port);
		System.out.println("Client connected to server running at: " + socket.getLocalSocketAddress() + ":" + socket.getPort());
		(thread = new Thread(this)).start();
		return this;
	}

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
				// Read operations
				if (bin.available() > 0) {
					Object object = in.readObject();
					for (SocketListener listener : listeners)
						listener.received(object);
				}
				// Write operations
				for (Iterator<Object> iterator = buffer.iterator(); iterator.hasNext();) {
					Object object = (Object) iterator.next();
					System.out.println("Client sent object: " + object);
					out.writeObject(object);
					out.flush();
					iterator.remove();
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				bin.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
