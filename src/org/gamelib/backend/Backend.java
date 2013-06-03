/**
 * 
 */
package org.gamelib.backend;

import org.gamelib.Destroyable;
import org.gamelib.Drawable;
import org.gamelib.Game;
import org.gamelib.Input;
import org.gamelib.Resolution;
import org.gamelib.util.geom.Rectangle;

/**
 * The class responsible for collecting input and processing it.
 * @author pwnedary
 */
public interface Backend extends Destroyable {
	public void start(Game instance, Resolution resolution);

	/** @return a {@link Graphics} context to draw on. */
	public Graphics getGraphics();

	/** @return a {@link Graphics} context to draw on the specified image. */
	public Graphics getGraphics(Image image);

	/** @return the processor for input */
	public Input getInput();

	public void screenUpdate(Drawable callback, float delta);

	/** @return system time in milliseconds */
	public long getTime();

	/** temporary name */
	public boolean shouldClose();

	/** @param s the new window title */
	public void setTitle(String s);

	/** @return the size of the canvas drawn on. */
	public Rectangle getSize();

	/** @return factory for parsing files */
	public ResourceFactory getResourceFactory();
}
