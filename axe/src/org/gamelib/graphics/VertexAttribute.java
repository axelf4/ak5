/**
 * 
 */
package org.gamelib.graphics;

/** VertexAttrib
 * 
 * @author pwnedary */
public class VertexAttribute {
	public final Type type;
	public final int numComponents;
	public int location;
	public final String name;

	public VertexAttribute(Type type, int numComponents) {
		this.type = type;
		this.numComponents = numComponents;
		this.name = null;
	}
	
	public VertexAttribute(Type type, int numComponents, String name) {
		this.type = type;
		this.numComponents = numComponents;
		this.name = name;
	}

	public String toString() {
		return type + " (" + numComponents + ")";
	}

	public enum Type {
		POSITION, COLOR, COLOR_PACKED, NORMAL, TEXTURE_COORDINATES;
	}
}
