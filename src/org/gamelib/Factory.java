/**
 * 
 */
package org.gamelib;

/**
 * An object that creates objects of a particular type.
 * @author Axel
 */
public interface Factory<T> {
	/** @return the created object */
	public T create();
}
