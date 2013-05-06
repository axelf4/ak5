/**
 * 
 */
package org.gamelib.backend.java2D;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.gamelib.backend.Graphics;
import org.gamelib.backend.Image;
import org.gamelib.util.Color;

/**
 * @author pwnedary
 */
public class Java2DGraphics implements Graphics {

	private java.awt.Graphics g;
	int width, height;

	public Java2DGraphics(java.awt.Graphics g, int width, int height) {
		this.g = g;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Graphics#setColor(java.awt.Color)
	 */
	@Override
	public void setColor(Color c) {
		g.setColor(new java.awt.Color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()));
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

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backend.Graphics#dispose()
	 */
	@Override
	public void dispose() {
		g.dispose();
	}

	/** @return the awt {@link Graphics2D} used to draw */
	public Graphics2D getGraphics2D() {
		return (Graphics2D) g;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backend.Graphics#clear()
	 */
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		g.fillRect(0, 0, width, height);
	}
}
