/**
 * 
 */
package org.gamelib.util.slow.collection;

/**
 * @author Axel
 */
public abstract class AbstractTuple<E> implements Row<E> {
	@Override
	public boolean isEmpty() {
		return size() > 0;
	}

	@Override
	public int indexOf(E e) {
		for (int i = 0; i < size(); i++)
			if (get(i).equals(e)) return i;
		return 0;
	}
}
