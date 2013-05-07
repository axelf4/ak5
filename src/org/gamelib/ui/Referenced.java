/**
 * 
 */
package org.gamelib.ui;

/**
 * Object that in some way has a reference to what created it, etc.
 * @author pwnedary
 */
public interface Referenced<T> {
	/** @return the reference */
	public T getReference();

	/** @param reference the new reference */
	public void setReference(T reference);
}
