/**
 * 
 */
package org.gamelib;

/**
 * Object that can notify listeners.
 * @author Axel
 */
public interface Listenable<T> {
	/** Sets the specified listener to be notified. */
	public void setListener(T listener);
}
