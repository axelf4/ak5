package org.gamelib.util.geom;

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

	/** Constructs an identity matrix */
	public Matrix4() {
		data[M00] = 1f;
		data[M11] = 1f;
		data[M22] = 1f;
		data[M33] = 1f;
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
	public int getNumRows() {
		return 4;
	}

	@Override
	public int getNumColumns() {
		return 4;
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

	@Override
	public Matrix4 mul(Matrix4 matrix) {
		for (int i = 0, aIndexStart = 0, cIndex = 0; i < getNumRows(); i++) {
			for (int j = 0; j < matrix.getNumColumns(); j++) {
				float total = 0;

				int indexA = aIndexStart;
				int indexB = j;
				int end = indexA + matrix.getNumRows();
				while (indexA < end) {
					total += data[indexA++] * matrix.data[indexB];
					indexB += matrix.getNumColumns();
				}

				tmp[cIndex++] = total;
			}
			aIndexStart += getNumColumns();
		}
		return set(tmp);
	}

	/** Sets the matrix to a projection matrix with a near- and far plane, a field of view in degrees and an aspect
	 * ratio.
	 * 
	 * @param near The near plane
	 * @param far The far plane
	 * @param fov The field of view in degrees
	 * @param aspectRatio The "width over height" aspect ratio
	 * @return This matrix for the purpose of chaining methods together. */
	public Matrix4 setToProjection(float near, float far, float fov, float aspectRatio) {
		idt();
		float l_fd = (float) (1.0 / Math.tan((fov * (Math.PI / 180)) / 2.0));
		float l_a1 = (far + near) / (near - far);
		float l_a2 = (2 * far * near) / (near - far);
		data[M00] = l_fd / aspectRatio;
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

	/** Sets the matrix to an orthographic projection like glOrtho (http://www.opengl.org/sdk/docs/man/xhtml/glOrtho.xml)
	 * following the OpenGL equivalent
	 * 
	 * @param left The left clipping plane
	 * @param right The right clipping plane
	 * @param bottom The bottom clipping plane
	 * @param top The top clipping plane
	 * @param near The near clipping plane
	 * @param far The far clipping plane
	 * @return This matrix for the purpose of chaining methods together. */
	public Matrix4 setToOrtho(float left, float right, float bottom, float top, float near, float far) {
		this.idt();
		float x_orth = 2 / (right - left);
		float y_orth = 2 / (top - bottom);
		float z_orth = -2 / (far - near);

		float tx = -(right + left) / (right - left);
		float ty = -(top + bottom) / (top - bottom);
		float tz = -(far + near) / (far - near);

		data[M00] = x_orth;
		data[M10] = 0;
		data[M20] = 0;
		data[M30] = 0;
		data[M01] = 0;
		data[M11] = y_orth;
		data[M21] = 0;
		data[M31] = 0;
		data[M02] = 0;
		data[M12] = 0;
		data[M22] = z_orth;
		data[M32] = 0;
		data[M03] = tx;
		data[M13] = ty;
		data[M23] = tz;
		data[M33] = 1;

		return this;
	}
}
