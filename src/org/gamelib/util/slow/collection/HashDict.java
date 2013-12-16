/**
 * 
 */
package org.gamelib.util.slow.collection;

import org.gamelib.util.slow.collection.Dict.Entry;

/**
 * @author pwnedary
 */
public class HashDict<K, V> extends AbstractRow<Entry<K, V>> implements Dict<K, V> {
	private Entry<K, V>[] dictionary;
	private int size;

	@SuppressWarnings("unchecked")
	public HashDict(Entry<?, ?>[] dictionary) {
		this.dictionary = (Entry<K, V>[]) dictionary;
		this.size = dictionary.length;
	}

	@SuppressWarnings("unchecked")
	public HashDict(int initialSize) {
		dictionary = (Entry<K, V>[]) new Entry<?, ?>[initialSize];
	}

	@Override
	public Entry<K, V> get(int index) {
		return dictionary[index];
	}

	@Override
	public int add(Entry<K, V> e) {
		if (size() >= dictionary.length) {
			@SuppressWarnings("unchecked")
			Entry<K, V>[] tmp = (Entry<K, V>[]) new Object[size() << 1];
			System.arraycopy(dictionary, 0, tmp, 0, dictionary.length);
			dictionary = tmp;
		}
		dictionary[size] = e;
		return size++;
	}

	@Override
	public Entry<K, V> remove(int index) {
		Entry<K, V> value = dictionary[index];
		dictionary[index] = dictionary[--size];
		return value;
	}

	@Override
	public int size() {
		return size;
	}

	private static final class HashEntry<K, V> implements Entry<K, V> {
		private final K key;
		private V value;
		private final int hashCode;

		public HashEntry(K key, V value) {
			this.key = key;
			this.value = value;
			this.hashCode = key.hashCode() ^ value.hashCode();
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public void setValue(V value) {
			this.value = value;
		}

		@Override
		public int hashCode() {
			return hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			return obj instanceof HashEntry<?, ?> && hashCode() == obj.hashCode();
		}
	}

	@Override
	public int put(K key, V value) {
		return add(new HashEntry<K, V>(key, value));
	}

	@Override
	public V get(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V remove(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Row<K> keys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Row<V> values() {
		// TODO Auto-generated method stub
		return null;
	}
}
