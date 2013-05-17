/**
 * 
 */
package org.gamelib.network;

import java.io.IOException;
import java.net.InetAddress;

/**
 * @author Axel
 */
public interface Connection {
	
	/** The port that is used if passed an invalid port. */
	public static final short DEFAULT_PORT = 2222;

	/**
	 * Opens this connection at the specified address and port.
	 * @param address the address to connect at
	 * @param port the port to connect at
	 */
	public void open(InetAddress address, short port) throws IOException;

	/** Closes this connection. */
	public void close() throws IOException;
	
	public void send(Object obj);
	
	public void addSocketListener(SocketListener listener);
	
	public boolean closed();

}
