/**
 * 
 */
package org.gamelib.graphics;

import java.nio.FloatBuffer;

import org.gamelib.graphics.VertexAttribute.Type;
import org.gamelib.util.io.BufferUtil;

/** @author pwnedary */
public class VertexArray implements VertexData {
	private GL10 gl;
	private final VertexAttribute[] attributes;
	private final int vertexSize;
	private final FloatBuffer buffer;

	public VertexArray(GL10 gl, int numVertices, VertexAttribute... attributes) {
		this.gl = gl;
		vertexSize = VertexAttribute.calculateOffsets(this.attributes = attributes);
		buffer = (FloatBuffer) BufferUtil.newByteBuffer(vertexSize * numVertices).asFloatBuffer().flip();
	}

	public void bind() {
		for (int i = 0, textureUnit = 0; i < attributes.length; i++) {
			VertexAttribute attrib = attributes[i];
			buffer.position(attrib.location / 4);

			switch (attrib.type) {
			case POSITION:
				gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
				gl.glVertexPointer(attrib.numComponents, GL10.GL_FLOAT, vertexSize, buffer);
				break;
			case COLOR:
			case COLOR_PACKED:
				gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
				gl.glColorPointer(attrib.numComponents, attrib.type == Type.COLOR ? GL10.GL_FLOAT : GL10.GL_UNSIGNED_BYTE, vertexSize, buffer);
				break;
			case NORMAL:
				gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
				gl.glNormalPointer(GL10.GL_FLOAT, vertexSize, buffer);
				break;
			case TEXTURE_COORDINATES:
				gl.glClientActiveTexture(GL10.GL_TEXTURE0 + textureUnit++);
				gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
				gl.glTexCoordPointer(attrib.numComponents, GL10.GL_FLOAT, vertexSize, buffer);
				break;
			}
		}
	}

	@Override
	public void bind(ShaderProgram shader, int[] locations) {
		GL20 gl = (GL20) this.gl;
		for (int i = 0; i < attributes.length; i++) {
			final VertexAttribute attribute = attributes[i];
			final int location = locations == null ? shader.getAttribLocation(attribute.name) : locations[i];
			if (location < 0) continue;
			gl.glEnableVertexAttribArray(location);
			buffer.position(attribute.location);

			if (attribute.type == Type.COLOR_PACKED) gl.glVertexAttribPointer(location, attribute.numComponents, GL20.GL_UNSIGNED_BYTE, true, vertexSize, buffer);
			gl.glVertexAttribPointer(location, attribute.numComponents, GL20.GL_FLOAT, false, vertexSize, buffer);
		}
	}

	public void unbind() {
		for (int i = 0, textureUnit = 0; i < attributes.length; i++) {
			switch (attributes[i].type) {
			case POSITION:
				// gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
				break;
			case COLOR:
			case COLOR_PACKED:
				gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
				break;
			case NORMAL:
				gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
				break;
			case TEXTURE_COORDINATES:
				gl.glClientActiveTexture(GL10.GL_TEXTURE0 + textureUnit++);
				gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
				break;
			}
		}
		buffer.position(0);
	}

	@Override
	public void unbind(ShaderProgram shader, int[] locations) {
		for (int i = 0; i < attributes.length; i++)
			if (locations == null) ((GL20) gl).glEnableVertexAttribArray(shader.getAttribLocation(attributes[i].name));
			else ((GL20) gl).glDisableVertexAttribArray(locations[i]);
	}

	@Override
	public void setVertices(float[] vertices, int offset, int length) {
		((FloatBuffer) buffer.clear()).put(vertices, offset, length).flip();
	}

	@Override
	public int getNumVertices() {
		return buffer.limit() * 4 / vertexSize;
	}

	@Override
	public void dispose() {}
}
