/**
 * 
 */
package org.gamelib;

/** Abstraction for "something that can be updated".
 * 
 * @author pwnedary */
public interface Updatable {
	/** Updates this object. */
	public void update(float delta);
}
