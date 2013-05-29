/**
 * 
 */
package org.gamelib.network.protocol;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.gamelib.network.Connection;
import org.gamelib.network.ConnectionImpl;

/**
 * @author Axel
 */
public class TCPConnection extends ConnectionImpl implements Connection, Runnable {

	Socket socket;
	ObjectOutputStream out = null;
	ObjectInputStream in = null;
	BufferedInputStream bin = null;

	Thread thread;

	public TCPConnection(Socket socket) throws IOException {
		this.socket = socket;
		(this.thread = new Thread(this)).start();
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.network.Connection#closed()
	 */
	@Override
	public boolean closed() {
		return socket.isClosed();
	}

	@Override
	public void run() {
		try {
			(out = new ObjectOutputStream(socket.getOutputStream())).flush();
			in = new ObjectInputStream(socket.getInputStream());
			bin = new BufferedInputStream(socket.getInputStream());
			notifyConnected(this);
			while (!closed()) {
				// Write operations
				{
					Object object = null;
					while (!buffer.isEmpty()) {
						object = buffer.poll();
						out.writeObject(object);
						notifySent(object);
					}
					if (object != null) out.flush();
				}
				// Read operations
				{
					if (bin.available() > 0) {
						Object object = in.readObject();
						// System.out.println("Server recieved object: " + object);
						notifyReceived(object);
					}
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
				notifyDisconnected(this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
