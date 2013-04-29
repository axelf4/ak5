/**
 * 
 */
package org.gamelib.backend;

import java.io.File;
import java.io.IOException;

/**
 * @author pwnedary
 *
 */
public interface ResourceFactory {

	/** @return the image from the file */
	public Image getImage(File file) throws IOException;

	/** @return an empty image */
	public Image createImage(int width, int height);
	
	// public Sound getSound(File file) throws IOException;
	
}
