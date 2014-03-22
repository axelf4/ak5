/**
 * 
 */
package org.gamelib.graphics;

import java.nio.ByteBuffer;

/** @author pwnedary */
public interface IndexData {
	int getNumIndices();

	void setIndices(byte[] indices, int offset, int length);

	ByteBuffer getBuffer();

	void bind();

	void unbind();
}
