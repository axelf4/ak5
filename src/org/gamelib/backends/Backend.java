/**
 * 
 */
package org.gamelib.backends;

import org.gamelib.DisplayMode;
import org.gamelib.Game;
import org.gamelib.Graphics;
import org.gamelib.Input;

/**
 * maybe interface?
 * @author pwnedary
 * 
 */
public abstract class Backend {
	public abstract void start(Game instance, DisplayMode mode);

	public abstract Graphics getGraphics();

	public abstract Input getInput();

	public abstract void screenUpdate();
}
