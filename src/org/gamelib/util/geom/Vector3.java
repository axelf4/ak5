/**
 * 
 */
package org.gamelib.util.geom;

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
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.util.geom.Vector#lengthSquared()
	 */
	@Override
	public float lengthSquared() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.util.geom.Vector#normalize()
	 */
	@Override
	public Vector3 normalize() {
		// TODO Auto-generated method stub
		return null;
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

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
