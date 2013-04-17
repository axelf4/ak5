/**
 * 
 */
package org.gamelib.backend;

import org.gamelib.DisplayMode;
import org.gamelib.Game;
import org.gamelib.Input;

/**
 * maybe interface?
 * 
 * @author pwnedary
 */
public interface Backend {
	public void start(Game instance, DisplayMode mode);

	/**
	 * better with singleton
	 * 
	 * @deprecated use {@link #getGraphics(Image)}
	 */
	public Graphics getGraphics();

	public Graphics getGraphics(Image image);

	/** no need for singleton */
	public Input getInput();

	public void screenUpdate();

	/** @return System time in milliseconds */
	public long getTime();

	public boolean shouldClose();

	/** @param s the new window title */
	public void setTitle(String s);

	/** @return an empty image */
	public Image createImage(int width, int height);
}
