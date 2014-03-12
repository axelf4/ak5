/**
 * 
 */
package org.gamelib.util.io;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/** @author pwnedary */
public class ByteBufferOutputStream extends OutputStream {
	private ByteBuffer buffer;

	public ByteBufferOutputStream(ByteBuffer buffer) {
		this.buffer = buffer;
	}

	public ByteBufferOutputStream(int bufferSize) {
		this(ByteBuffer.allocate(bufferSize));
	}

	@Override
	public void write(int b) throws IOException {
		if (!buffer.hasRemaining()) flush();
		buffer.put((byte) b);
	}

	@Override
	public void write(byte[] bytes, int off, int len) throws IOException {
		if (buffer.remaining() < len) flush();
		buffer.put(bytes, off, len);
	}

}
