/**
 * 
 */
package org.gamelib;

/**
 * Abstraction for "something that can be updated".
 * @author pwnedary
 *
 */
public interface Updateable {

	/** Updates this object. */
	public void update(float delta);
}
