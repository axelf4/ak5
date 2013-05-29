/**
 * 
 */
package org.gamelib.network.protocol;

import java.net.DatagramSocket;

import org.gamelib.network.Connection;
import org.gamelib.network.ConnectionImpl;

/**
 * @author Axel
 *
 */
public class UDPConnection extends ConnectionImpl implements Connection {
	
	DatagramSocket socket;

	/**
	 * 
	 */
	public UDPConnection(DatagramSocket socket) {
		this.socket = socket;
	}

	/* (non-Javadoc)
	 * @see org.gamelib.network.Connection#closed()
	 */
	@Override
	public boolean closed() {
		return socket.isClosed();
	}

}
