/**
 * 
 */
package org.gamelib;

import org.gamelib.backend.Graphics;
import org.gamelib.graphics.GL10;

/**
 * Abstraction for "something that can be drawn".
 * @author pwnedary
 */
public interface Drawable {
	
	public void draw(GL10 gl, float delta);

	/** Draws its content on the passed graphics context. */
	public void draw(Graphics g, float delta);
}
