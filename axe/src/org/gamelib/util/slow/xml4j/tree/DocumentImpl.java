/**
 * 
 */
package org.gamelib.util.slow.xml4j.tree;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.gamelib.util.slow.xml4j.Attr;
import org.gamelib.util.slow.xml4j.Document;
import org.gamelib.util.slow.xml4j.Tag;
import org.gamelib.util.slow.xml4j.io.OutputFormat;

/**
 * @author pwnedary
 */
public class DocumentImpl implements Document {

	Tag root;
	String encoding;

	/** {@inheritDoc} */
	@Override
	public Tag getRoot() {
		return root;
	}

	/** {@inheritDoc} */
	@Override
	public void setRoot(Tag tag) {
		this.root = tag;
	}

	/** {@inheritDoc} */
	@Override
	public void addComment(String string) {
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	@Override
	public String getXMLEncoding() {
		return encoding;
	}

	/** {@inheritDoc} */
	@Override
	public void setXMLEncoding(String encoding) {
		this.encoding = encoding;
	}

	/** {@inheritDoc} */
	@Override
	public void write(File file, OutputFormat format) {
		if (format == null) format = new OutputFormat();
		try {
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			writer.write("<?xml version=" + '"' + "1.0" + '"' + "?>\n");
			getRoot().writeXML(writer, "", format);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** {@inheritDoc} */
	@Override
	public Tag getTagById(String id) {
		Tag selected = null;
		for (Tag tag : getRoot().downwards()) {
			Attr attribute = tag.getAttribute("id");
			if (attribute == null) attribute = tag.getAttribute("ID");
			if (attribute.isId() && attribute.getValue().equals(id)) if (selected != null) selected = tag;
			else return null;
		}
		return selected;
	}

	/** {@inheritDoc} */
	@Override
	public List<Tag> getTagsByName(String qName) {
		List<Tag> tags = new LinkedList<>();
		for (Tag tag : getRoot().downwards()) {
			if (tag.getQName().equals(qName)) tags.add(tag);
		}
		return tags;
	}

}
