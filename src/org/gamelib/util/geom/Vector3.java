/**
 * 
 */
package org.gamelib.util.geom;

import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * A 3D vector. Allows chaining operations by returning a reference to itself in all modification methods.
 * @author pwnedary
 */
public class Vector3 implements Vector<Vector3> {

	/** the x coordinate of this vector **/
	public float x;
	/** the y coordinate of this vector **/
	public float y;
	/** the z coordinate of this vector **/
	public float z;

	/**
	 * Creates a vector with the given components
	 * @param x The x-component
	 * @param y The y-component
	 * @param z The z-component
	 */
	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.util.geom.Vector#length()
	 */
	@Override
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.util.geom.Vector#lengthSquared()
	 */
	@Override
	public float lengthSquared() {
		return x * x + y * y + z * z;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.util.geom.Vector#dst(org.gamelib.util.geom.Vector3)
	 */
	@Override
	public float dst(Vector3 v) {
		float a = v.x - x;
		float b = v.y - y;
		float c = v.z - z;
		return (float) Math.sqrt((a *= a) + (b *= b) + (c *= c));
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.util.geom.Vector#normalize()
	 */
	@Override
	public Vector3 normalize() {
		float length = this.length();
		if (length == 0) return this;
		else {
			float x = this.x / length;
			float y = this.y / length;
			float z = this.z / length;
			return new Vector3(x, y, z);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.util.geom.Vector#cross(org.gamelib.util.geom.Vector)
	 */
	public Vector3 cross(Vector3 v) {
		return new Vector3(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.util.geom.Vector#dot(org.gamelib.util.geom.Vector)
	 */
	@Override
	public float dot(Vector3 v) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.util.geom.Vector#add(org.gamelib.util.geom.Vector)
	 */
	public Vector3 add(Vector3 v) {
		x += v.x;
		y += v.y;
		z += v.z;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.util.geom.Vector#sub(org.gamelib.util.geom.Vector)
	 */
	@Override
	public Vector3 sub(Vector3 v) {
		x -= v.x;
		y -= v.y;
		z -= v.z;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return x + "," + y + "," + z;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		result = prime * result + Float.floatToIntBits(z);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Vector3 other = (Vector3) obj;
		return Float.floatToIntBits(x) == Float.floatToIntBits(other.x) && Float.floatToIntBits(y) == Float.floatToIntBits(other.y) && Float.floatToIntBits(z) == Float.floatToIntBits(other.z);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new Vector3(x, y, z);
	}

}
