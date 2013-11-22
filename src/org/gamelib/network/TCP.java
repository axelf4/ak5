/**
 * 
 */
package org.gamelib.network;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

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
		writeBuffer.clear();
		readBuffer.clear();
		readBuffer.flip();
		currentObjectLength = 0;

		this.socketChannel = socketChannel;
		socketChannel.configureBlocking(false);

		selectionKey = socketChannel.register(selector, SelectionKey.OP_READ);

		return selectionKey;
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
		writeBuffer.clear();
		readBuffer.clear();
		readBuffer.flip();
		currentObjectLength = 0;

		this.socketChannel = selector.provider().openSocketChannel();
		Socket socket = socketChannel.socket();
		socket.setTcpNoDelay(true);
		socket.connect(address);
		socketChannel.configureBlocking(false);

		selectionKey = socketChannel.register(selector, SelectionKey.OP_READ);
		selectionKey.attach(this);
	}

	/**
	 * @throws IOException
	 */
	public void writeOperation() throws IOException {
		if (socketChannel == null) throw new SocketException("Connection is closed.");

		if (writeToSocket()) selectionKey.interestOps(SelectionKey.OP_READ); // write successful, clear OP_WRITE
	}

	private boolean writeToSocket() throws IOException {
		writeBuffer.flip();
		while (writeBuffer.hasRemaining()) {
			if (socketChannel.write(writeBuffer) == 0) break;
		}
		writeBuffer.compact();

		return writeBuffer.position() == 0;
	}

	public int send(Object object) throws IOException {
		// Leave room for length.
		int start = writeBuffer.position();
		int lengthLength = serialization.getLengthLength();
		writeBuffer.position(writeBuffer.position() + lengthLength);

		// Write data.
		serialization.write(writeBuffer, object);
		int end = writeBuffer.position();

		// Write data length.
		writeBuffer.position(start);
		serialization.writeLength(writeBuffer, end - lengthLength - start);
		writeBuffer.position(end);
		System.out.println(Arrays.toString(writeBuffer.array())); // Debugging

		// Write to socket if no data was queued.
		if (start == 0 && !writeToSocket()) selectionKey.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE); // A partial write, set OP_WRITE to be notified when more writing can occur.
		else selectionKey.selector().wakeup(); // Full write, wake up selector so idle event will be fired.
		return end - start;
	}

	public Object readObject() throws IOException {
		if (currentObjectLength == 0) {
			// read length of next object
			int lengthLength = serialization.getLengthLength();
			if (readBuffer.remaining() < lengthLength) {
				readBuffer.compact();
				int bytesRead = socketChannel.read(readBuffer);
				readBuffer.flip();
				if (bytesRead == -1) throw new SocketException("Connection is closed.");

				if (readBuffer.remaining() < lengthLength) return null;
			}
			currentObjectLength = serialization.readLength(readBuffer);
		}

		int length = currentObjectLength;
		if (readBuffer.remaining() < length) {
			readBuffer.compact();
			int bytesRead = socketChannel.read(readBuffer);
			readBuffer.flip();
			if (bytesRead == -1) throw new SocketException("Connection is closed.");

			if (readBuffer.remaining() < length) return null;
		}
		currentObjectLength = 0;

		int startPosition = readBuffer.position();
		int oldLimit = readBuffer.limit();

		if (length == 0) return null;
		readBuffer.limit(startPosition + length);
		Object object = serialization.read(readBuffer);
		readBuffer.limit(oldLimit);
		if (readBuffer.position() - startPosition != length) throw new RuntimeException("Incorrect number of bytes (" + (startPosition + length - readBuffer.position()) + " remaining) used to deserialize object: " + object);

		return object;
	}

	public void close() throws IOException {
		if (socketChannel != null) {
			socketChannel.close();
			socketChannel = null;
			if (selectionKey != null) selectionKey.selector().wakeup();
		}
	}

}
