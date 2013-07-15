/**
 * 
 */
package org.gamelib.util.geom;

/**
 * A general vector. Allows chaining operations by returning a reference to itself in all modification methods. See {@link Vector2} and {@link Vector3} for specific implementations.
 * @author pwnedary
 */
public interface Vector<T extends Vector<?>> extends Cloneable {

	/** @return The euclidian length (len) */
	float length();

	/** @return The squared euclidian length (len2) */
	float lengthSquared();

	/**
	 * @param vector The other vector
	 * @return The euclidian distance between this and the other vector
	 */
	public float dst(Vector3 v);

	/**
	 * Normalizes this vector
	 * @return This vector for chaining
	 */
	T normalize();

	/**
	 * @param v The other vector
	 * @return The dot product between this and the other vector
	 */
	float dot(T v);

	/**
	 * Adds the given vector to this vector
	 * @param v The vector
	 * @return This vector for chaining
	 */
	T add(T v);

	/**
	 * Substracts the given vector from this vector.
	 * @param v The vector
	 * @return This vector for chaining
	 */
	T sub(T v);

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public Object clone() throws CloneNotSupportedException;
}
