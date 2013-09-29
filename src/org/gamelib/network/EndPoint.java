/**
 * 
 */
package org.gamelib.network;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * The local end point of a connection.
 * @author Axel
 */
public interface EndPoint {

	/** The port that is used if passed an invalid port. */
	public static final short DEFAULT_PORT = 800;

	/**
	 * Opens this connection at the specified address and port.
	 * @param address the address to open at
	 */
	public void open(InetSocketAddress address) throws IOException;

	public void update() throws IOException;

	/** Closes this connection. */
	public void close() throws IOException;

}
