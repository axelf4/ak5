/**
 * 
 */
package org.gamelib.util.slow.collection;

/**
 * A matrix consisting of a single row of elements. Elements' indexes are preferred to be consistent but aren't required.
 * 
 * @author pwnedary
 */
public interface Row<E> extends Iterable<E> {
	/**
	 * Returns the element at <code>index</code> in this {@link Row}.
	 * 
	 * @param index the element's index in this row
	 * @return the element at index
	 */
	E get(int index);

	/**
	 * Adds <code>e</code> to (preferably, but not guaranteed) the next available position in this row and returns it's index or <tt>-1</tt> if it wasn't successful.
	 * 
	 * @param e the element added to this row
	 * @return the element's future index in this row or <tt>-1</tt>
	 */
	int add(final E e);

	/**
	 * Removes the element at <code>index</code>.
	 * 
	 * @param index the element's index of which to remove
	 */
	void remove(int index);

	/**
	 * Returns <code>e</code>'s first occurence's index in this row or <tt>-1</tt> if it didn't exist.
	 * 
	 * @param e the element, of which index to search for
	 * @return e's first occurence's index
	 */
	int indexOf(final E e);

	/**
	 * Returns the number of elements contained.
	 * 
	 * @return the number of elements contained
	 */
	int size();

	/**
	 * Returns <tt>true</tt> if the size is zero.
	 * 
	 * @return <tt>true</tt> if the size is zero.
	 */
	boolean isEmpty();
}
