/**
 * 
 */
package org.gamelib.backend.java2D;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.gamelib.backend.Graphics;
import org.gamelib.util.Font.FontImpl;

/**
 * @author pwnedary
 * @see java.awt.Font
 */
public class AWTFont extends FontImpl {

	private java.awt.Font font;
	private FontMetrics metrics;

	/**
	 * 
	 */
	public AWTFont(java.awt.Font font) {
		this.font = font;
		this.metrics = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_BINARY).createGraphics().getFontMetrics(font);
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.util.Font#drawString(java.awt.Graphics2D, java.lang.String, int, int)
	 */
	@Override
	public void drawString(Graphics g, String str, int x, int y) {
		Java2DGraphics g2 = (Java2DGraphics) g;
		Graphics2D g2d = g2.getGraphics2D();
		java.awt.Font tmp = g2d.getFont();

		g2d.setFont(font);
		g2d.drawString(str, x, y);
		g2d.setFont(tmp);
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.util.Font#getWidth(java.lang.String)
	 */
	@Override
	public int getWidth(String str) {
		return metrics.stringWidth(str);
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.util.Font#getHeight()
	 */
	@Override
	public int getHeight() {
		return metrics.getHeight();
	}

}
