/**
 * 
 */
package org.gamelib.graphics;

import org.gamelib.util.Disposable;

/** @author pwnedary */
public interface VertexData extends Disposable {
	void bind();

	void bind(ShaderProgram shader, int[] locations);

	void unbind();

	void unbind(ShaderProgram shader, int[] locations);

	void setVertices(float[] vertices, int offset, int length);

	int getNumVertices();
}
