/**
 * 
 */
package org.gamelib.util.geom;

/**
 * Encapsulates a general vector. Allows chaining operations by returning a
 * reference to itself in all modification methods. See {@link Vector2} and
 * {@link Vector3} for specific implementations.
 * 
 * @author pwnedary
 * 
 */
public interface Vector<T extends Vector<?>> extends Cloneable {

	/** @return The euclidian length */
	float length();

	/** @return The squared euclidian length */
	float lengthSquared();

	/**
	 * Normalizes this vector
	 * 
	 * @return This vector for chaining
	 */
	T normalize();

	/**
	 * @param v The other vector
	 * @return The dot product between this and the other vector
	 */
	float dot(T v);

	/* (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone() */
	public Object clone() throws CloneNotSupportedException;
}
