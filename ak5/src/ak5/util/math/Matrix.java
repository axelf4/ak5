/**
 * 
 */
package ak5.util.math;

/** Encapsulates a <a href="http://en.wikipedia.org/wiki/Row-major_order#Column-major_order">column major</a> matrix.
 * 
 * @author pwnedary */
public interface Matrix<T extends Matrix<T>> extends Cloneable {
	/** Returns the internal float array, backing this matrix.
	 * 
	 * @return the backing float array */
	float[] get();

	/** Sets the matrix to the given matrix. <code>A = B</code>
	 *
	 * @param matrix The matrix that is to be copied. (Is not modified)
	 * @return This matrix for the purpose of chaining methods together. */
	T set(T B);

	/** Sets the matrix to the given matrix as a float array.
	 *
	 * @param values The matrix, in float form, that is to be copied
	 * @return This matrix for the purpose of chaining methods together. */
	T set(float[] value);

	/** Returns this matrix's number of rows.
	 * 
	 * @return the number of rows */
	int numRows();

	/** Returns this matrix's number of columns.
	 * 
	 * @return the number of columns */
	int numColumns();

	/** Returns true if this matrix is a square.
	 * 
	 * @return if this matrix's a square */
	boolean isSquare();

	/** Sets the matrix to an identity matrix.
	 * 
	 * @return This matrix for the purpose of chaining methods together. */
	T idt();

	/** Postmultiplies this matrix with the given matrix, storing the result in this matrix. <code>A *= B</code>
	 *
	 * @param matrix The other matrix to multiply by.
	 * @return This matrix for the purpose of chaining operations together. */
	T mult(T matrix);
}
