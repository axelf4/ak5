/**
 * 
 */
package org.gamelib.util;

import java.beans.ConstructorProperties;
import java.io.Serializable;

/**
 * Colors in the sRGB color space. TODO add color spaces use {@link Float#floatToRawIntBits(float)}.
 * 
 * @author pwnedary
 * @see java.awt.Color
 */
public class Color implements Serializable {
	/** The color white. */
	public static final Color WHITE = new Color(255, 255, 255);
	public static final Color BLACK = new Color(0, 0, 0);
	public static final Color RED = new Color(255, 0, 0);
	public static final Color GREEN = new Color(0, 255, 0);
	public static final Color BLUE = new Color(0, 0, 255);

	/** The color value. */
	int value;

	/**
	 * Creates an sRGB color with the specified red, green, blue, and alpha values in the range (0 - 255).
	 * 
	 * @param r the red component
	 * @param g the green component
	 * @param b the blue component
	 * @param a the alpha component
	 */
	@ConstructorProperties({ "red", "green", "blue", "alpha" })
	public Color(int r, int g, int b, int a) {
		value = ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | ((b & 0xFF) << 0);
		testColorValueRange(r, g, b, a);
	}

	/**
	 * Creates an opaque sRGB color with the specified red, green, and blue values in the range (0.0 - 1.0).
	 */
	public Color(float r, float g, float b, float a) {
		this((int) (r * 255 + 0.5), (int) (g * 255 + 0.5), (int) (b * 255 + 0.5), (int) (a * 255 + 0.5));
	}

	public Color(int r, int g, int b) {
		this(r, g, b, 255);
	}

	/**
	 * Checks the color integer components supplied for validity. Throws an {@link IllegalArgumentException} if the value is out of range.
	 * 
	 * @param r the Red component
	 * @param g the Green component
	 * @param b the Blue component
	 **/
	private static void testColorValueRange(int r, int g, int b, int a) {
		boolean rangeError = false;
		String badComponentString = "";

		if (a < 0 || a > 255) {
			rangeError = true;
			badComponentString = badComponentString + " Alpha";
		}
		if (r < 0 || r > 255) {
			rangeError = true;
			badComponentString = badComponentString + " Red";
		}
		if (g < 0 || g > 255) {
			rangeError = true;
			badComponentString = badComponentString + " Green";
		}
		if (b < 0 || b > 255) {
			rangeError = true;
			badComponentString = badComponentString + " Blue";
		}
		if (rangeError == true) {
			throw new IllegalArgumentException("Color parameter outside of expected range:" + badComponentString);
		}
	}

	/**
	 * Returns the red component in the range 0-255 in the default sRGB space.
	 * 
	 * @return the red component.
	 * @see #getRGB
	 */
	public int getRed() {
		return (value >> 16) & 0xFF;
	}

	/**
	 * Returns the green component in the range 0-255 in the default sRGB space.
	 * 
	 * @return the green component.
	 * @see #getRGB
	 */
	public int getGreen() {
		return (value >> 8) & 0xFF;
	}

	/**
	 * Returns the blue component in the range 0-255 in the default sRGB space.
	 * 
	 * @return the blue component.
	 * @see #getRGB
	 */
	public int getBlue() {
		return (value >> 0) & 0xFF;
	}

	/**
	 * Returns the alpha component in the range 0-255.
	 * 
	 * @return the alpha component.
	 * @see #getRGB
	 */
	public int getAlpha() {
		return (value >> 24) & 0xff;
	}

}
