/**
 * 
 */
package org.gamelib.util.slow.collection;

import org.gamelib.util.slow.collection.Dict.Entry;

/**
 * An associative array made to solve the dictionary problem.
 * 
 * @author pwnedary
 */
public interface Dict<K, V> extends Row<Entry<K, V>> {
	/**
	 * Returns the value mapped to <code>key</code>.
	 * 
	 * @param key the key whose value is returned
	 * @return the value mapped to key
	 */
	V get(K key);

	/**
	 * Associates <code>value</code> with <code>key</code>.
	 * 
	 * @param key key which to associate with
	 * @param value value to be associated
	 * @return the index of the key-value pair in the dictionary
	 */
	int put(K key, V value);

	/**
	 * Removes the pair mapped to associated with <code>key</code> and returns it's value.
	 * 
	 * @param key the pair to remove's key
	 * @return the removed pair's value
	 */
	V remove(K key);

	/**
	 * Returns every pair's keys in a unspecified row of any implementation.
	 * 
	 * @return row of every pair's keys
	 * @see #values()
	 */
	Row<K> keys();

	/**
	 * Returns every pair's values in a unspecified row of any implementation.
	 * 
	 * @return row of every pair's values
	 * @see #keys()
	 */
	Row<V> values();

	/** A key-value pair in a {@link Dict} */
	interface Entry<K, V> {
		/**
		 * Returns the key associated with the value.
		 * 
		 * @return the key associated with the value
		 */
		K getKey();

		/**
		 * Returns the value mapped to the key
		 * 
		 * @return the value mapped to the key
		 */
		V getValue();

		/**
		 * Replaces the value to be the specified <code>value</code>.
		 * 
		 * @param value this entry's new value
		 */
		void setValue(V value);
	}
}
