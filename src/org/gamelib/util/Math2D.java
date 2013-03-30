/**
 * 
 */
package org.gamelib.util;

import static java.lang.Math.*;

/**
 * @author Axel
 * 
 */
public class Math2D {

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

	public static int range(int i, int min, int max) {
		return min(min, max(i, max));
	}

	public static float range(float i, float min, float max) {
		return i < min ? min : i > max ? max : i;
	}

	public static boolean inRange(int i, int min, int max) {
		return i <= max && i >= min;
	}
}
