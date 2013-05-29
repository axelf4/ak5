/**
 * 
 */
package org.gamelib.network.protocol;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.gamelib.network.Connection;
import org.gamelib.network.Protocol;
import org.gamelib.network.SocketListener;

/**
 * @author Axel
 *
 */
public class TCPServer implements Protocol, Runnable {
	
	ServerSocket server;
	Thread thread;
	SocketListener listener;

	/**
	 * 
	 */
	public TCPServer() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.gamelib.network.Protocol#open()
	 */
	@Override
	public void open(InetAddress address, short port) throws IOException {
		server = new ServerSocket(port);
		(thread = new Thread(this)).start();
	}

	/* (non-Javadoc)
	 * @see org.gamelib.network.Protocol#close()
	 */
	@Override
	public void close() throws IOException {
		/*thread.interrupt();
		thread = null;*/
		server.close();
	}

	@Override
	public void run() {
		while (!server.isClosed()) {
			try {
				final Socket socket = server.accept();
				Connection connection = new TCPConnection(socket);
				connection.setListener(listener);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void setListener(SocketListener listener) {
		this.listener = listener;
	}
	
	protected void notifyConnected(Connection connection) {
		if (listener != null) listener.connected(connection);
	}

}
