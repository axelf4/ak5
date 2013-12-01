/**
 * 
 */
package org.gamelib.net;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * The local end point of a connection.
 * 
 * @author Axel
 */
public interface EndPoint {

	/** The port that is used if passed an invalid port. */
	public static final short DEFAULT_PORT = 800;

	/**
	 * Opens this connection at the specified address and port.
	 * 
	 * @param tcpPort the port to open TCP at
	 * @param udpPort the port to open UDP at
	 */
	public void open(InetSocketAddress tcpHost, InetSocketAddress udpHost)
			throws IOException;

	public void update() throws IOException;

	/** Closes this connection. */
	public void close() throws IOException;

}
