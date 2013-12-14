/**
 * 
 */
package org.gamelib.util.slow.collection;

import java.util.Iterator;

import org.gamelib.util.slow.collection.HashTable.Entry;

/**
 * @author Axel
 */
public class HashTable<K, V> implements Row<Entry<K, V>> {
	private Entry<K, V> table;
	private int size;

	@Override
	public Entry<K, V> get(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int add(Entry<K, V> e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void remove(int index) {
		// TODO Auto-generated method stub

	}

	@Override
	public int indexOf(Entry<K, V> e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<Entry<K, V>> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public static class Entry<K, V> {
		private K key;
		private V value;
	}
}
