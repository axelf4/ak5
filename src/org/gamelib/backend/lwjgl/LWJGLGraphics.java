/**
 * 
 */
package org.gamelib.backend.lwjgl;

import static org.lwjgl.opengl.GL11.*;

import org.gamelib.backend.Color;
import org.gamelib.backend.Graphics;
import org.gamelib.backend.Image;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

/**
 * @author pwnedary
 */
public class LWJGLGraphics implements Graphics {

	/** the current color */
	private Color currentColor = Color.BLACK;
	protected LWJGLImage image;
	protected float deltaX, deltaY, deltaZ;

	/**
	 * 
	 */
	public LWJGLGraphics() {}

	/**
	 * 
	 */
	public LWJGLGraphics(LWJGLImage img) {
		this();
		this.image = img;
	}

	int d = 0;

	private void initGL(int d) {
		if (d != this.d) {
			if (d == 2) LWJGLBackend.init2d(Display.getWidth(), Display.getHeight());
			else if (d == 3) LWJGLBackend.init3d(Display.getWidth(), Display.getHeight());
			this.d = d;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setColor(Color color) {
		this.currentColor = color;
		float r = currentColor.getRed() / 255;
		float g = currentColor.getGreen() / 255;
		float b = currentColor.getBlue() / 255;
		float a = currentColor.getAlpha() / 255;
		// GL11.glColor3f(currentColor.getRed() / 255, currentColor.getGreen() /
		// 255, currentColor.getBlue() / 255);
		GL11.glColor4f(r, g, b, a);
		GL11.glClearColor(r, g, b, a);
	}

	/** {@inheritDoc} */
	@Override
	public Color getColor() {
		return currentColor;
	}

	/** {@inheritDoc} */
	@Override
	public void drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2) {
		initGL(2);
		LWJGLImage image = (LWJGLImage) img;
		float u = (float) sx1 / image.getTexWidth();
		float v = (float) sy1 / image.getTexHeight();
		float u2 = (float) sx2 / image.getTexWidth();
		float v2 = (float) sy2 / image.getTexHeight();
		int width = dx2 - dx1;
		int height = dy2 - dy1;
		Color previous = currentColor;

		setColor(Color.WHITE);
		translate(dx1, dy1, 0.0f);
		glEnable(GL_TEXTURE_2D);
		glBindTexture(image.target, image.textureID); // bind the appropriate texture for this image
		/*
		 * glBegin(GL11.GL_QUADS); // GL_QUADS { glTexCoord2f(u, v); glVertex2f(0, 0); glTexCoord2f(u2, v); glVertex2f(width, 0); glTexCoord2f(u2, v2); glVertex2f(width, height); glTexCoord2f(u, v2); glVertex2f(0, height); // glTexCoord2f(u, v); glVertex2f(dx1, dx1); glTexCoord2f(u2, v); glVertex2f(dx2, dy1); glTexCoord2f(u2, v2); glVertex2f(dx2, dy2); glTexCoord2f(u, v2); glVertex2f(dx1, dy2); }
		 */
		glBegin(GL11.GL_TRIANGLE_STRIP); // GL_QUADS
		{
			glTexCoord2f(u, v);
			glVertex2f(0, 0);

			glTexCoord2f(u, v2);
			glVertex2f(0, height);

			glTexCoord2f(u2, v);
			glVertex2f(width, 0);

			glTexCoord2f(u2, v2);
			glVertex2f(width, height);

			// glTexCoord2f(u, v); glVertex2f(dx1, dx1); glTexCoord2f(u2, v); glVertex2f(dx2, dy1); glTexCoord2f(u2, v2); glVertex2f(dx2, dy2); glTexCoord2f(u, v2); glVertex2f(dx1, dy2);
		}
		glEnd();
		glBindTexture(image.target, 0);
		glDisable(GL_TEXTURE_2D);
		translate(-dx1, -dy1, 0.0f);
		setColor(previous);
	}

	/** {@inheritDoc} */
	@Override
	public void drawLine(int x1, int y1, int x2, int y2) {
		initGL(2);
		GL11.glBegin(GL11.GL_LINE_STRIP);
		{
			GL11.glVertex2i(x1, y1);
			GL11.glVertex2i(x2, y2);
		}
		GL11.glEnd();
	}

	/** {@inheritDoc} */
	@Override
	public void drawRect(int x, int y, int width, int height) {
		initGL(2);
		drawLine(x, y, x + width, y);
		drawLine(x + width, y, x + width, y + height);
		drawLine(x + width, y + height, x, y + height);
		drawLine(x, y + height, x, y);
	}

	/** {@inheritDoc} */
	@Override
	public void fillRect(int x, int y, int width, int height) {
		initGL(2);
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glVertex2i(x, y);
			GL11.glVertex2i(x + width, y);
			GL11.glVertex2i(x + width, y + height);
			GL11.glVertex2i(x, y + height);
		}
		GL11.glEnd();
	}

