/**
 * 
 */
package org.gamelib;

/**
 * An object that needs to be further defined after instantiation.
 * @author pwnedary
 */
public interface Createable {
	/** Defines data that cannot be appropriately handled on instantiation. */
	public void create();
}
