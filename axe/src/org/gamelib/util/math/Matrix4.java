package org.gamelib.util.math;

/**
 * 
 */

/** Encapsulates a <a href="http://en.wikipedia.org/wiki/Row-major_order#Column-major_order">column major</a> 4 by 4
 * matrix. Like the {@link Vector3} class it allows the chaining of methods by returning a reference to itself. For
 * example:
 * 
 * <pre>
 * Matrix4 mat = new Matrix4().trn(position).mul(camera.combined);
 * </pre>
 * 
 * @author pwnedary */
public class Matrix4 implements Matrix<Matrix4> {
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

	public final float[] data = new float[16];

	private final float tmp[] = new float[16];

	/** Constructs an identity matrix. */
	public Matrix4() {
		data[M00] = 1f;
		data[M11] = 1f;
		data[M22] = 1f;
		data[M33] = 1f;
	}
	
	public Matrix4(Quaternion q) {
		// First Column
	    data[0] = 1 - 2 * (q.y * q.y + q.z * q.z);
	    data[1] = 2 * (q.x * q.y + q.z * q.w);
	    data[2] = 2 * (q.x * q.z - q.y * q.w);
	    data[3] = 0;

	    // Second Column
	    data[4] = 2 * (q.x * q.y - q.z * q.w);
	    data[5] = 1 - 2 * (q.x * q.x + q.z * q.z);
	    data[6] = 2 * (q.z * q.y + q.x * q.w);
	    data[7] = 0;

	    // Third Column
	    data[8] = 2 * (q.x * q.z + q.y * q.w);
	    data[9] = 2 * (q.y * q.z - q.x * q.w);
	    data[10] = 1 - 2 * (q.x * q.x + q.y * q.y);
	    data[11] = 0;

	    // Fourth Column
	    data[12] = 0;
	    data[13] = 0;
	    data[14] = 0;
	    data[15] = 1;
	}

	@Override
	public float[] get() {
		return data;
	}

	@Override
	public Matrix4 set(Matrix4 matrix) {
		return this.set(matrix.data);
	}

	@Override
	public Matrix4 set(float[] value) {
		System.arraycopy(value, 0, data, 0, data.length);
		return this;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new Matrix4().set(this);
	}

	@Override
	public int numRows() {
		return 4;
	}

	@Override
	public int numColumns() {
		return 4;
	}

	@Override
	public boolean isSquare() {
		return true;
	}

	@Override
	public Matrix4 idt() {
		data[M00] = 1;
		data[M01] = 0;
		data[M02] = 0;
		data[M03] = 0;
		data[M10] = 0;
		data[M11] = 1;
		data[M12] = 0;
		data[M13] = 0;
		data[M20] = 0;
		data[M21] = 0;
		data[M22] = 1;
		data[M23] = 0;
		data[M30] = 0;
		data[M31] = 0;
		data[M32] = 0;
		data[M33] = 1;
		return this;
	}
	
