/**
 * 
 */
package ak5.graphics;

import java.nio.ShortBuffer;

import ak5.util.Disposable;

/** @author pwnedary */
public interface IndexData extends Disposable {
	int getNumIndices();

	void setIndices(short[] indices, int offset, int length);

	ShortBuffer getBuffer();

	void bind();

	void unbind();
}
