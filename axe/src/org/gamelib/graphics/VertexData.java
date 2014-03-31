/**
 * 
 */
package org.gamelib.graphics;

/** @author pwnedary */
public interface VertexData {
	void bind();
	
	void bind(ShaderProgram shader, int[] locations);
	
	void unbind();
	
	void unbind(ShaderProgram shader, int[] locations);
	
	void setVertices(float[] vertices, int offset, int length);
	
	int getNumVertices();
}
