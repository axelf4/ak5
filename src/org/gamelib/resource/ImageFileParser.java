/**
 * 
 */
package org.gamelib.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author Axel
 * 
 */
public class ImageFileParser implements FileParser {

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.resource.FileParser#parse(java.io.File) */
	@Override
	public Object parse(File file) throws IOException {
		// return ImageIO.read(new FileInputStream(file));
		// return ImageIO.read(getClass().getResourceAsStream(file.getPath()));
		return ImageIO.read(ResourceLoader.getResourceAsStream(file.getPath()));
	}

	@Override
	public String[] getExtensions() {
		return ImageIO.getReaderFileSuffixes();
	}

}
