/**
 * 
 */
package org.gamelib.graphics;

/** @author pwnedary */
public class VertexAttributes {
	public Attrib[] attributes;

	public int vertexSize;

	public VertexAttributes(Attrib[] attributes) {
		this.attributes = attributes;

		vertexSize = calculateOffsets();
	}

	private int calculateOffsets() {
		int count = 0;
		for (int i = 0; i < attributes.length; i++) {
			Attrib attribute = attributes[i];
			attribute.location = count;
			if (attribute.type == Attrib.Type.COLOR_PACKED) count += 4;
			else count += 4 * attribute.numComponents;
		}

		return count;
	}

	public int size() {
		return attributes.length;
	}

	public Attrib get(int i) {
		return attributes[i];
	}
}
