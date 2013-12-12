/**
 * 
 */
package org.gamelib.util.slow.collection;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * @author pwnedary
 */
public final class Array<T> implements Iterable<T> {
	/** The internal array */
	private final T[] array;

	public <T2 extends T> Array(final T2[] array) {
		this.array = array;
	}

	/**
	 * Returns the length of this array.
	 * 
	 * @return The length of this array.
	 */
	public int length() {
		return array.length;
	}

	/**
	 * Returns the element at the given index if it exists, fails otherwise.
	 * 
	 * @param index The index at which to get the element to return
	 * @return The element at the given index if it exists, fails otherwise
	 */
	public T get(final int index) {
		return array[index];
	}

	@Override
	public Iterator<T> iterator() {
		return toCollection().iterator();
	}

	public Collection<T> toCollection() {
		return Collections.unmodifiableCollection(Arrays.asList(array));
	}

}
