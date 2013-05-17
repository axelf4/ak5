/**
 * 
 */
package org.gamelib;

/**
 * Objects that needs to destroy it's contents.
 * @author pwnedary
 * @see javax.security.auth.Destroyable
 */
public interface Destroyable {
	/** Destroy this object. */
	public void destroy();
}
