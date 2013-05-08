/**
 * 
 */
package org.gamelib.backend.lwjgl;

import static org.lwjgl.opengl.GL11.*;

import org.gamelib.backend.Graphics;
import org.gamelib.backend.Image;
import org.gamelib.util.Color;
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
		GL11.glClearColor(r, g, b, a);
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Graphics#drawImage(java.awt.Image, int, int, int, int, int, int, int, int)
	 */
	@Override
	public void drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2) {
		LWJGLImage image = (LWJGLImage) img;

		// store the current model matrix
		// glPushMatrix();
		/*glEnable(GL_TEXTURE_2D);

		// bind to the appropriate texture for this sprite
		// image.bind();
		GL11.glBindTexture(image.target, image.textureID);
		// glTranslatef(dx1, dy1, 0); translate to the right location and prepare to draw

		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2f(0f, 0f);
			GL11.glVertex2f(dx1, dx1);
			GL11.glTexCoord2f(1f, 0f);
			GL11.glVertex2f(dx2, dy1);
			GL11.glTexCoord2f(1f, 1f);
			GL11.glVertex2f(dx2, dy2);
			GL11.glTexCoord2f(0f, 1f);
			GL11.glVertex2f(dx1, dy2);
		}
		GL11.glEnd();
		glBindTexture(GL_TEXTURE_2D, 0);*/
		
		
		// restore the model room matrix to prevent contamination
		// glPopMatrix();
		
		// store the current model matrix
				glPushMatrix();

				// bind to the appropriate texture for this sprite
				glBindTexture(GL_TEXTURE_2D, image.getTextureID());

				// translate to the right location and prepare to draw
				glTranslatef(dx1, dy1, 0);

				// draw a quad textured to match the sprite
				glBegin(GL_QUADS);
				{
					glTexCoord2f(0, 0);
					glVertex2f(0, 0);

					glTexCoord2f(0, sy2);
					glVertex2f(0, dy2);

					glTexCoord2f(sx2, sy2);
					glVertex2f(dx2, dy2);

					glTexCoord2f(sx2, 0);
					glVertex2f(dx2, 0);
				}
				glEnd();

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
		{
			GL11.glVertex2d(x1, y1);
			GL11.glVertex2d(x2, y2);
		}
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
	 * @see org.gamelib.backend.Graphics#dispose()
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear() {
		// clear the screen and depth buffer
		// GL11.glClearColor(0, 0, 0, 0);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		// GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // 3d
	}

}
