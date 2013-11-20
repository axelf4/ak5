/**
 * 
 */
package org.gamelib.util.slow.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author pwnedary
 */
public final class Array<T> implements Iterable<T> {

	/** the internal array */
	private final T[] array;

	public <T2 extends T> Array(final T2[] array) {
		this.array = array;
	}

	/**
	 * Returns the length of this array.
	 * @return The length of this array.
	 */
	public int length() {
		return array.length;
	}

	/**
	 * Returns the element at the given index if it exists, fails otherwise.
	 * @param index The index at which to get the element to return
	 * @return The element at the given index if it exists, fails otherwise
	 */
	public T get(final int index) {
		return array[index];
	}

	/** {@inheritDoc} */
	@Override
	public Iterator<T> iterator() {
		return toCollection().iterator();
	}

	public Collection<T> toCollection() {
		ArrayList<T> list = new ArrayList<T>(array.length) { // TODO make immutable
			private static final long serialVersionUID = -5031963875430820586L;
		};
		list.addAll(Arrays.asList(array));
		return list;
	}

}
