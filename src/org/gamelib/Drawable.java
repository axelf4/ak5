/**
 * 
 */
package org.gamelib;

import org.gamelib.backend.Graphics;

/**
 * Abstraction for "something that can be drawn".
 * @author pwnedary
 */
public interface Drawable {

	/** Draws its content on the passed graphics context. */
	public void draw(Graphics g, float delta);
}
