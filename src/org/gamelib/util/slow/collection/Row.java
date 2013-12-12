/**
 * 
 */
package org.gamelib.util.slow.collection;


/**
 * @author pwnedary
 */
public interface Row<E> extends Iterable<E> {
	E get(int index);

	void add(final E e);
	
	void remove(int index);

	int size();

	boolean isEmpty();
}
