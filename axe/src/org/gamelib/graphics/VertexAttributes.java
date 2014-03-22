/**
 * 
 */
package org.gamelib.graphics;

/** @author pwnedary */
public class VertexAttributes {
	public VertexAttrib[] attributes;

	public int vertexSize;

	public VertexAttributes(VertexAttrib[] attributes) {
		this.attributes = attributes;

		vertexSize = calculateOffsets();
	}

	private int calculateOffsets() {
		int count = 0;
		for (int i = 0; i < attributes.length; i++) {
			VertexAttrib attribute = attributes[i];
			attribute.location = count;
			if (attribute.type == VertexAttrib.Type.COLOR_PACKED) count += 4;
			else count += 4 * attribute.numComponents;
		}

		return count;
	}

	public int size() {
		return attributes.length;
	}

	public VertexAttrib get(int i) {
		return attributes[i];
	}
}
