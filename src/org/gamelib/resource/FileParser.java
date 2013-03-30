/**
 * 
 */
package org.gamelib.resource;

import java.io.File;
import java.io.IOException;

/**
 * @author Axel
 *
 */
public interface FileParser {
	public Object parse(File file) throws IOException;
	public String[] getExtensions();
}
