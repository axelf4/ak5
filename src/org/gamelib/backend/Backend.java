/**
 * 
 */
package org.gamelib.backend;

import java.io.File;
import java.io.IOException;

import org.gamelib.Destroyable;
import org.gamelib.Game;
import org.gamelib.Input;
import org.gamelib.Resolution;

/**
 * The class responsible for collecting input and processing it.
 * 
 * @author pwnedary
 */
public interface Backend extends Destroyable {
	public void start(Game instance, Resolution resolution);

	/**
	 * better with singleton
	 * 
	 * @deprecated use {@link #getGraphics(Image)}
	 */
	public Graphics getGraphics();

	public Graphics getGraphics(Image image);

	/** @return the processor for input */
	public Input getInput();

	public void screenUpdate(float delta);

	/** @return system time in milliseconds */
	public long getTime();

	public boolean shouldClose();

	/** @param s the new window title */
	public void setTitle(String s);
	
	/** @return factory for parsing files */
	public ResourceFactory getResourceFactory();
	
	/** @return the image from the file */
	public Image getImage(File file) throws IOException;

	/** @return an empty image */
	public Image createImage(int width, int height);
	
	public Sound getSound(File file) throws IOException;
}
