/**
 * 
 */
package org.gamelib.util.geom;

/**
 * A general vector. Allows chaining operations by returning a reference to itself in all modification methods. See {@link Vector2} and {@link Vector3} for specific implementations.
 * 
 * @author pwnedary
 */
public interface Vector<T extends Vector<T>> extends Cloneable {

	/** @return the x-axis coordinate */
	public float getX();

	/** @return the y-axis coordinate */
	public float getY();

	/** @return the z-axis coordinate */
	public float getZ();

	/** @return the euclidian length (len) */
	public float length();

	/** @return the squared euclidian length (len2) */
	public float lengthSquared();

	/**
	 * @param vector the other vector
	 * @return the euclidian distance between this and the other vector
	 */
	public float dst(Vector3 v);

	/**
	 * Normalizes this vector
	 * 
	 * @return this vector for chaining
	 */
	public T normalize();

	/**
	 * @param v The other vector
	 * @return the dot product between this and the other vector
	 */
	public float dot(T v);

	/**
	 * Adds the given vector to this vector
	 * 
	 * @param v the vector
	 * @return this vector for chaining
	 */
	public T add(T v);

	/**
	 * Subtracts the given vector from this vector.
	 * 
	 * @param v the vector
	 * @return this vector for chaining
	 */
	public T sub(T v);

	public Object clone() throws CloneNotSupportedException;
}
