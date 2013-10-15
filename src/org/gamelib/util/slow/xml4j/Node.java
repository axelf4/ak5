/**
 * 
 */
package org.gamelib.util.slow.xml4j;

import java.io.FileWriter;
import java.io.IOException;

import org.gamelib.util.slow.xml4j.io.OutputFormat;

/**
 * @author pwnedary
 */
public interface Node {
	public String asXML();

	/** @throws IOException */
	public void writeXML(FileWriter writer, String indent, OutputFormat format) throws IOException;
}
