/**
 * 
 */
package org.gamelib;

import org.gamelib.graphics.GL10;

/** Abstraction for "something that can be drawn".
 * 
 * @author pwnedary */
public interface Drawable {

	/** Draws its content on the passed OpenGL context. */
	public void draw(GL10 gl, float delta);
}
