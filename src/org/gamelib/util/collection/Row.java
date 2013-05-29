/**
 * 
 */
package org.gamelib.util.collection;

/**
 * @author Axel
 */
public interface Row<E> {

	/** @return the number of elements. */
	public int size();

	/** Adds the element to the list. */
	public void add(E element);

	/** @return the element at the specified index. */
	public E get(int i);

	/** Removes the element from the list. */
	public void remove(E element);
	
	public static class Item {
		
	}
}
