/**
 * 
 */
package org.gamelib.backend;

import org.gamelib.Destroyable;
import org.gamelib.Drawable;
import org.gamelib.Game;
import org.gamelib.Input;
import org.gamelib.util.geom.Rectangle;

/**
 * The class responsible for collecting input and processing it.
 * @author pwnedary
 */
public interface Backend extends Destroyable {
	void start(Game game);

	void stop();

	void screenUpdate(Drawable callback, float delta);

	/** @return system time in milliseconds */
	long getTime();

	/** temporary name */
	boolean shouldClose();

	/** @param s the new window title */
	void setTitle(String s);

	/** @return the size of the canvas drawn on. */
	Rectangle getSize();

	/** @return the width of the canvas */
	int getWidth();

	/** @return the width of the canvas */
	int getHeight();

	/** @return a {@link Graphics} context to draw on. */
	Graphics getGraphics();

	/** @return a {@link Graphics} context to draw on the specified image. */
	Graphics getGraphics(Image image);

	/** @return the processor for input */
	Input getInput();

	/** @return factory for parsing files */
	ResourceFactory getResourceFactory();
}
