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
import org.lwjgl.util.glu.GLU;

/**
 * @author pwnedary
 */
public class LWJGLGraphics implements Graphics {
	/** The current color */
	private Color currentColor = Color.BLACK;
	protected LWJGLImage image;

	public LWJGLGraphics() {}

	public LWJGLGraphics(LWJGLImage img) {
		this.image = img;
	}

	static int dimension = 0;

	private void predraw(int dimension) {
		if (dimension != LWJGLGraphics.dimension) {
			if (dimension == 2) {
				glMatrixMode(GL_PROJECTION);
				glLoadIdentity();
				glOrtho(0, Display.getWidth(), 0, Display.getHeight(), 1, -1); // 0,0 at bottom left
				// glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1); // 0,0 at top left
				glMatrixMode(GL_MODELVIEW);

				glDisable(GL_DEPTH_TEST);
				// glDisable(GL_LIGHTING);
			} else if (dimension == 3) {
				GL11.glMatrixMode(GL11.GL_PROJECTION); // resets any previous projection matrices
				GL11.glLoadIdentity();
				GLU.gluPerspective(45.0f, (float) Display.getWidth() / (float) Display.getHeight(), 0.1f, 100.0f);
				GL11.glMatrixMode(GL11.GL_MODELVIEW);

				GL11.glEnable(GL11.GL_DEPTH_TEST);
				GL11.glDepthFunc(GL11.GL_LEQUAL);
				GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
			}
			LWJGLGraphics.dimension = dimension;
		}
	}

	@Override
	public void setColor(Color color) {
		this.currentColor = color;
		float r = (float) currentColor.getRed() / 255;
		float g = (float) currentColor.getGreen() / 255;
		float b = (float) currentColor.getBlue() / 255;
		float a = (float) currentColor.getAlpha() / 255;
		glColor4f(r, g, b, a);
		glClearColor(r, g, b, a);
	}

	@Override
	public Color getColor() {
		return currentColor;
	}

	@Override
	public void drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2) {
		predraw(2);
		LWJGLImage image = (LWJGLImage) img;
		float u = (float) sx1 / image.getTexWidth();
		float v = (float) sy1 / image.getTexHeight();
		float u2 = (float) sx2 / image.getTexWidth();
		float v2 = (float) sy2 / image.getTexHeight();

		glEnable(GL_TEXTURE_2D);
		glBindTexture(image.target, image.texture); // bind the appropriate texture for this image
		glBegin(GL11.GL_TRIANGLE_STRIP); // GL_QUADS
		{
			glTexCoord2f(u, v);
			glVertex2f(dx1, dy1);
			glTexCoord2f(u, v2);
			glVertex2f(dx1, dy2);
			glTexCoord2f(u2, v);
			glVertex2f(dx2, dy1);
			glTexCoord2f(u2, v2);
			glVertex2f(dx2, dy2);
		}
		glEnd();
		glBindTexture(image.target, 0);
		glDisable(GL_TEXTURE_2D);
	}

	@Override
	public void drawLine(int x1, int y1, int x2, int y2) {
		predraw(2);
		GL11.glBegin(GL11.GL_LINE_STRIP);
		{
			GL11.glVertex2i(x1, y1);
			GL11.glVertex2i(x2, y2);
		}
		GL11.glEnd();
	}

	@Override
	public void drawRect(int x, int y, int width, int height) {
		predraw(2);
		drawLine(x, y, x + width, y);
		drawLine(x + width, y, x + width, y + height);
		drawLine(x + width, y + height, x, y + height);
		drawLine(x, y + height, x, y);
	}

	@Override
	public void fillRect(int x, int y, int width, int height) {
		predraw(2);
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glVertex2i(x, y);
			GL11.glVertex2i(x + width, y);
			GL11.glVertex2i(x + width, y + height);
			GL11.glVertex2i(x, y + height);
		}
		GL11.glEnd();
	}

	@Override
	public void clear() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | (GL11.glIsEnabled(GL11.GL_DEPTH_TEST) ? GL11.GL_DEPTH_BUFFER_BIT : 0)); // clear the screen and depth buffer
	}

	@Override
	public void drawCube(int x, int y, int z, int width, int height, int depth) {
		predraw(3);
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

	@Override
	public void translate(float x, float y, float z) {
		glTranslatef(x, y, z);
	}

	@Override
	public void scale(float sx, float sy, float sz) {
		glScalef(sx, sy, sz);
	}

	@Override
	public void rotate(double theta) {
		glRotated(theta, 0.0d, 0.0d, 0.0d);
	}

	@Override
	public void begin() {}

	@Override
	public void end() {}

	@Override
	public void dispose() {}

	@Override
	public void push() {
		glPushMatrix();
	}

	@Override
	public void pop() {
		glPopMatrix();
	}

}
