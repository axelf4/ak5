/**
 * 
 */
package org.gamelib.util.slow.collection;

import java.util.Comparator;
import java.util.List;

/**
 * @author pwnedary
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public interface Sorter<T> {

	public static final Comparator<Object> DEFAULT_COMPARATOR = new Comparator<Object>() {
		@Override
		public int compare(Object o1, Object o2) {
			return ((Comparable) o1).compareTo((Comparable) o2);
		}
	};
	public static final Comparator<Object> TO_STRING_COMPARATOR = new Comparator<Object>() { // LITERAL_COMPARATOR
		@Override
		public int compare(Object o1, Object o2) {
			return o1.toString().compareTo(o2.toString());
		}
	};

	/** Reverses the sorting order. */
	public Sorter<T> reverse();

	/** Sets the {@link Comparator} to use for sorting. */
	public Sorter<T> use(Comparator<Object> comparator);

	/** Sorts the {@link List} on the returned object's next called method's return value. */
	public List<T> sort(Object argument);

	// public List<T> sort(Class clazz);

}