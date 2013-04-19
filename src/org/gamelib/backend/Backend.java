/**
 * 
 */
package org.gamelib.backend;

import java.io.File;
import java.io.IOException;

import org.gamelib.DisplayMode;
import org.gamelib.Game;
import org.gamelib.Input;

/**
 * The class responsible for collecting input and processing it.
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

	/** @return the processor for input */
	public Input getInput();

	public void screenUpdate();

	/** @return system time in milliseconds */
	public long getTime();

	public boolean shouldClose();

	/** @param s the new window title */
	public void setTitle(String s);
	
	/** @return the image from the file */
	public Image getImage(File file) throws IOException;

	/** @return an empty image */
	public Image createImage(int width, int height);
}
