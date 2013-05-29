/**
 * 
 */
package org.gamelib.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import org.gamelib.network.protocol.TCPClient;
import org.gamelib.network.protocol.TCPServer;

/**
 * @author Axel
 */
public class Client extends EndPointImpl implements EndPoint {

	private Socket socket;
	Protocol protocol;

	/**
	 * 
	 */
	public Client() {
		Class<? extends Protocol> preffered = getPrefferedProtocol();
		if (preffered.equals(TCP.class)) {
			protocol = new TCPClient();
		}
	}

	@Override
	public void open(InetAddress address, short port) throws IOException {
		/*socket = new Socket(address, port);
		System.out.println("Client connected to server running at: " + socket.getLocalSocketAddress() + ":" + socket.getPort());
		// (thread = new Thread(this)).start();
		Connection connection = new TCPConnection(socket);
		connection.setListener(listener);
		notifyConnected(connection);*/
		
		protocol.open(address, port);
	}

	@Override
	public void close() throws IOException {
		socket.close();
	}

	/*@Override
	public void run() {
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		BufferedInputStream bin = null;
		try {
			in = new ObjectInputStream(socket.getInputStream());
			(out = new ObjectOutputStream(socket.getOutputStream())).flush();
			bin = new BufferedInputStream(socket.getInputStream());

			while (!socket.isClosed()) {
				// Read operations
				if (bin.available() > 0) {
					Object object = in.readObject();
					for (SocketListener listener : listeners)
						listener.received(object);
				}
				// Write operations
				{
					Object object = null;
					while (!buffer.isEmpty()) {
						object = buffer.poll();
						out.writeObject(object);
					}
					if (object != null) out.flush();
				}
				Thread.sleep(1000);
			}
		} catch (IOException | ClassNotFoundException
				| InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				in.close();
				bin.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}*/

}
