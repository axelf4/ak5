/**
 * 
 */
package org.gamelib.network;

import org.gamelib.network.protocol.TCPServer;

/**
 * @author Axel
 */
public abstract class EndPointImpl implements EndPoint {

	public Thread thread;

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		Thread moribund = thread;
		thread = null;
		Thread.currentThread().interrupt();
		moribund.interrupt();

	}

	public SocketListener listener;

	public void setSocketListener(SocketListener listener) {
		this.listener = listener;
	}
	
	@Override
	public Class<? extends Protocol> getPrefferedProtocol() {
		return TCPServer.class;
	}

}
