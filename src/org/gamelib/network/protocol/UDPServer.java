/**
 * 
 */
package org.gamelib.network.protocol;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.gamelib.network.Protocol;
import org.gamelib.network.SocketListener;

/**
 * @author Axel
 *
 */
public class UDPServer implements Protocol {
	
	DatagramSocket socket;
	SocketListener listener;

	/**
	 * 
	 */
	public UDPServer() {
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
		socket = new DatagramSocket(port);
		
	}

	/* (non-Javadoc)
	 * @see org.gamelib.network.Protocol#close()
	 */
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}

}
