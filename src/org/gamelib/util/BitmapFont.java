/**
 * 
 */
package org.gamelib.util;

import org.gamelib.backend.Graphics;

/**
 * @author pwnedary
 */
public class BitmapFont implements Font {

	/** The highest glyph code allowed */
	static private final int MAX_GLYPH_CODE = 0x10FFFF;
	/** The number of glyphs on a page */
	private static final int PAGE_SIZE = 512;
	/** The number of pages */
	private static final int PAGES = MAX_GLYPH_CODE / PAGE_SIZE;

	/**
	 * 
	 */
	public BitmapFont() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.util.Font#drawString(java.awt.Graphics2D, java.lang.String, int, int)
	 */
	@Override
	public void drawString(Graphics g, String str, int x, int y) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.util.Font#getWidth(java.lang.String)
	 */
	@Override
	public int getWidth(String str) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.util.Font#getHeight()
	 */
	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

}
