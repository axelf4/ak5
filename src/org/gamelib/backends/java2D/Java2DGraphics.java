/**
 * 
 */
package org.gamelib.backends.java2D;

import java.awt.Color;

import org.gamelib.graphics.Graphics;
import org.gamelib.graphics.Image;

/**
 * @author pwnedary
 * 
 */
public class Java2DGraphics implements Graphics {

	private Java2dPanel panel;

	public Java2DGraphics(Java2dPanel panel) {
		this.panel = panel;
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.Graphics#setColor(java.awt.Color) */
	@Override
	public void setColor(Color c) {
		panel.graphics2d.setColor(c);
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.Graphics#drawImage(java.awt.Image, int, int, int, int,
	 * int, int, int, int) */
	@Override
	public void drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2) {
		// panel.graphics2d.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.Graphics#fillRect(int, int, int, int) */
	@Override
	public void fillRect(int x, int y, int width, int height) {
		panel.graphics2d.fillRect(x, y, width, height);
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.Graphics#drawString(java.lang.String, int, int) */
	@Override
	public void drawString(String str, int x, int y) {
		panel.graphics2d.drawString(str, x, y);
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.Graphics#drawRect(int, int, int, int) */
	@Override
	public void drawRect(int x, int y, int width, int height) {
		panel.graphics2d.drawRect(x, y, width, height);
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.Graphics#drawLine(int, int, int, int) */
	@Override
	public void drawLine(int x1, int y1, int x2, int y2) {
		panel.graphics2d.drawLine(x1, y1, x2, y2);
	}

}
