/**
 * 
 */
package org.gamelib.util.net;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @author pwnedary
 */
public class TCP {

	SocketChannel socketChannel;
	ByteBuffer readBuffer, writeBuffer;

	SelectionKey selectionKey;
	int currentObjectLength = 0;
	Serialization serialization = new Serialization();

	public TCP(int readBufferSize, int writeBufferSize) {
		readBuffer = ByteBuffer.allocate(readBufferSize);
		writeBuffer = ByteBuffer.allocate(writeBufferSize);
	}

	public TCP() {
		this(4096, 4096);
	}

	/**
	 * Server
	 * 
	 * @return
	 */
	public SelectionKey accept(Selector selector, SocketChannel socketChannel)
			throws IOException {
		close();
		try {
			this.socketChannel = socketChannel;
			socketChannel.configureBlocking(false);
			socketChannel.socket().setTcpNoDelay(true);

			return selectionKey = socketChannel.register(selector, SelectionKey.OP_READ); // Return selection key
		} catch (IOException e) {
			close();
			throw e;
		}
	}

	/**
	 * Client
	 * 
	 * @param address
	 * @param selector
	 * @throws IOException
	 */
	public void connect(Selector selector, SocketAddress address)
			throws IOException {
		close();
		try {
			this.socketChannel = selector.provider().openSocketChannel();
			socketChannel.configureBlocking(false);
			socketChannel.socket().setTcpNoDelay(true);

			selectionKey = socketChannel.register(selector, SelectionKey.OP_CONNECT); // , this
			socketChannel.connect(address);
		} catch (IOException e) {
			close();
			throw new IOException("Unable to connect to: " + address, e);
		}
	}

	/**
	 * @throws IOException
	 */
	public void writeOperation() throws IOException {
		if (socketChannel == null) throw new SocketException("Connection is closed.");
		if (writeToSocket()) selectionKey.interestOps(SelectionKey.OP_READ); // Write successful, clear OP_WRITE
	}

	private boolean writeToSocket() throws IOException {
		if (!socketChannel.isConnected()) return false;
		writeBuffer.flip();
		while (writeBuffer.hasRemaining())
			if (socketChannel.write(writeBuffer) == 0) break;
		writeBuffer.compact();

		return writeBuffer.position() == 0;
	}

	public int send(Object object) throws IOException {
		if (socketChannel == null) throw new SocketException("Connection is closed.");
		// Leave room for length.
		int start = writeBuffer.position();
		int lengthLength = serialization.getLengthLength();
		writeBuffer.position(writeBuffer.position() + lengthLength);

		serialization.write(writeBuffer, object); // Write data.
		int end = writeBuffer.position();

		// Write data length.
		writeBuffer.position(start);
		serialization.writeLength(writeBuffer, end - lengthLength - start);
		writeBuffer.position(end);

		// Write to socket if no data was queued.
		if (start == 0 && !writeToSocket()) selectionKey.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE); // A partial write, set OP_WRITE to be notified when more writing can occur.
		else selectionKey.selector().wakeup(); // Full write, wake up selector so idle event will be fired.
		return end - start;
	}

	public Object readObject() throws IOException {
		if (socketChannel == null) throw new SocketException("Connection is closed.");
		if (currentObjectLength == 0) {
			int lengthLength = serialization.getLengthLength(); // Read length of next object
			if (readBuffer.remaining() < lengthLength) {
				readBuffer.compact();
				if (socketChannel.read(readBuffer) == -1) throw new SocketException("Connection is closed."); // Read bytes into buffer
				readBuffer.flip();

				if (readBuffer.remaining() < lengthLength) return null;
			}
			currentObjectLength = serialization.readLength(readBuffer);
		}

		int length = currentObjectLength;
		if (readBuffer.remaining() < length) {
			readBuffer.compact();
			if (socketChannel.read(readBuffer) == -1) throw new SocketException("Connection is closed."); // Read bytes into buffer
			readBuffer.flip();

			if (readBuffer.remaining() < length) return null;
		}
		currentObjectLength = 0;

		int startPosition = readBuffer.position();
		int oldLimit = readBuffer.limit();

		if (length == 0) return null;
		readBuffer.limit(startPosition + length);
		Object object = serialization.read(readBuffer);
		readBuffer.limit(oldLimit);
		if (readBuffer.position() - startPosition != length) throw new IOException("Incorrect number of bytes (" + (startPosition + length - readBuffer.position()) + " remaining) used to deserialize object: " + object);

		return object;
	}

	public void close() throws IOException {
		writeBuffer.clear();
		readBuffer.clear();
		readBuffer.flip();
		currentObjectLength = 0;
		if (socketChannel != null) {
			socketChannel.close();
			socketChannel = null;
			if (selectionKey != null) selectionKey.selector().wakeup();
		}
	}
}
