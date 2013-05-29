/**
 * 
 */
package org.gamelib.network;

import java.io.IOException;
import java.net.InetAddress;

/**
 * The local end point of a connection.
 * @author Axel
 */
public interface EndPoint {

	/** The port that is used if passed an invalid port. */
	public static final short DEFAULT_PORT = 80;

	/**
	 * Opens this connection at the specified address and port.
	 * @param address the address to connect at
	 * @param port the port to connect at
	 */
	public void open(InetAddress address, short port) throws IOException;

	/** Closes this connection. */
	public void close() throws IOException;
	
	public Class<? extends Protocol> getPrefferedProtocol();

}
