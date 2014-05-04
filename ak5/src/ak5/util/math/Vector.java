/**
 * 
 */
package ak5.util.math;

/** A general float coordinate vector. Allows chaining operations by returning a reference to itself in all modification
 * methods.
 * 
 * @see Vector2 2-D vector
 * @see Vector3 3-D vector
 * @author pwnedary */
public interface Vector<T extends Vector<T>> extends Cloneable {
	/** Returns this vector's X component.
	 * 
	 * @return the x component
	 * @throws UnsupportedOperationException if no X component exists */
	float getX();

	/** Returns this vector's Y component.
	 * 
	 * @return the y component
	 * @throws UnsupportedOperationException if no Y component exists */
	float getY();

	/** Returns this vector's Z component.
	 * 
	 * @return the z component
	 * @throws UnsupportedOperationException if no Z component exists */
	float getZ();

	/** Returns this vector's W component.
	 * 
	 * @return the w component
	 * @throws UnsupportedOperationException if no W component exists */
	float getW();

	/** @return the euclidian length */
	float len();

	/** @return the squared euclidian length */
	float len2();

	/** @param vector the other vector
	 * @return the euclidian distance between this and the other vector */
	float dst(Vector3 v);

	/** Normalizes/Unitizes this vector.
	 * 
	 * @return this vector for chaining */
	T normalize();

	/** Returns the dot/scalar product (the sum of the products of the corresponding vectors' entries) between this and
	 * the other vector.
	 * 
	 * @param v The other vector
	 * @return the dot product */
	float dot(T v);

	/** Adds the given vector to this vector
	 * 
	 * @param v The other vector
	 * @return this vector for chaining */
	T add(T v);

	/** Subtracts the given vector from this vector.
	 * 
	 * @param v The other vector
	 * @return this vector for chaining */
	T sub(T v);
}
