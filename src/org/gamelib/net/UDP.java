/**
 * 
 */
package org.gamelib.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

/**
 * @author pwnedary
 */
public class UDP {
	DatagramChannel datagramChannel;
	final ByteBuffer readBuffer, writeBuffer;
	private SelectionKey selectionKey;
	InetSocketAddress connectedAddress;
	Serialization serialization = new Serialization();

	public UDP(int bufferSize) {
		readBuffer = ByteBuffer.allocate(bufferSize);
		writeBuffer = ByteBuffer.allocateDirect(bufferSize);
	}

	public UDP() {
		this(4096);
	}

	public void bind(Selector selector, InetSocketAddress localPort) {
		close();
		readBuffer.clear();
		writeBuffer.clear();
		try {
			datagramChannel = selector.provider().openDatagramChannel();
			datagramChannel.socket().bind(localPort);
			datagramChannel.configureBlocking(false);
			selectionKey = datagramChannel.register(selector, SelectionKey.OP_READ);
		} catch (IOException e) {
			close();
			e.printStackTrace();
		}
	}

	public void connect(Selector selector, InetSocketAddress remoteAddress) {
		close();
		readBuffer.clear();
		writeBuffer.clear();
		try {
			datagramChannel = selector.provider().openDatagramChannel();
			datagramChannel.socket().bind(null);
			datagramChannel.socket().connect(this.connectedAddress = remoteAddress);
			datagramChannel.configureBlocking(false);

			selectionKey = datagramChannel.register(selector, SelectionKey.OP_READ);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public InetSocketAddress readFromAddress() throws IOException {
		if (datagramChannel == null) throw new SocketException("Connection is closed.");
		return (InetSocketAddress) datagramChannel.receive(readBuffer);
	}

	public int send(Object object, SocketAddress address) throws IOException {
		if (datagramChannel == null) throw new SocketException("Connection is closed.");
		try {
			serialization.write(writeBuffer, object);
			writeBuffer.flip();
			int length = writeBuffer.limit();
			datagramChannel.send(writeBuffer, address);

			boolean wasFullWrite = !writeBuffer.hasRemaining();
			return wasFullWrite ? length : -1;
		} finally {
			writeBuffer.clear();
		}
	}

	public Object readObject() throws IOException {
		readBuffer.flip();
		try {
			Object object = serialization.read(readBuffer);
			if (readBuffer.hasRemaining()) throw new IOException("Incorrect number of bytes (" + readBuffer.remaining() + " remaining) used to deserialize object: " + object);
			return object;
		} finally {
			readBuffer.clear();
		}
	}

	public void close() {
		try {
			if (datagramChannel != null) {
				datagramChannel.close();
				datagramChannel = null;
				if (selectionKey != null) selectionKey.selector().wakeup();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
