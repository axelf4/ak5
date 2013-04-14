/**
 * 
 */
package org.gamelib.backends.lwjgl;

import static org.lwjgl.opengl.GL11.*;
import java.awt.Color;

import org.gamelib.graphics.Graphics;
import org.gamelib.graphics.Image;
import org.lwjgl.opengl.GL11;

/**
 * @author pwnedary
 */
public class LWJGLGraphics implements Graphics {

	/** The current color */
	private Color currentColor = Color.BLACK;
	protected LWJGLImage image;

	/**
	 * 
	 */
	public LWJGLGraphics() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public LWJGLGraphics(LWJGLImage img) {
		this.image = img;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Graphics#setColor(java.awt.Color)
	 */
	@Override
	public void setColor(Color c) {
		this.currentColor = c;
		float r = currentColor.getRed() / 255;
		float g = currentColor.getGreen() / 255;
		float b = currentColor.getBlue() / 255;
		float a = currentColor.getAlpha() / 255;
		a = 1.0F;
		// GL11.glColor3f(currentColor.getRed() / 255, currentColor.getGreen() /
		// 255, currentColor.getBlue() / 255);
		// System.out.println(r + " " + g + " " + b + " " + a);
		GL11.glColor4f(r, g, b, a);
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Graphics#drawImage(java.awt.Image, int, int, int, int, int, int, int, int)
	 */
	@Override
	public void drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2) {
		LWJGLImage image = (LWJGLImage) img;

		// store the current model matrix
		glPushMatrix();

		// bind to the appropriate texture for this sprite
		// image.bind();
		GL11.glBindTexture(image.target, image.textureID);
		// translate to the right location and prepare to draw
		// glTranslatef(dx1, dy1, 0);

		GL11.glBegin(GL11.GL_QUADS);
		{
			// GL11.glTexCoord2f(sx1, sy1);
			GL11.glTexCoord2f(0f, 0f);
			GL11.glVertex2f(dx1, dx1);
			
			// GL11.glTexCoord2f(sx2, sy1);
			GL11.glTexCoord2f(1f, 0f);
			GL11.glVertex2f(dx2, dy1);
			
			// GL11.glTexCoord2f(sx2, sy2);
			GL11.glTexCoord2f(1f, 1f);
			GL11.glVertex2f(dx2, dy2);
			
			// GL11.glTexCoord2f(sx1, sy2);
			GL11.glTexCoord2f(0f, 1f);
			GL11.glVertex2f(dx1, dy2);
		}
		GL11.glEnd();
		// restore the model view matrix to prevent contamination
		glPopMatrix();
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Graphics#drawLine(int, int, int, int)
	 */
	@Override
	public void drawLine(int x1, int y1, int x2, int y2) {
		GL11.glBegin(GL11.GL_LINE_STRIP);

		GL11.glVertex2d(x1, y1);
		GL11.glVertex2d(x2, y2);
		GL11.glEnd();
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Graphics#drawRect(int, int, int, int)
	 */
	@Override
	public void drawRect(int x, int y, int width, int height) {
		drawLine(x, y, x + width, y);
		drawLine(x + width, y, x + width, y + height);
		drawLine(x + width, y + height, x, y + height);
		drawLine(x, y + height, x, y);
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Graphics#fillRect(int, int, int, int)
	 */
	@Override
	public void fillRect(int x, int y, int width, int height) {
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x + width, y);
		GL11.glVertex2f(x + width, y + height);
		GL11.glVertex2f(x, y + height);
		GL11.glEnd();
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Graphics#drawString(java.lang.String, int, int)
	 */
	@Override
	public void drawString(String str, int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void begin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

}
