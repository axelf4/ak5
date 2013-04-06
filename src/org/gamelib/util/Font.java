/**
 * 
 */
package org.gamelib.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * 
 * this.metrics = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_GRAY).getGraphics().getFontMetrics(font);
 * @author pwnedary
 * 
 */
public interface Font {

	/* Constants to be used for styles. Can be combined to mix styles. */

	/**
	 * The plain style constant.
	 */
	public static final int PLAIN = 0;

	/**
	 * The bold style constant. This can be combined with the other style
	 * constants (except PLAIN) for mixed styles.
	 */
	public static final int BOLD = 1;

	/**
	 * The italicized style constant. This can be combined with the other style
	 * constants (except PLAIN) for mixed styles.
	 */
	public static final int ITALIC = 2;

	public void drawString(Graphics2D g2d, String str, int x, int y);

	public int getWidth(String str);

	public int getHeight();

}
