/**
 * 
 */
package org.gamelib.util.slow.collection;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Provides an interface to a low-level <code>array</code>.
 * 
 * @author pwnedary
 */
public final class Array<E> implements Iterable<E> {
	/** The internal array. */
	private final Object[] array;

	public Array(final Object[] array) {
		this.array = array;
	}

	/**
	 * Returns the element at the given index if it exists, fails otherwise.
	 * 
	 * @param index The index at which to get the element to return
	 * @return The element at the given index if it exists, fails otherwise
	 * @throws IndexOutOfBoundsException If the given index does not exist
	 */
	@SuppressWarnings("unchecked")
	public E get(final int index) {
		return (E) array[index];
	}

	/**
	 * Sets the element at the given index to the given value.
	 * 
	 * @param index The index at which to set the given value.
	 * @param e The value to set at the given index.
	 * @throws IndexOutOfBoundsException If the given index does not exist
	 */
	public void set(final int index, final E e) {
		array[index] = e;
	}

	/**
	 * Returns the length of this array.
	 * 
	 * @return The length of this array.
	 */
	public int length() {
		return array.length;
	}

	public Collection<E> toCollection() {
		// return Collections.unmodifiableCollection(Arrays.asList((E[]) array));
		return new AbstractCollection<E>() {
			@Override
			public Iterator<E> iterator() {
				return Array.this.iterator();
			}

			@Override
			public int size() {
				return array.length;
			}
		};
	}

	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			private int i;

			@Override
			public boolean hasNext() {
				return i < array.length;
			}

			@SuppressWarnings("unchecked")
			@Override
			public E next() {
				if (i >= array.length) throw new NoSuchElementException();
				return (E) array[i++];
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
}
