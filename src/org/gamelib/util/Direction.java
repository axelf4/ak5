/**
 * 
 */
package org.gamelib.util;


/**
 * @author Axel
 * 
 */
public enum Direction {
	EAST(0), NORTH(90), WEST(180), SOUTH(270);

	private int degrees;

	private Direction(int degrees) {
		this.degrees = degrees;
	}

	/**
	 * @return the degrees
	 */
	public int getDegrees() {
		return degrees;
	}

	public int toX() {
		return Math2D.sign((int) Math.cos(Math.toRadians(degrees)));
	}

	public int toY() {
		return Math2D.sign((int) Math.sin(Math.toRadians(degrees)));
	}

	public static Direction getDirection(int x, int y) {
		x = Math2D.sign(x);
		y = Math2D.sign(y);
		System.out.println("x: " + x + " y: " + y);
		for (Direction direction : values()) {
			System.out.print(direction.name());
			System.out.println("x: " + direction.toX() + " y: " + direction.toY());
			if (direction.toX() == x && direction.toY() == y)
				return direction;
		}
		return null;
	}
}
