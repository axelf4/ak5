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
	
	public static int calculateOffsets(VertexAttribute[] attributes) {
		int count = 0;
		for (int i = 0; i < attributes.length; i++) {
			VertexAttribute attribute = attributes[i];
			attribute.location = count;
			if (attribute.type == VertexAttribute.Type.COLOR_PACKED) count += 4;
			else count += 4 * attribute.numComponents;
		}
		return count;
	}

	public enum Type {
		POSITION, COLOR, COLOR_PACKED, NORMAL, TEXTURE_COORDINATES;
	}
}
