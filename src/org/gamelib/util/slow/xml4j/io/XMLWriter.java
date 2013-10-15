/**
 * 
 */
package org.gamelib.util.slow.xml4j.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.gamelib.util.slow.xml4j.Document;

/**
 * @author pwnedary
 */
public class XMLWriter {

	OutputFormat format; // TODO use lazy initialization

	public void write(Document document) {
		if (format == null) format = new OutputFormat();
		try {
			File file = new File("test2.xml");
//			file.delete();
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			writer.write("<?xml version=" + '"' + "1.0" + '"' + "?>\n");
			document.getRoot().writeXML(writer, "", format);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
