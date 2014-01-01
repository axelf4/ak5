package org.gamelib.util.geom;

/**
 * 
 */

/**
 * Encapsulates a <a href="http://en.wikipedia.org/wiki/Row-major_order#Column-major_order">column major</a> 4 by 4 matrix. Like the {@link Vector3} class it allows the chaining of methods by returning a reference to itself. For example:
 * 
 * <pre>
 * Matrix4 mat = new Matrix4().trn(position).mul(camera.combined);
 * </pre>
 * @author pwnedary
 */
public class Matrix4 {
	public static final int M00 = 0; // 0;
	public static final int M01 = 4; // 1;
	public static final int M02 = 8; // 2;
	public static final int M03 = 12; // 3;
	public static final int M10 = 1; // 4;
	public static final int M11 = 5; // 5;
	public static final int M12 = 9; // 6;
	public static final int M13 = 13; // 7;
	public static final int M20 = 2; // 8;
	public static final int M21 = 6; // 9;
	public static final int M22 = 10; // 10;
	public static final int M23 = 14; // 11;
	public static final int M30 = 3; // 12;
	public static final int M31 = 7; // 13;
	public static final int M32 = 11; // 14;
	public static final int M33 = 15; // 15;

	public final float val[] = new float[16];

	// private final float tmp[] = new float[16];

	/** Constructs an identity matrix */
	public Matrix4() {
		val[M00] = 1f;
		val[M11] = 1f;
		val[M22] = 1f;
		val[M33] = 1f;
	}

	/** @return the backing float array */
	public float[] getValues() {
		return val;
	}

	/**
	 * Sets the matrix to an identity matrix.
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Matrix4 idt() {
		val[M00] = 1;
		val[M01] = 0;
		val[M02] = 0;
		val[M03] = 0;
		val[M10] = 0;
		val[M11] = 1;
		val[M12] = 0;
		val[M13] = 0;
		val[M20] = 0;
		val[M21] = 0;
		val[M22] = 1;
		val[M23] = 0;
		val[M30] = 0;
		val[M31] = 0;
		val[M32] = 0;
		val[M33] = 1;
		return this;
	}

	/**
	 * Sets the matrix to an orthographic projection like glOrtho (http://www.opengl.org/sdk/docs/man/xhtml/glOrtho.xml) following the OpenGL equivalent
	 * @param left The left clipping plane
	 * @param right The right clipping plane
	 * @param bottom The bottom clipping plane
	 * @param top The top clipping plane
	 * @param near The near clipping plane
	 * @param far The far clipping plane
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Matrix4 setToOrtho(float left, float right, float bottom, float top, float near, float far) {
		this.idt();
		float x_orth = 2 / (right - left);
		float y_orth = 2 / (top - bottom);
		float z_orth = -2 / (far - near);

		float tx = -(right + left) / (right - left);
		float ty = -(top + bottom) / (top - bottom);
		float tz = -(far + near) / (far - near);

		val[M00] = x_orth;
		val[M10] = 0;
		val[M20] = 0;
		val[M30] = 0;
		val[M01] = 0;
		val[M11] = y_orth;
		val[M21] = 0;
		val[M31] = 0;
		val[M02] = 0;
		val[M12] = 0;
		val[M22] = z_orth;
		val[M32] = 0;
		val[M03] = tx;
		val[M13] = ty;
		val[M23] = tz;
		val[M33] = 1;

		return this;
	}
}
