/**
 * 
 */
package org.gamelib.util;

import java.util.ArrayList;
import java.util.List;

import org.gamelib.util.math.Math2;

/**
 * @author pwnedary
 */
public enum Direction {
	// EAST(0), NORTH(90), WEST(180), SOUTH(270);
	/***/
	LEFT(-1, 0),
	/***/
	TOPLEFT(-1, -1),
	/***/
	TOP(0, -1),
	/***/
	TOPRIGHT(1, -1),
	/***/
	RIGHT(1, 0),
	/***/
	DOWNRIGHT(1, 1),
	/***/
	DOWN(0, 1),
	/***/
	DOWNLEFT(-1, 1);

	public final int xpos;
	public final int ypos;
	/** Rotation in radians. */
	private final double rotation;

	private Direction(int xpos, int ypos) {
		this.xpos = xpos;
		this.ypos = ypos;
		this.rotation = Math.atan2(ypos, xpos);
	}

	/*
	 * public int xpos() { return Math2.sign((int) Math.cos(Math.toRadians(degrees))); } public int ypos() { return Math2.sign((int) Math.sin(Math.toRadians(degrees))); }
	 */

	/**
	 * Returns the rotation in radians.
	 * @return the rotation
	 */
	public double getRotation() {
		return rotation;
	}

	public boolean isDiagonal() {
		return xpos != 0 && ypos != 0;
	}

	public static Direction getDirection(int x, int y) {
		x = Math2.sign(x);
		y = Math2.sign(y);
		System.out.println("x: " + x + " y: " + y);
		for (Direction direction : values()) {
			System.out.print(direction.name());
			System.out.println("x: " + direction.xpos + " y: " + direction.ypos);
			if (direction.xpos == x && direction.ypos == y) return direction;
		}
		return null;
	}

	public static Iterable<Direction> getDirections(boolean diagonally) {
		List<Direction> list = new ArrayList<>(diagonally ? 8 : 4);
		Direction[] values = values();
		for (int i = 0; i < values.length; i += diagonally ? 1 : 2)
			list.add(values[i]);
		return list;
	}
}
