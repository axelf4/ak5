/**
 * 
 */
package org.gamelib.util.geom;

/**
 * A 2D vector. Allows chaining operations by returning a reference to itself in all modification methods.
 * 
 * @author pwnedary
 */
public class Vector2 implements Vector<Vector2> {

	/** the X coordinate **/
	public float x;
	/** the Y coordinate **/
	public float y;

	/**
	 * Constructs a vector with the given coordinates
	 * 
	 * @param x The X coordinate
	 * @param y The Y coordinate
	 */
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/** Constructs a vector at 0, 0 */
	public Vector2() {
		this(.0f, .0f);
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}
	
	@Override
	public float getZ() {
		return .0f;
	}

	@Override
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	@Override
	public float lengthSquared() {
		return x * x + y * y;
	}

	@Override
	public float dst(Vector3 v) {
		final float x_d = v.x - x;
		final float y_d = v.y - y;
		return (float) Math.sqrt(x_d * x_d + y_d * y_d);
	}

	@Override
	public Vector2 normalize() {
		float length = length();
		if (length != 0) {
			x /= length;
			y /= length;
		}
		return this;
	}

	/**
	 * Calculates the 2D cross product between this and the given vector.
	 * 
	 * @param v the other vector
	 * @return the cross product
	 */
	public float crs(Vector2 v) {
		return this.x * v.y - this.y * v.x;
	}

	@Override
	public float dot(Vector2 v) {
		return x * v.x + y * v.y;
	}

	public Vector2 add(Vector2 v) {
		x += v.x;
		y += v.y;
		return this;
	}

	@Override
	public Vector2 sub(Vector2 v) {
		x -= v.x;
		y -= v.y;
		return this;
	}

	@Override
	public String toString() {
		return "[" + x + ":" + y + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Vector2 other = (Vector2) obj;
		return Float.floatToIntBits(x) == Float.floatToIntBits(other.x) && Float.floatToIntBits(y) == Float.floatToIntBits(other.y);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return new Vector2(x, y);
	}

	/**
	 * @return the angle in degrees of this vector (point) relative to the x-axis. Angles are counter-clockwise and between 0 and 360.
	 */
	public float angle() {
		float angle = (float) Math.toDegrees(Math.atan2(y, x));
		if (angle < 0) angle += 360;
		return angle;
	}

}
