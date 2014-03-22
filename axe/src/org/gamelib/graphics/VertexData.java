/**
 * 
 */
package org.gamelib.graphics;

/** @author pwnedary */
public interface VertexData {
	void bind();
	
	void unbind();
	
	void setVertices(float[] vertices, int offset, int length);
	
	int getNumVertices();
}
