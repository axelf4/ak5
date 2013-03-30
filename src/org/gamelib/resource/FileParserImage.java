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
public class FileParserImage implements FileParser {

	/* (non-Javadoc)
	 * @see org.gamelib.resource.FileParser#parse(java.io.File)
	 */
	@Override
	public Object parse(File file) throws IOException {
		return ImageIO.read(new FileInputStream(file));
	}

	@Override
	public String[] getExtensions() {
		return ImageIO.getReaderFileSuffixes();
	}

}
