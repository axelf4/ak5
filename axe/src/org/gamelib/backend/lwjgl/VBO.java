/**
 * 
 */
package org.gamelib.backend.lwjgl;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

/**
 * TODO add cons VBO(FloatBuffer pos, FloatBuffer col)<br />
 * From: https://sites.google.com/site/voxelenginelwjglport/04-advanced-rendering
 * @author pwnedary
 */
public class VBO {
	
	static {
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
	}

	int vertexHandle;
	int colorHandle;
	int count;
	
	/**
	 * 
	 */
	public VBO(FloatBuffer vertexPositionData, FloatBuffer vertexColorData) {
		vertexHandle = GL15.glGenBuffers();
		colorHandle = GL15.glGenBuffers();
		count = Math.min(vertexPositionData.capacity(), vertexColorData.capacity());
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexHandle);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexPositionData, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorHandle);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexColorData, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	/**
	 * 
	 */
	public VBO(float[] pos, float[] col) {
		this(getVertexPositionData(pos), getVertexColorData(col));
		/*FloatBuffer vertexPositionData = BufferUtils.createFloatBuffer(pos.length);
		FloatBuffer vertexColorData = BufferUtils.createFloatBuffer(col.length);
		vertexPositionData.put(pos);
		vertexColorData.put(col);
		vertexPositionData.flip();
		vertexColorData.flip();

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexHandle);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexPositionData, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorHandle);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexColorData, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);*/
	}
	
	static FloatBuffer getVertexPositionData(float[] pos) {
		FloatBuffer vertexPositionData = BufferUtils.createFloatBuffer(pos.length);
		vertexPositionData.put(pos);
		vertexPositionData.flip();
		return vertexPositionData;
	}
	static FloatBuffer getVertexColorData(float[] col) {
		FloatBuffer vertexColorData = BufferUtils.createFloatBuffer(col.length);
		vertexColorData.put(col);
		vertexColorData.flip();
		return vertexColorData;
	}

	public void draw() {
		GL11.glPushMatrix();
		{
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexHandle);
			GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0L);
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorHandle);
			GL11.glColorPointer(3, GL11.GL_FLOAT, 0, 0L);
			GL11.glDrawArrays(GL11.GL_QUADS, 0, count);
		}
		GL11.glPopMatrix();
	}

}
