/**
 * 
 */
package ak5.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * @author pwnedary
 */
public class ByteBufferInputStream extends InputStream {
	private ByteBuffer buffer;

	public ByteBufferInputStream(ByteBuffer buffer) {
		this.buffer = buffer;
	}

	public ByteBufferInputStream(int bufferSize) {
		this(ByteBuffer.allocate(bufferSize));
		buffer.flip();
	}

	@Override
	public int read() throws IOException {
		if (!buffer.hasRemaining()) return -1;
		return buffer.get() & 0xFF; // unsigned & 0xFF
	}

	@Override
	public int read(byte[] bytes, int off, int len) throws IOException {
		len = Math.min(len, buffer.remaining());
		if (len == 0) return -1;
		buffer.get(bytes, off, len);
		return len;
	}

	@Override
	public int available() throws IOException {
		return buffer.remaining();
	}

}
