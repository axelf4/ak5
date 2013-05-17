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
 */
public class Client extends ConnectionImpl implements Connection {

	private Socket socket;

	/**
	 * 
	 */
	public Client() {
		// TODO Auto-generated constructor stub
	}

	public void open(InetAddress address, short port) throws IOException {
		socket = new Socket(address, port);
		System.out.println("Client connected to server running at: " + socket.getLocalSocketAddress() + ":" + socket.getPort());
		(thread = new Thread(this)).start();
	}

	@Override
	public void close() throws IOException {
		socket.close();
	}

	@Override
	public void run() {
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		BufferedInputStream bin = null;
		try {
			in = new ObjectInputStream(socket.getInputStream());
			(out = new ObjectOutputStream(socket.getOutputStream())).flush();
			bin = new BufferedInputStream(socket.getInputStream());

			while (!socket.isClosed()) {
				// Read operations
				if (bin.available() > 0) {
					Object object = in.readObject();
					for (SocketListener listener : listeners)
						listener.received(object);
				}
				// Write operations
				{
					Object object = null;
					while (!buffer.isEmpty()) {
						object = buffer.poll();
						out.writeObject(object);
					}
					if (object != null) out.flush();
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
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean closed() {
		return socket.isClosed();
	}

}