	/** Inverts the matrix. Stores the result in this matrix.
	 * 
	 * @return This matrix for the purpose of chaining methods together.
	 * @throws RuntimeException if the matrix is singular (not invertible) */
	public Matrix4 inv () {
		float l_det = data[M30] * data[M21] * data[M12] * data[M03] - data[M20] * data[M31] * data[M12] * data[M03] - data[M30] * data[M11]
			* data[M22] * data[M03] + data[M10] * data[M31] * data[M22] * data[M03] + data[M20] * data[M11] * data[M32] * data[M03] - data[M10]
			* data[M21] * data[M32] * data[M03] - data[M30] * data[M21] * data[M02] * data[M13] + data[M20] * data[M31] * data[M02] * data[M13]
			+ data[M30] * data[M01] * data[M22] * data[M13] - data[M00] * data[M31] * data[M22] * data[M13] - data[M20] * data[M01] * data[M32]
			* data[M13] + data[M00] * data[M21] * data[M32] * data[M13] + data[M30] * data[M11] * data[M02] * data[M23] - data[M10] * data[M31]
			* data[M02] * data[M23] - data[M30] * data[M01] * data[M12] * data[M23] + data[M00] * data[M31] * data[M12] * data[M23] + data[M10]
			* data[M01] * data[M32] * data[M23] - data[M00] * data[M11] * data[M32] * data[M23] - data[M20] * data[M11] * data[M02] * data[M33]
			+ data[M10] * data[M21] * data[M02] * data[M33] + data[M20] * data[M01] * data[M12] * data[M33] - data[M00] * data[M21] * data[M12]
			* data[M33] - data[M10] * data[M01] * data[M22] * data[M33] + data[M00] * data[M11] * data[M22] * data[M33];
		if (l_det == 0f) throw new RuntimeException("non-invertible matrix");
		float inv_det = 1.0f / l_det;
		tmp[M00] = data[M12] * data[M23] * data[M31] - data[M13] * data[M22] * data[M31] + data[M13] * data[M21] * data[M32] - data[M11]
			* data[M23] * data[M32] - data[M12] * data[M21] * data[M33] + data[M11] * data[M22] * data[M33];
		tmp[M01] = data[M03] * data[M22] * data[M31] - data[M02] * data[M23] * data[M31] - data[M03] * data[M21] * data[M32] + data[M01]
			* data[M23] * data[M32] + data[M02] * data[M21] * data[M33] - data[M01] * data[M22] * data[M33];
		tmp[M02] = data[M02] * data[M13] * data[M31] - data[M03] * data[M12] * data[M31] + data[M03] * data[M11] * data[M32] - data[M01]
			* data[M13] * data[M32] - data[M02] * data[M11] * data[M33] + data[M01] * data[M12] * data[M33];
		tmp[M03] = data[M03] * data[M12] * data[M21] - data[M02] * data[M13] * data[M21] - data[M03] * data[M11] * data[M22] + data[M01]
			* data[M13] * data[M22] + data[M02] * data[M11] * data[M23] - data[M01] * data[M12] * data[M23];
		tmp[M10] = data[M13] * data[M22] * data[M30] - data[M12] * data[M23] * data[M30] - data[M13] * data[M20] * data[M32] + data[M10]
			* data[M23] * data[M32] + data[M12] * data[M20] * data[M33] - data[M10] * data[M22] * data[M33];
		tmp[M11] = data[M02] * data[M23] * data[M30] - data[M03] * data[M22] * data[M30] + data[M03] * data[M20] * data[M32] - data[M00]
			* data[M23] * data[M32] - data[M02] * data[M20] * data[M33] + data[M00] * data[M22] * data[M33];
		tmp[M12] = data[M03] * data[M12] * data[M30] - data[M02] * data[M13] * data[M30] - data[M03] * data[M10] * data[M32] + data[M00]
			* data[M13] * data[M32] + data[M02] * data[M10] * data[M33] - data[M00] * data[M12] * data[M33];
		tmp[M13] = data[M02] * data[M13] * data[M20] - data[M03] * data[M12] * data[M20] + data[M03] * data[M10] * data[M22] - data[M00]
			* data[M13] * data[M22] - data[M02] * data[M10] * data[M23] + data[M00] * data[M12] * data[M23];
		tmp[M20] = data[M11] * data[M23] * data[M30] - data[M13] * data[M21] * data[M30] + data[M13] * data[M20] * data[M31] - data[M10]
			* data[M23] * data[M31] - data[M11] * data[M20] * data[M33] + data[M10] * data[M21] * data[M33];
		tmp[M21] = data[M03] * data[M21] * data[M30] - data[M01] * data[M23] * data[M30] - data[M03] * data[M20] * data[M31] + data[M00]
			* data[M23] * data[M31] + data[M01] * data[M20] * data[M33] - data[M00] * data[M21] * data[M33];
		tmp[M22] = data[M01] * data[M13] * data[M30] - data[M03] * data[M11] * data[M30] + data[M03] * data[M10] * data[M31] - data[M00]
			* data[M13] * data[M31] - data[M01] * data[M10] * data[M33] + data[M00] * data[M11] * data[M33];
		tmp[M23] = data[M03] * data[M11] * data[M20] - data[M01] * data[M13] * data[M20] - data[M03] * data[M10] * data[M21] + data[M00]
			* data[M13] * data[M21] + data[M01] * data[M10] * data[M23] - data[M00] * data[M11] * data[M23];
		tmp[M30] = data[M12] * data[M21] * data[M30] - data[M11] * data[M22] * data[M30] - data[M12] * data[M20] * data[M31] + data[M10]
			* data[M22] * data[M31] + data[M11] * data[M20] * data[M32] - data[M10] * data[M21] * data[M32];
		tmp[M31] = data[M01] * data[M22] * data[M30] - data[M02] * data[M21] * data[M30] + data[M02] * data[M20] * data[M31] - data[M00]
			* data[M22] * data[M31] - data[M01] * data[M20] * data[M32] + data[M00] * data[M21] * data[M32];
		tmp[M32] = data[M02] * data[M11] * data[M30] - data[M01] * data[M12] * data[M30] - data[M02] * data[M10] * data[M31] + data[M00]
			* data[M12] * data[M31] + data[M01] * data[M10] * data[M32] - data[M00] * data[M11] * data[M32];
		tmp[M33] = data[M01] * data[M12] * data[M20] - data[M02] * data[M11] * data[M20] + data[M02] * data[M10] * data[M21] - data[M00]
			* data[M12] * data[M21] - data[M01] * data[M10] * data[M22] + data[M00] * data[M11] * data[M22];
		data[M00] = tmp[M00] * inv_det;
		data[M01] = tmp[M01] * inv_det;
		data[M02] = tmp[M02] * inv_det;
		data[M03] = tmp[M03] * inv_det;
		data[M10] = tmp[M10] * inv_det;
		data[M11] = tmp[M11] * inv_det;
		data[M12] = tmp[M12] * inv_det;
		data[M13] = tmp[M13] * inv_det;
		data[M20] = tmp[M20] * inv_det;
		data[M21] = tmp[M21] * inv_det;
		data[M22] = tmp[M22] * inv_det;
		data[M23] = tmp[M23] * inv_det;
		data[M30] = tmp[M30] * inv_det;
		data[M31] = tmp[M31] * inv_det;
		data[M32] = tmp[M32] * inv_det;
		data[M33] = tmp[M33] * inv_det;
		return this;
	}

