/**
 * 
 */
package org.gamelib.backends;

import org.gamelib.DisplayMode;
import org.gamelib.Game;
import org.gamelib.Input;
import org.gamelib.graphics.Graphics;
import org.gamelib.graphics.Image;

/**
 * maybe interface?
 * 
 * @author pwnedary
 * 
 */
public interface Backend {
	public void start(Game instance, DisplayMode mode);

	/** better with singleton */
	public Graphics getGraphics();
	
	public Graphics getGraphics(Image image);

	/** no need for singleton */
	public Input getInput();

	public void screenUpdate();

	/** @return system time in milliseconds */
	public long getTime();
	
	public boolean shouldClose();
	
	public void setTitle(String s);
	
	// public abstract void getImage();
}
