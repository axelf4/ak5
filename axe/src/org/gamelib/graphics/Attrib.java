/**
 * 
 */
package org.gamelib.graphics;

/** VertexAttrib
 * 
 * @author pwnedary */
public class Attrib {
	public final Type type;
	public final int numComponents;
	public int location;
	public final String name;

	public Attrib(Type type, int numComponents) {
		this.type = type;
		this.numComponents = numComponents;
		this.name = null;
	}
	
	public Attrib(Type type, int numComponents, String name) {
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
