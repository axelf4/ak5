/**
 * 
 */
package org.gamelib.network.protocol;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.gamelib.network.Protocol;
import org.gamelib.network.SocketListener;

/**
 * @author Axel
 *
 */
public class UDPClient implements Protocol {
	
	DatagramSocket socket;
	SocketListener listener;

	/**
	 * 
	 */
	public UDPClient() {
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
		socket = new DatagramSocket();
		
		DatagramPacket p = new DatagramPacket(new byte[0], 0, address, port);
		socket.send(p);
	}

	/* (non-Javadoc)
	 * @see org.gamelib.network.Protocol#close()
	 */
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}

}
