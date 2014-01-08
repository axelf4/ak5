/**
 * 
 */
package org.gamelib.util.slow.collection;

import java.util.Iterator;

/**
 * @author pwnedary
 */
public class ArrayRow<E> extends AbstractRow<E> {
	public E[] elements; // public here trades stability for speed
	private int size;

	public ArrayRow(E[] elements) {
		this.elements = elements;
		this.size = elements.length;
	}

	@SuppressWarnings("unchecked")
	public ArrayRow(int initialSize) {
		this((E[]) new Object[initialSize]);
		this.size = 0;
	}

	public ArrayRow() {
		this(16);
	}

	@Override
	public E get(int index) {
		return elements[index];
	}

	@Override
	public int add(E e) {
		if (size == elements.length) {
			@SuppressWarnings("unchecked")
			E[] tmp = (E[]) new Object[size << 1];
			System.arraycopy(elements, 0, tmp, 0, elements.length);
			elements = tmp;
		}
		elements[size] = e;
		return size++;
	}
	
	@Override
	public E remove(int index) {
		E value = elements[index];
		System.arraycopy(elements, index + 1, elements, index, size-- - index);
		return value;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator<E> iterator() {
		return new AbstractIterator<E>() {
			private int index;

			@Override
			protected E computeNext() {
				return index < size() ? get(index++) : endOfData();
			}

			@Override
			public void remove() {
				if (index == 0) throw new IllegalStateException();
				ArrayRow.this.remove(index-- - 1);
			}
		};
	}
}
