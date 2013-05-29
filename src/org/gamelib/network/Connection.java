/**
 * 
 */
package org.gamelib.network;

import java.io.Serializable;

import org.gamelib.Listenable;


/**
 * A connection between a {@link Client} and a {@link Server}.
 * @author Axel
 */
public interface Connection extends Listenable<SocketListener> {

	/** Sends the object to the remote end. */
	public void send(Serializable obj);

	/** @return if connected to the remote end. */
	public boolean closed();

}
