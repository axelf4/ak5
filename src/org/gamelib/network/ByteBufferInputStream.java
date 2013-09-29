/**
 * 
 */
package org.gamelib.network;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * @author pwnedary
 */
public class ByteBufferInputStream extends InputStream {

	ByteBuffer buffer;

	public ByteBufferInputStream(ByteBuffer buffer) {
		this.buffer = buffer;
	}

	public int read() throws IOException {
		if (!buffer.hasRemaining()) return -1;
		return buffer.get() & 0xFF;
	}

	public int read(byte[] bytes, int off, int len) throws IOException {
		if (!buffer.hasRemaining()) return -1;

		len = Math.min(len, buffer.remaining());
		buffer.get(bytes, off, len);
		return len;
	}

}
