/**
 * 
 */
package org.gamelib.util;

import org.gamelib.backend.Graphics;

/**
 * @author pwnedary
 */
public interface Font {

	/** The plain style constant. */
	public static final int PLAIN = 0;
	/** The bold style constant. */
	public static final int BOLD = 1;
	/** The italicized style constant. */
	public static final int ITALIC = 2;
	/** The underline style constant. */
	public static final int UNDERLINE = 3;

	public static final int ALIGN_LEFT = 0;
	public static final int ALIGN_RIGHT = 1;
	public static final int ALIGN_CENTER = 2;

	public void drawString(Graphics g, String str, int x, int y);

	public int getWidth(String str);

	public int getHeight();

}
