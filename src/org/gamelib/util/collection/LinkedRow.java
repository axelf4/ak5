/**
 * 
 */
package org.gamelib.util.collection;

import java.lang.reflect.Proxy;

/**
 * @author Axel LinkedList
 */
public class LinkedRow<E> implements Row<E> {

	int size;
	E first;

	/**
	 * 
	 */
	public LinkedRow() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void add(E element) {
		if (!(element instanceof LinkedItem)) {
			// element = (E) Proxy.newProxyInstance(getClass().getClassLoader(), new Class<?>[] {}, new InvocationHandlerImpl<E>(element));
		}
		first = element;
	}

	@Override
	public E get(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(E element) {
		// TODO Auto-generated method stub

	}

	static class LinkedItem extends Item {
		LinkedItem next;

		public LinkedItem(LinkedItem next) {
			this.next = next;
		}
	}

}
