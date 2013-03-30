/**
 * 
 */
package org.gamelib.util;

/**
 * Interface used to select items within an iterator against a predicate.
 * 
 * @author Axel
 */
public interface Predicate<T> {
	boolean evaluate(T arg0);
}
