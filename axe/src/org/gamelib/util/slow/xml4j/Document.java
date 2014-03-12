/**
 * 
 */
package org.gamelib.util.slow.xml4j;

import java.io.File;
import java.util.List;

import org.gamelib.util.slow.xml4j.io.OutputFormat;

/**
 * @author pwnedary
 */
public interface Document {
	/** @return the top-level tag */
	Tag getRoot();

	void setRoot(Tag tag);

	void addComment(String string);

	String getXMLEncoding();

	void setXMLEncoding(String encoding);

	void write(File file, OutputFormat format);

	Tag getTagById(String id);

	List<Tag> getTagsByName(String qName);
}
