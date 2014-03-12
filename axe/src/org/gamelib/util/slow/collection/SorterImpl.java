/**
 * 
 */
package org.gamelib.util.slow.collection;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.gamelib.util.slow.reflection.proxy.Invocation;

/**
 * @author pwnedary
 */
public class SorterImpl<T> implements Sorter<T> {

	List<T> list;
	boolean reversed;
	Comparator<Object> comparator = DEFAULT_COMPARATOR;

	/**
	 * 
	 */
	public SorterImpl(List<T> list) {
		this.list = list;
	}

	/*
	 * (non-Javadoc)
	 * @see listLib.two.Sorter#reverse()
	 */
	@Override
	public Sorter<T> reverse() {
		this.reversed = !this.reversed;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see listLib.two.Sorter#use(java.util.Comparator)
	 */
	@Override
	public Sorter<T> use(Comparator<Object> comparator) {
		this.comparator = comparator;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see listLib.two.Sorter#sort(java.lang.Object)
	 */
	@Override
	public List<T> sort(Object argument) {
		final Invocation invoke = Invocation.from(argument);
		Comparator<T> comparator2 = new Comparator<T>() {
			@Override
			public int compare(Object o1, Object o2) {
				// return ((Comparable<T>) invoke.on(o1)).compareTo((T) invoke.on(o2));
				// return ((Comparable<T2>) method.invoke(o1, args)).compareTo(((T2) method.invoke(o2, args)));
				return comparator.compare(invoke.invoke(o1), invoke.invoke(o2));
				// return comparator.compare(method.invoke(o1, args), method.invoke(o2, args));
			}
		};
		Collections.sort(list, !reversed ? comparator2 : Collections.reverseOrder(comparator2));
		return list;
	}

}
