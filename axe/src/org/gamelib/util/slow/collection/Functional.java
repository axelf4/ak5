/**
 * 
 */
package org.gamelib.util.slow.collection;

import java.util.Comparator;

/**
 * WIP
 * @author pwnedary
 */
public interface Functional<E> extends Iterable<E> {
	void sort(Comparator<E> comparator);

	void filter();
}
