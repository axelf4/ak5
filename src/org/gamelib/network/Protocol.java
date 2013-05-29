/**
 * 
 */
package org.gamelib.network;

import java.io.IOException;
import java.net.InetAddress;

import org.gamelib.Listenable;

/**
 * @author Axel
 */
public interface Protocol extends Listenable<SocketListener> {

	public void open(InetAddress address, short port) throws IOException;

	public void close() throws IOException;
}
