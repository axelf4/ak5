/**
 * 
 */
package org.gamelib.util.slow.reflection;

/**
 * @author pwnedary
 *
 */
public class Atomic<T> implements Access<T> {
	
	T object;

	/**
	 * 
	 */
	public Atomic(T object) {
		this.object = object;
	}

	/** {@inheritDoc} */
	@Override
	public T get() {
		return object;
	}

	/** {@inheritDoc} */
	@Override
	public void set(T value) {
		this.object = value;
	}

}
