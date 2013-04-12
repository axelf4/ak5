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
 * 
 */
public class LWJGLGraphics implements Graphics {

	/** The current color */
	private Color currentColor = Color.white;

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.Graphics#setColor(java.awt.Color) */
	@Override
	public void setColor(Color c) {
		this.currentColor = c;
		GL11.glColor3f(currentColor.getRed() / 255, currentColor.getGreen() / 255, currentColor.getBlue() / 255);
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.Graphics#drawImage(java.awt.Image, int, int, int, int,
	 * int, int, int, int) */
	@Override
	public void drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2) {
		LWJGLImage image = (LWJGLImage) img;

		glPushMatrix();
		glTranslatef(dx1, dy1, 0);
		image.bind();
		// glBindTexture(GL_TEXTURE_2D, textureID);
		glBegin(GL_QUADS);
		{
			glTexCoord2f(0, 0);
			glVertex2f(0, 0);

			glTexCoord2f(1, 0);
			glVertex2f(128, 0);

			glTexCoord2f(1, 1);
			glVertex2f(128, 128);

			glTexCoord2f(0, 1);
			glVertex2f(0, 128);
		}
		glEnd();
		glPopMatrix();
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.Graphics#drawLine(int, int, int, int) */
	@Override
	public void drawLine(int x1, int y1, int x2, int y2) {
		GL11.glColor3f(currentColor.getRed() / 255, currentColor.getGreen() / 255, currentColor.getBlue() / 255);
		GL11.glBegin(GL11.GL_LINE_STRIP);

		GL11.glVertex2d(x1, y1);
		GL11.glVertex2d(x2, y2);
		GL11.glEnd();
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.Graphics#drawRect(int, int, int, int) */
	@Override
	public void drawRect(int x, int y, int width, int height) {
		drawLine(x, y, x + width, y);
		drawLine(x + width, y, x + width, y + height);
		drawLine(x + width, y + height, x, y + height);
		drawLine(x, y + height, x, y);
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.Graphics#fillRect(int, int, int, int) */
	@Override
	public void fillRect(int x, int y, int width, int height) {
		GL11.glColor3f(currentColor.getRed() / 255, currentColor.getGreen() / 255, currentColor.getBlue() / 255);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x + width, y);
		GL11.glVertex2f(x + width, y + height);
		GL11.glVertex2f(x, y + height);
		GL11.glEnd();
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.Graphics#drawString(java.lang.String, int, int) */
	@Override
	public void drawString(String str, int x, int y) {
		// TODO Auto-generated method stub

	}

}
