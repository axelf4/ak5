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
 * 
 * @author pwnedary
 * 
 */
public abstract class Backend {
	public abstract void start(Game instance, DisplayMode mode);

	/** better with singleton */
	public abstract Graphics getGraphics();

	/** no need for singleton */
	public abstract Input getInput();

	public abstract void screenUpdate();

	/** @return system time in milliseconds */
	public abstract long getTime();
	
	public abstract boolean shouldClose();
	
	public abstract void setTitle(String s);
}
