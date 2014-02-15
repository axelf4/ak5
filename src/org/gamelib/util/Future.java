/**
 * 
 */
package org.gamelib.util;

/** @author pwnedary
 * @param <V> The result type returned by {@link #get()} */
public interface Future<V> {
	V get();
}