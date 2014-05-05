/**
 * 
 */
package ak5.util.math;

/** @author pwnedary */
public class Vector4 implements Vector<Vector4> {
	/** The x component of this vector. */
	public float x;
	/** The y component of this vector. */
	public float y;
	/** The z component of this vector. */
	public float z;
	/** The w component of this vector. */
	public float w;

	public Vector4() {}

	public Vector4(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
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
		return w;
	}

	@Override
	public float len() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

	@Override
	public float len2() {
		return x * x + y * y + z * z + w * w;
	}

	@Override
	public float dst(Vector3 v) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Vector4 normalize() {
		if (len2() != 0) {
			float length = len();
			w /= length;
			x /= length;
			y /= length;
			z /= length;
		}
		return this;
	}

	@Override
	public float dot(Vector4 v) {
		return this.x * v.x + this.y * v.y + this.z * v.z + this.w * v.w;
	}

	@Override
	public Vector4 add(Vector4 v) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector4 sub(Vector4 v) {
		// TODO Auto-generated method stub
		return null;
	}

	public Vector4 idt() {
		x = 0;
		y = 0;
		z = 0;
		w = 1;
		return this;
	}
}
