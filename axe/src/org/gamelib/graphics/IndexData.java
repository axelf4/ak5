/**
 * 
 */
package org.gamelib.graphics;

import java.nio.ByteBuffer;

import org.gamelib.util.Disposable;

/** @author pwnedary */
public interface IndexData extends Disposable {
	int getNumIndices();

	void setIndices(byte[] indices, int offset, int length);

	ByteBuffer getBuffer();

	void bind();

	void unbind();
}
