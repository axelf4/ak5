/**
 * 
 */
package org.gamelib.util.geom;

/**
 * Encapsulates a 2D vector. Allows chaining operations by returning a reference
 * to itself in all modification methods.
 * 
 * @author Axel
 * 
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

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.util.geom.Vector#length() */
	@Override
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.util.geom.Vector#lengthSquared() */
	@Override
	public float lengthSquared() {
		return x * x + y * y;
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.util.geom.Vector#normalize() */
	@Override
	public Vector2 normalize() {
		float length = length();
		if (length != 0) {
			x /= length;
			y /= length;
		}
		return this;
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.util.geom.Vector#dot(org.gamelib.util.geom.Vector) */
	@Override
	public float dot(Vector2 v) {
		return x * v.x + y * v.y;
	}

	/* (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString() */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[" + x + ":" + y + "]";
	}

	/* (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone() */
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new Vector2(x, y);
	}

	/**
	 * @return the angle in degrees of this vector (point) relative to the
	 *         x-axis. Angles are counter-clockwise and between 0 and 360.
	 */
	public float angle() {
		float angle = (float) Math.toDegrees(Math.atan2(y, x));
		if (angle < 0)
			angle += 360;
		return angle;
	}

}
