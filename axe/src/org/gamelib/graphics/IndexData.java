/**
 * 
 */
package org.gamelib.graphics;

import java.nio.ShortBuffer;

import org.gamelib.util.Disposable;

/** @author pwnedary */
public interface IndexData extends Disposable {
	int getNumIndices();

	void setIndices(short[] indices, int offset, int length);

	ShortBuffer getBuffer();

	void bind();

	void unbind();
}
