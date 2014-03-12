/**
 * 
 */
package org.gamelib.util.slow.collection;

import java.util.Iterator;

import org.gamelib.util.slow.collection.Node.BinaryNode;

/**
 * @author pwnedary
 */
public class LinkedRow<E> extends AbstractRow<E> {
	private BinaryNode<E> head;
	private BinaryNode<E> tail;
	private int size;

	@Override
	public E get(int index) {
		BinaryNode<E> node = head;
		for (; index > 0; index--)
			node = node.getNext();
		return node.get();
	}

	@Override
	public int add(final E e) {
		BinaryNode<E> node = new BinaryNode<>(e, null, null, tail);
		if (size() == 0) head = node;
		else tail.setNext(node);
		tail = node;
		return size++;
	}

	@Override
	public E remove(int index) {
		if (index >= size) throw new IndexOutOfBoundsException();
		BinaryNode<E> node = head;
		for (; index > 0; index--)
			node = node.getNext();
		if (node.getPrev() != null) node.getPrev().setNext(node.getNext());
		else head = node.getNext();
		return node.get();
	}

	@Override
	public int indexOf(E e) {
		int i = 0;
		for (Iterator<E> iterator = iterator(); iterator.hasNext(); i++)
			if (e.equals(iterator.next())) return i;
		return -1;
	};

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator<E> iterator() {
		return new AbstractIterator<E>() {
			private BinaryNode<E> node = head, prev = node;

			@Override
			protected E computeNext() {
				E e = node != null ? node.get() : endOfData();
				prev = node;
				if (node != null) node = node.getNext();
				return e;
			}

			@Override
			public void remove() {
				if (prev.getPrev() != null) prev.getPrev().setNext(prev.getNext());
				else head = prev.getNext();
			}
		};
	}
}