	/** {@inheritDoc} */
	@Override
	public void dispose() {}

	/** {@inheritDoc} */
	@Override
	public void clear() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | (GL11.glIsEnabled(GL11.GL_DEPTH_TEST) ? GL11.GL_DEPTH_BUFFER_BIT : 0)); // clear the screen and depth buffer
	}

	/** {@inheritDoc} */
	@Override
	public void drawCube(int x, int y, int z, int width, int height, int depth) {
		initGL(3);
		int x2 = x + width;
		int y2 = y + height;
		int z2 = z + depth;
		GL11.glBegin(GL11.GL_QUADS);
		{
			// Front Face
			GL11.glVertex3f(x, y, z2); // bottom left of the texture and quad
			GL11.glVertex3f(x2, y, z2); // bottom right of the texture and quad
			GL11.glVertex3f(x2, y2, z2); // top right of the texture and quad
			GL11.glVertex3f(x, y2, z2); // top left of the texture and quad

			// Back Face
			GL11.glVertex3f(x, y, z); // bottom right of the texture and quad
			GL11.glVertex3f(x, y2, z); // top right of the texture and quad
			GL11.glVertex3f(x2, y2, z); // top left of the texture and quad
			GL11.glVertex3f(x2, y, z); // bottom left of the texture and quad

			// top Face
			GL11.glVertex3f(x, y2, z); // top left of the texture and quad
			GL11.glVertex3f(x, y2, z2); // bottom left of the texture and quad
			GL11.glVertex3f(x2, y2, z2); // bottom right of the texture and quad
			GL11.glVertex3f(x2, y2, z); // top right of the texture and quad

			// bottom Face
			GL11.glVertex3f(x, y, z); // top right of the texture and quad
			GL11.glVertex3f(x2, y, z); // top left of the texture and quad
			GL11.glVertex3f(x2, y, z2); // bottom left of the texture and quad
			GL11.glVertex3f(x, y, z2); // bottom right of the texture and quad

			// right face
			GL11.glVertex3f(x, y, z); // bottom right of the texture and quad
			GL11.glVertex3f(x, y2, z); // top right of the texture and quad
			GL11.glVertex3f(x, y2, z2); // top left of the texture and quad
			GL11.glVertex3f(x, y, z2); // bottom left of the texture and quad

			// left Face
			GL11.glVertex3f(x2, y, z); // bottom left of the texture and quad
			GL11.glVertex3f(x2, y, z2); // bottom right of the texture and quad
			GL11.glVertex3f(x2, y2, z2); // top right of the texture and quad
			GL11.glVertex3f(x2, y2, z); // top left of the texture and quad
		}
		GL11.glEnd();
	}

	/** {@inheritDoc} */
	@Override
	public void translate(float x, float y, float z) {
		glTranslatef(x, y, z);
		this.deltaX += x;
		this.deltaY += y;
	}

	/** {@inheritDoc} */
	@Override
	public void translate(float x, float y, float z, int flag) {
		if ((flag & ADD) == ADD) translate(x, y, z);
		else if ((flag & SET) == SET) translate(-deltaX + x, -deltaY + y, -deltaZ + z);
	}

	/** {@inheritDoc} */
	@Override
	public void scale(float sx, float sy, float sz) {
		glScalef(sx, sy, sz);
	}

	/** {@inheritDoc} */
	@Override
	public void rotate(double theta) {
		glRotated(theta, deltaX, deltaY, deltaZ);
	}

	@Override
	public void begin() {}

	@Override
	public void end() {}

}
