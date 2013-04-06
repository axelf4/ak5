/**
 * 
 */
package org.gamelib.resource;

import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import org.gamelib.util.Log;

/**
 * @author pwnedary
 *
 */
public class FontFileParser implements FileParser {

	/* (non-Javadoc)
	 * @see org.gamelib.resource.FileParser#parse(java.io.File)
	 */
	@Override
	public Object parse(File file) throws IOException {
		/*try {
			return new UnicodeFont(Font.createFont(Font.TRUETYPE_FONT, file));
		} catch (FontFormatException e) {
			Log.error("can't create font", e);
			return null;
		}*/
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.gamelib.resource.FileParser#getExtensions()
	 */
	@Override
	public String[] getExtensions() {
		return new String[] {"ttf"};
	}

}
