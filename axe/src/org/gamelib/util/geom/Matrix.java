/**
 * 
 */
package org.gamelib.util.geom;

/** Encapsulates a <a href="http://en.wikipedia.org/wiki/Row-major_order#Column-major_order">column major</a> matrix.
 * 
 * @author pwnedary */
public interface Matrix<T extends Matrix<T>> {
	/** Returns the internal float array, backing this matrix.
	 * 
	 * @return the backing float array */
	float[] get();

	/** Sets the matrix to the given matrix.
	 *
	 * @param matrix The matrix that is to be copied. (Is not modified)
	 * @return This matrix for the purpose of chaining methods together. */
	T set(T matrix);

	/** Sets the matrix to the given matrix as a float array.
	 *
	 * @param values The matrix, in float form, that is to be copied
	 * @return This matrix for the purpose of chaining methods together. */
	T set(float[] value);

	/** Sets the matrix to an identity matrix.
	 * 
	 * @return This matrix for the purpose of chaining methods together. */
	T idt();

	/** Returns this matrix's number of rows.
	 * 
	 * @return the number of rows */
	int getNumRows();

	/** Returns this matrix's number of columns.
	 * 
	 * @return the number of columns */
	int getNumColumns();

	/** Postmultiplies this matrix with the given matrix, storing the result in this matrix.
	 *
	 * @param matrix The other matrix to multiply by.
	 * @return This matrix for the purpose of chaining operations together. */
	T mul(T matrix);
}
