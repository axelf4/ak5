/**
 * 
 */
package org.gamelib.backend;

import java.beans.ConstructorProperties;
import java.io.Serializable;

/**
 * Colors in the sRGB color space. TODO add color spaces use {@link Float#floatToRawIntBits(float)}.
 * @author pwnedary
 * @see java.awt.Color
 */
public class Color implements Serializable {
	private static final long serialVersionUID = 4649728280397747213L;

	/** The color white. */
	public static final Color WHITE = new Color(255, 255, 255);
	/** The color light gray. */
	public static final Color LIGHT_GRAY = new Color(192, 192, 192);
	/** The color gray. */
	public static final Color GRAY = new Color(128, 128, 128);
	/** The color dark gray. */
	public static final Color DARK_GRAY = new Color(64, 64, 64);
	/** The color black. */
	public static final Color BLACK = new Color(0, 0, 0);
	/** The color red. */
	public static final Color RED = new Color(255, 0, 0);
	/** The color pink. */
	public static final Color PINK = new Color(255, 175, 175);
	/** The color orange. */
	public static final Color ORANGE = new Color(255, 200, 0);
	/** The color yellow. */
	public static final Color YELLOW = new Color(255, 255, 0);
	/** The color green. */
	public static final Color GREEN = new Color(0, 255, 0);
	/** The color magenta. */
	public static final Color MAGENTA = new Color(255, 0, 255);
	/** The color cyan. */
	public static final Color CYAN = new Color(0, 255, 255);
	/** The color blue. */
	public static final Color BLUE = new Color(0, 0, 255);

	/** The color value. */
	final int value;

	/**
	 * Creates an sRGB color with the red, green, blue and alpha values in the range (0 - 255).
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
	 * Creates an sRGB color with the red, green and blue values in the range (0.0 - 1.0).
	 * @param r the red component
	 * @param g the green component
	 * @param b the blue component
	 * @param a the alpha component
	 */
	public Color(float r, float g, float b, float a) {
		this((int) (r * 255 + 0.5), (int) (g * 255 + 0.5), (int) (b * 255 + 0.5), (int) (a * 255 + 0.5));
	}

	/**
	 * Creates an opaque sRGB color with the red, green and blue values in the range (0 - 255).
	 * @param r the red component
	 * @param g the green component
	 * @param b the blue component
	 * @param a the alpha component
	 */
	public Color(int r, int g, int b) {
		this(r, g, b, 255);
	}

	/**
	 * Checks the color integer components supplied for validity. Throws an {@link IllegalArgumentException} if the value is out of range.
	 * @param r the red component
	 * @param g the green component
	 * @param b the blue component
	 * @param a the alpha component
	 **/
	private static void testColorValueRange(int r, int g, int b, int a) {
		boolean rangeError = true;
		String badComponentString = "";

		if (a < 0 || a > 255) badComponentString += " Alpha";
		if (r < 0 || r > 255) badComponentString += " Red";
		if (g < 0 || g > 255) badComponentString = " Green";
		if (b < 0 || b > 255) badComponentString += " Blue";
		else rangeError = false;
		if (rangeError == true) throw new IllegalArgumentException("Color parameter outside of expected range:" + badComponentString);
	}

	/**
	 * Returns the red component in the range 0-255 in the default sRGB space.
	 * @return the red component.
	 * @see #getRGB
	 */
	public int getRed() {
		return (value >> 16) & 0xFF;
	}

	/**
	 * Returns the green component in the range 0-255 in the default sRGB space.
	 * @return the green component.
	 * @see #getRGB
	 */
	public int getGreen() {
		return (value >> 8) & 0xFF;
	}

	/**
	 * Returns the blue component in the range 0-255 in the default sRGB space.
	 * @return the blue component.
	 * @see #getRGB
	 */
	public int getBlue() {
		return (value >> 0) & 0xFF;
	}

	/**
	 * Returns the alpha component in the range 0-255.
	 * @return the alpha component.
	 * @see #getRGB
	 */
	public int getAlpha() {
		return (value >> 24) & 0xff;
	}

	/**
	 * Returns the AWT version of this color.
	 * @return the AWT version
	 * @see java.awt.Color
	 */
	public java.awt.Color toAWT() {
		return new java.awt.Color(getRed(), getGreen(), getBlue(), getAlpha());
	}

}
