/**
 * 
 */
package org.gamelib.graphics;

/** @author pwnedary */
public class VertexAttributes {
	public VertexAttribute[] attributes;

	public int vertexSize;

	public VertexAttributes(VertexAttribute[] attributes) {
		this.attributes = attributes;

		vertexSize = calculateOffsets();
	}

	private int calculateOffsets() {
		int count = 0;
		for (int i = 0; i < attributes.length; i++) {
			VertexAttribute attribute = attributes[i];
			attribute.location = count;
			if (attribute.type == VertexAttribute.Type.COLOR_PACKED) count += 4;
			else count += 4 * attribute.numComponents;
		}

		return count;
	}

	public int size() {
		return attributes.length;
	}

	public VertexAttribute get(int i) {
		return attributes[i];
	}
}