	@Override
	public Matrix4 mult(Matrix4 m) {
		tmp[M00] = data[M00] * m.data[M00] + data[M01] * m.data[M10] + data[M02] * m.data[M20] + data[M03] * m.data[M30];
		tmp[M01] = data[M00] * m.data[M01] + data[M01] * m.data[M11] + data[M02] * m.data[M21] + data[M03] * m.data[M31];
		tmp[M02] = data[M00] * m.data[M02] + data[M01] * m.data[M12] + data[M02] * m.data[M22] + data[M03] * m.data[M32];
		tmp[M03] = data[M00] * m.data[M03] + data[M01] * m.data[M13] + data[M02] * m.data[M23] + data[M03] * m.data[M33];
		tmp[M10] = data[M10] * m.data[M00] + data[M11] * m.data[M10] + data[M12] * m.data[M20] + data[M13] * m.data[M30];
		tmp[M11] = data[M10] * m.data[M01] + data[M11] * m.data[M11] + data[M12] * m.data[M21] + data[M13] * m.data[M31];
		tmp[M12] = data[M10] * m.data[M02] + data[M11] * m.data[M12] + data[M12] * m.data[M22] + data[M13] * m.data[M32];
		tmp[M13] = data[M10] * m.data[M03] + data[M11] * m.data[M13] + data[M12] * m.data[M23] + data[M13] * m.data[M33];
		tmp[M20] = data[M20] * m.data[M00] + data[M21] * m.data[M10] + data[M22] * m.data[M20] + data[M23] * m.data[M30];
		tmp[M21] = data[M20] * m.data[M01] + data[M21] * m.data[M11] + data[M22] * m.data[M21] + data[M23] * m.data[M31];
		tmp[M22] = data[M20] * m.data[M02] + data[M21] * m.data[M12] + data[M22] * m.data[M22] + data[M23] * m.data[M32];
		tmp[M23] = data[M20] * m.data[M03] + data[M21] * m.data[M13] + data[M22] * m.data[M23] + data[M23] * m.data[M33];
		tmp[M30] = data[M30] * m.data[M00] + data[M31] * m.data[M10] + data[M32] * m.data[M20] + data[M33] * m.data[M30];
		tmp[M31] = data[M30] * m.data[M01] + data[M31] * m.data[M11] + data[M32] * m.data[M21] + data[M33] * m.data[M31];
		tmp[M32] = data[M30] * m.data[M02] + data[M31] * m.data[M12] + data[M32] * m.data[M22] + data[M33] * m.data[M32];
		tmp[M33] = data[M30] * m.data[M03] + data[M31] * m.data[M13] + data[M32] * m.data[M23] + data[M33] * m.data[M33];
		return set(tmp);
	}

