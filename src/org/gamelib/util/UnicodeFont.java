/**
 * 
 */
package org.gamelib.util;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * @author pwnedary
 * 
 */
public class UnicodeFont implements Font {

	private java.awt.Font font;
	private FontMetrics metrics;

	/** The ascent of the font */
	private int ascent;
	/** The decent of the font */
	private int descent;
	/** The leading edge of the font */
	private int leading;
	/** The width of a space for the font */
	private int spaceWidth;

	/** The padding applied in pixels to the top of the glyph rendered area */
	private int paddingTop;
	/** The padding applied in pixels to the left of the glyph rendered area */
	private int paddingLeft;
	/** The padding applied in pixels to the bottom of the glyph rendered area */
	private int paddingBottom;
	/** The padding applied in pixels to the right of the glyph rendered area */
	private int paddingRight;
	/** The padding applied in pixels to horizontal advance for each glyph */
	private int paddingAdvanceX;
	/** The padding applied in pixels to vertical advance for each glyph */
	private int paddingAdvanceY;

	/**
	 * @param font
	 * 
	 */
	public UnicodeFont(java.awt.Font font) {
		this.font = font;
		this.metrics = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_GRAY).getGraphics().getFontMetrics(font);
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.util.Font#drawString(java.awt.Graphics2D,
	 * java.lang.String, int, int) */
	@Override
	public void drawString(Graphics2D g2d, String str, int x, int y) {
		java.awt.Font font = g2d.getFont(); // current font
		g2d.setFont(this.font);
		g2d.drawString(str, x, y);
		g2d.setFont(font);
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.util.Font#getWidth(java.lang.String) */
	@Override
	public int getWidth(String str) {
		return metrics.stringWidth(str);
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.util.Font#getHeight() */
	@Override
	public int getHeight() {
		return metrics.getHeight();
	}

}
