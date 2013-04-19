/**
 * 
 */
package org.gamelib.backend.java2D;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.gamelib.backend.Graphics;
import org.gamelib.backend.Image;

/**
 * @author pwnedary
 */
public class Java2DGraphics implements Graphics {

	private java.awt.Graphics g;
	@Deprecated
	private Java2dPanel panel;

	public Java2DGraphics(Java2dPanel panel) {
		this.panel = panel;
	}

	public Java2DGraphics(java.awt.Graphics g) {
		this.g = g;
	}
	
	/**debug*/
	public java.awt.Graphics getG() {
		return g;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Graphics#setColor(java.awt.Color)
	 */
	@Override
	public void setColor(Color c) {
		g.setColor(c);
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Graphics#drawImage(java.awt.Image, int, int, int, int, int, int, int, int)
	 */
	@Override
	public void drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2) {
		// panel.graphics2d.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
		BufferedImage bufferedImage = ((Java2DImage) img).bufferedImage;
		g.drawImage(bufferedImage, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
		// bufferedImage.flush();
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Graphics#fillRect(int, int, int, int)
	 */
	@Override
	public void fillRect(int x, int y, int width, int height) {
		g.fillRect(x, y, width, height);
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Graphics#drawRect(int, int, int, int)
	 */
	@Override
	public void drawRect(int x, int y, int width, int height) {
		g.drawRect(x, y, width, height);
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Graphics#drawLine(int, int, int, int)
	 */
	@Override
	public void drawLine(int x1, int y1, int x2, int y2) {
		g.drawLine(x1, y1, x2, y2);
	}

	@Override
	public void begin() {
		// TODO Auto-generated method stub

	}

	@Override
	public void end() {
		g.dispose();
	}

	/* (non-Javadoc)
	 * @see org.gamelib.backend.Graphics#dispose()
	 */
	@Override
	public void dispose() {
		g.dispose();
	}

}
