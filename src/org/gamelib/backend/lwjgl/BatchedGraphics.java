/**
 * 
 */
package org.gamelib.backend.lwjgl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.gamelib.backend.Color;
import org.gamelib.backend.Graphics;
import org.gamelib.backend.Image;
import org.lwjgl.opengl.ARBMapBufferRange;
import org.lwjgl.opengl.GLContext;

/** @author pwnedary */
public class BatchedGraphics extends ImmediateGraphics implements Graphics {
	private int vbo;
	private int count = 0;
	private ByteBuffer buffer;
	private FloatBuffer floatBuffer;

	public BatchedGraphics() {
		vbo = glGenBuffers(); // generate VBO id

		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		
		buffer = ByteBuffer.allocateDirect(10000).order(ByteOrder.nativeOrder());
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STREAM_DRAW);

		{
			buffer = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY, (9 + 9) << 2, buffer);

			// create geometry buffer (both vertex and color)
			floatBuffer = buffer.order(ByteOrder.nativeOrder()).asFloatBuffer();

			//			vcBuffer.put(0).put(0).put(0.0f); // v
			//			vcBuffer.put(1).put(0).put(0); // c
			//
			//			vcBuffer.put(100).put(0).put(0.0f); // v
			//			vcBuffer.put(0).put(1).put(0); // c
			//
			//			vcBuffer.put(100).put(100).put(0.0f); // v
			//			vcBuffer.put(0).put(0).put(1); // c
			//
			//			vcBuffer.flip();

			glUnmapBuffer(GL_ARRAY_BUFFER);
		}

		glVertexPointer(3, GL_FLOAT, /* stride */(3 * 2) << 2, /* offset */0L << 2); // float at index 0
		glColorPointer(3, GL_FLOAT, /* stride */(3 * 2) << 2, /* offset */(3 * 1) << 2); // float at index 3

		glDrawArrays(GL_TRIANGLES, 0, 3 /* elements */);

//		glDisableClientState(GL_COLOR_ARRAY);
//		glDisableClientState(GL_VERTEX_ARRAY);

		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	@Override
	public void setColor(Color color) {
		// TODO Auto-generated method stub

	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void begin() {
//		glEnableClientState(GL_VERTEX_ARRAY);
//		glEnableClientState(GL_COLOR_ARRAY);
//
//		glBindBuffer(GL_ARRAY_BUFFER, vbo);
//		glBufferData(GL_ARRAY_BUFFER, (9 + 9) << 2, GL_DYNAMIC_DRAW);
//
//		buffer = GLContext.getCapabilities().OpenGL30 ? glMapBufferRange(GL_ARRAY_BUFFER, 0, (9 + 9) << 2, GL_MAP_WRITE_BIT, null) : //
//		GLContext.getCapabilities().GL_ARB_map_buffer_range ? ARBMapBufferRange.glMapBufferRange(GL_ARRAY_BUFFER, 0, (9 + 9) << 2, ARBMapBufferRange.GL_MAP_WRITE_BIT | ARBMapBufferRange.GL_MAP_UNSYNCHRONIZED_BIT, null) : //
//		glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY, buffer);
//		System.out.println("byteBuffer = " + buffer);
//		buffer = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY, buffer);
//
//		if (buffer != null) floatBuffer = buffer.asFloatBuffer();
		
		floatBuffer.rewind();
	}

	@Override
	public void end() {
		buffer.flip();
//		glUnmapBuffer(GL_ARRAY_BUFFER);
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);

		glVertexPointer(3, GL_FLOAT, /* stride */(3 * 2) << 2, /* offset */0L << 2); // float at index 0
		glColorPointer(3, GL_FLOAT, /* stride */(3 * 2) << 2, /* offset */(3 * 1) << 2); // float at index 3

		glDrawArrays(GL_TRIANGLES, 0, 3 /* elements */);

		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	@Override
	public void dispose() {
		glDeleteBuffers(vbo);
	}

	@Override
	public void translate(float x, float y, float z) {
		// TODO Auto-generated method stub

	}

	@Override
	public void scale(float sx, float sy, float sz) {
		// TODO Auto-generated method stub

	}

	@Override
	public void rotate(double theta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawLine(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawRect(int x, int y, int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fillRect(int x, int y, int width, int height) {
		floatBuffer.put(x).put(y).put(0);
		floatBuffer.put(1).put(0).put(0);

		floatBuffer.put(x + width).put(y).put(0);
		floatBuffer.put(0).put(1).put(0);

		floatBuffer.put(x + width).put(y + height).put(0);
		floatBuffer.put(0).put(0).put(1);
	}

	@Override
	public void drawCube(int x, int y, int z, int width, int height, int depth) {
		// TODO Auto-generated method stub

	}

	@Override
	public void push() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pop() {
		// TODO Auto-generated method stub

	}
}