	/** Sets the matrix to an orthographic projection like the OpenGL equivalent glOrtho
	 * (http://www.opengl.org/sdk/docs/man2/xhtml/glOrtho.xml).
	 * 
	 * @param left The left clipping plane
	 * @param right The right clipping plane
	 * @param bottom The bottom clipping plane
	 * @param top The top clipping plane
	 * @param near The near clipping plane
	 * @param far The far clipping plane
	 * @return This matrix for the purpose of chaining methods together. */
	public Matrix4 ortho(float left, float right, float bottom, float top, float near, float far) {
		data[M00] = 2 / (right - left);
		data[M10] = 0;
		data[M20] = 0;
		data[M30] = 0;
		data[M01] = 0;
		data[M11] = 2 / (top - bottom);
		data[M21] = 0;
		data[M31] = 0;
		data[M02] = 0;
		data[M12] = 0;
		data[M22] = -2 / (far - near);
		data[M32] = 0;
		data[M03] = -(right + left) / (right - left);
		data[M13] = -(top + bottom) / (top - bottom);
		data[M23] = -(far + near) / (far - near);
		data[M33] = 1;
		return this;
	}

	/** Sets the matrix to a projection matrix with a near- and far plane, a field of view in degrees and an aspect ratio
	 * like the GLU equivalent (http://www.opengl.org/sdk/docs/man2/xhtml/gluPerspective.xml).
	 * 
	 * @param near The near plane
	 * @param far The far plane
	 * @param fov The field of view in degrees
	 * @param aspectRatio The "width over height" aspect ratio
	 * @return This matrix for the purpose of chaining methods together. */
	public Matrix4 perspective(float fov, float aspect, float near, float far) {
		float l_fd = (float) (1.0 / Math.tan((fov * (Math.PI / 180)) / 2.0));
		float l_a1 = (far + near) / (near - far);
		float l_a2 = (2 * far * near) / (near - far);
		data[M00] = l_fd / aspect;
		data[M10] = 0;
		data[M20] = 0;
		data[M30] = 0;
		data[M01] = 0;
		data[M11] = l_fd;
		data[M21] = 0;
		data[M31] = 0;
		data[M02] = 0;
		data[M12] = 0;
		data[M22] = l_a1;
		data[M32] = -1;
		data[M03] = 0;
		data[M13] = 0;
		data[M23] = l_a2;
		data[M33] = 0;
		return this;
	}

	/** Adds a translational component to the matrix in the 4th column. The other columns are untouched.
	 * 
	 * @param vector The translation vector to add to the current matrix. (This vector is not modified)
	 * @return This matrix for the purpose of chaining methods together. */
	public Matrix4 trn(Vector3 vector) {
		data[M03] += vector.x;
		data[M13] += vector.y;
		data[M23] += vector.z;
		return this;
	}

	/** Adds a translational component to the matrix in the 4th column. The other columns are untouched.
	 * 
	 * @param x The x-component of the translation vector.
	 * @param y The y-component of the translation vector.
	 * @param z The z-component of the translation vector.
	 * @return This matrix for the purpose of chaining methods together. */
	public Matrix4 trn(float x, float y, float z) {
		data[M03] += x;
		data[M13] += y;
		data[M23] += z;
		return this;
	}
}
