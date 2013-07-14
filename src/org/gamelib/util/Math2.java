/**
 * 
 */
package org.gamelib.util;

import static java.lang.Math.*;

/**
 * rename to MathUtils or Math2
 * @author pwnedary
 */
public class Math2 {

	public static final float radiansToDegrees = 180f / (float) PI;
	public static final float degreesToRadians = (float) PI / 180;

	/**
	 * @param i an integer
	 * @return 1 if the integer is positive, -1 if negative and 0 if 0
	 */
	public static int sign(float i) {
		return i == 0 ? 0 : i < 0 ? -1 : 1;
	}

	/**
	 * @param i an integer
	 * @return 1 if the integer is positive, -1 if negative and 0 if 0
	 */
	public static int sign2(float i) {
		return i == 0 ? 0 : i < 1 ? 1 : i > -1 ? -1 : 0;
	}

	public static int clamp(int i, int min, int max) {
		return i < min ? min : i > max ? max : i;
	}

	public static float clamp(float i, float min, float max) {
		return i < min ? min : i > max ? max : i;
	}

	/** @deprecated in favor of {@link Range} */
	@Deprecated
	public static boolean inRange(int i, int min, int max) {
		return i <= max && i >= min;
	}

	public static int log2(int i) {
		return i == 0 ? 0 : 32 - Integer.numberOfLeadingZeros(i - 1);
	}

	/** @return the nearest power of 2 */
	public static int pot(int i) {
		return i == 0 ? 0 : (int) Math.pow(2, 32 - Integer.numberOfLeadingZeros(i - 1));
	}

	/**
	 * @return the nearest power of 2
	 * @deprecated replaced by {@link #pot(int)}
	 */
	public static int npow2(int i) {
		return i == 0 ? 0 : (int) Math.pow(2, 32 - Integer.numberOfLeadingZeros(i - 1));
	}
}
