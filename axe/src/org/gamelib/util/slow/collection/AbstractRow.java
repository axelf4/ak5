/**
 * 
 */
package org.gamelib.util.slow.collection;

import java.util.Comparator;
import java.util.Iterator;

/**
 * @author pwnedary
 */
public abstract class AbstractRow<E> implements Row<E> {
	@Override
	public int indexOf(E e) {
		for (int i = 0; i < size(); i++)
			if (get(i).equals(e)) return i;
		return -1;
	}

	@Override
	public Iterator<E> iterator() {
		return new AbstractIterator<E>() {
			private int i;

			@Override
			protected E computeNext() {
				return i < size() ? get(i) : endOfData();
			}

			@Override
			public void remove() {
				AbstractRow.this.remove(i--);
			}
		};
	}

	@Override
	public void sort(Comparator<E> comparator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void filter() {
		throw new UnsupportedOperationException();
	}
}
