/**
 * 
 */
package org.gamelib.backend;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author pwnedary
 */
public interface ResourceFactory {

	/** @return an {@link InputStream} for the specified resource. */
	public InputStream getResourceAsStream(String name);

	/** @return the image from the file */
	public Image getImage(File file) throws IOException;

	/** @return an empty image */
	public Image createImage(int width, int height);

	/** @return the sound from the file */
	public Sound getSound(File file) throws IOException;

}
