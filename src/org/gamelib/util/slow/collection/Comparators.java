/**
 * 
 */
package org.gamelib.util.slow.collection;

import java.util.Comparator;

/**
 * @author Axel
 */
public final class Comparators {
	public static final Comparator<Object> DEFAULT_COMPARATOR = new Comparator<Object>() {
		@SuppressWarnings("unchecked")
		@Override
		public int compare(Object o1, Object o2) {
			return ((Comparable<Object>) o1).compareTo((Comparable<?>) o2);
		}
	};
	public static final Comparator<Object> TO_STRING_COMPARATOR = new Comparator<Object>() { // LITERAL_COMPARATOR
		@Override
		public int compare(Object o1, Object o2) {
			return o1.toString().compareTo(o2.toString());
		}
	};

	private Comparators() {}
}
