/**
 * 
 */
package org.gamelib.util.math;

/** A 3-D vector. Allows chaining operations by returning a reference to itself in all modification methods.
 * 
 * @author pwnedary */
public class Vector3 implements Vector<Vector3> {
	/** The x component of this vector. */
	public float x;
	/** The y component of this vector. */
	public float y;
	/** The z component of this vector. */
	public float z;

	/** Creates a vector with the given components.
	 * 
	 * @param x The x-component
	 * @param y The y-component
	 * @param z The z-component */
	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/** Creates a vector initialized with 0, 0, 0. */
	public Vector3() {
		this(0f, 0f, 0f);
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
		return z;
	}

	@Override
	public float getW() {
		throw new UnsupportedOperationException();
	}

	public Vector3 set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	@Override
	public float len() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	@Override
	public float len2() {
		return x * x + y * y + z * z;
	}

	@Override
	public float dst(Vector3 v) {
		float a = v.x - x;
		float b = v.y - y;
		float c = v.z - z;
		return (float) Math.sqrt((a *= a) + (b *= b) + (c *= c));
	}

	@Override
	public Vector3 normalize() {
		if (len2() != 0) {
			float length = len();
			x /= length;
			y /= length;
			z /= length;
		}
		return this;
	}

	public Vector3 cross(Vector3 v) {
		return new Vector3(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
	}

	@Override
	public float dot(Vector3 v) {
		return this.x * v.x + this.y * v.y + this.z * v.z;
	}

	public Vector3 add(Vector3 v) {
		x += v.x;
		y += v.y;
		z += v.z;
		return this;
	}

	@Override
	public Vector3 sub(Vector3 v) {
		x -= v.x;
		y -= v.y;
		z -= v.z;
		return this;
	}

	@Override
	public String toString() {
		return x + "," + y + "," + z;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		result = prime * result + Float.floatToIntBits(z);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Vector3 other = (Vector3) obj;
		return Float.floatToIntBits(x) == Float.floatToIntBits(other.x) && Float.floatToIntBits(y) == Float.floatToIntBits(other.y) && Float.floatToIntBits(z) == Float.floatToIntBits(other.z);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return new Vector3(x, y, z);
	}

	/** Multiplies this vector by the given matrix dividing by w, assuming the fourth (w) component of the vector is 1.
	 * This is mostly used to project/unproject vectors via a perspective projection matrix.
	 * 
	 * @param matrix The matrix.
	 * @return This vector for chaining */
	public Vector3 prj(final Matrix4 matrix) {
		final float l_mat[] = matrix.data;
		final float l_w = 1f / (x * l_mat[Matrix4.M30] + y * l_mat[Matrix4.M31] + z * l_mat[Matrix4.M32] + l_mat[Matrix4.M33]);
		return this.set((x * l_mat[Matrix4.M00] + y * l_mat[Matrix4.M01] + z * l_mat[Matrix4.M02] + l_mat[Matrix4.M03]) * l_w, (x * l_mat[Matrix4.M10] + y * l_mat[Matrix4.M11] + z * l_mat[Matrix4.M12] + l_mat[Matrix4.M13]) * l_w, (x * l_mat[Matrix4.M20] + y * l_mat[Matrix4.M21] + z * l_mat[Matrix4.M22] + l_mat[Matrix4.M23]) * l_w);
	}
}
