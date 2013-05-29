/**
 * 
 */
package org.gamelib.network.protocol;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import org.gamelib.network.Connection;
import org.gamelib.network.Protocol;
import org.gamelib.network.SocketListener;

/**
 * @author Axel
 *
 */
public class TCPClient implements Protocol {
	
	Socket socket;
	SocketListener listener;

	/**
	 * 
	 */
	public TCPClient() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.gamelib.Listenable#setListener(java.lang.Object)
	 */
	@Override
	public void setListener(SocketListener listener) {
		this.listener = listener;
	}

	/* (non-Javadoc)
	 * @see org.gamelib.network.Protocol#open(java.net.InetAddress, short)
	 */
	@Override
	public void open(InetAddress address, short port) throws IOException {
		socket = new Socket(address, port);
		Connection connection = new TCPConnection(socket);
		connection.setListener(listener);
	}

	/* (non-Javadoc)
	 * @see org.gamelib.network.Protocol#close()
	 */
	@Override
	public void close() throws IOException {
		socket.close();
	}

}
