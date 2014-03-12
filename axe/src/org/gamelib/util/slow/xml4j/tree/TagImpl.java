/**
 * 
 */
package org.gamelib.util.slow.xml4j.tree;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.gamelib.util.slow.xml4j.Attr;
import org.gamelib.util.slow.xml4j.Tag;
import org.gamelib.util.slow.xml4j.io.OutputFormat;
import org.xml.sax.Attributes;

/**
 * @author pwnedary
 */
public class TagImpl implements Tag {

	private String qName;
	private List<Tag> childs = new LinkedList<>();
	// private LinkedHashMap<String, Attr> attributes;
	private List<Attr> attributes;
	private String cdata;

	/**
	 * 
	 */
	public TagImpl(String qName) {
		this.qName = qName;
	}

	/** {@inheritDoc} */
	@Override
	public Tag add(Tag tag) {
		childs.add(tag);
		return tag;
	}

	/** {@inheritDoc} */
	@Override
	public void print() {
		System.out.println(toString());
	}

	/** {@inheritDoc} */
	@Override
	public void setAttributes(Attributes attributes) {
		this.attributes = new ArrayList<Attr>(attributes.getLength());
		for (int i = 0; i < attributes.getLength(); i++) {
			String qName = attributes.getQName(i);
			String value = attributes.getValue(i);
			this.attributes.add(new AttrImpl(qName, value));
		}
	}

	/** {@inheritDoc} */
	@Override
	public Attr getAttribute(String qName) {
		// if (attributes == null || !attributes.containsKey(qName)) throw new RuntimeException("no attribute called " + qName);
		if (this.attributes == null) this.attributes = new LinkedList<>();
		for (Iterator<Attr> iterator = this.attributes.iterator(); iterator.hasNext();) {
			Attr attr = (Attr) iterator.next();
			if (qName.equals(attr.getQName())) return attr;
		}
		Attr attr = new AttrImpl(qName, "");
		this.attributes.add(attr);
		return attr;
	}

	/** {@inheritDoc} */
	@Override
	public void addComment(String string) {
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	@Override
	public void addCDATA(String cdata) {
		this.cdata = cdata;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(qName);
		builder.append(" {\n");
		builder.append("CDATA: " + this.cdata + "\n");
		for (Tag tag : childs) {
			builder.append(tag.toString());
		}
		builder.append("\n}\n");
		return builder.toString();
	}

	/** {@inheritDoc} */
	@Override
	public String getQName() {
		return qName;
	}

	/** {@inheritDoc} */
	@Override
	public List<Tag> children() {
		return childs;
	}

	/** {@inheritDoc} */
	@Override
	public String asXML() {
		StringBuilder builder = new StringBuilder();
		builder.append("<" + getQName() + " ");
		if (attributes != null) for (Attr attribute : this.attributes)
			builder.append(attribute.getQName() + "=" + '"' + attribute.getValue() + '"' + " ");
		if ((cdata == null || cdata.isEmpty()) && childs.isEmpty()) {
			builder.append("/>");
		} else {
			builder.append(">");
			if (cdata != null && !cdata.isEmpty()) builder.append(cdata);
			for (Tag child : childs) {
				builder.append(child.asXML());
			}
			builder.append("</" + getQName() + ">");
		}
		return builder.toString();
	}

	/** {@inheritDoc} */
	@Override
	public void writeXML(FileWriter writer, String indent, OutputFormat format)
			throws IOException {
		writer.write(indent + "<" + getQName());
		if (attributes != null) for (Attr attribute : this.attributes)
			// attributes
			writer.write(" " + attribute.getQName() + "=" + '"' + attribute.getValue() + '"');
		if ((cdata == null || cdata.isEmpty()) && childs.isEmpty()) writer.write(" />\n");
		else {
			writer.write(">\n");
			if (cdata != null && !cdata.isEmpty()) writer.write(indent + format.indent + cdata + "\n"); // cdata
			for (Tag child : childs)
				child.writeXML(writer, indent + format.indent, format);
			writer.write(indent + "</" + getQName() + ">\n");
		}
	}

	/** {@inheritDoc} */
	@Override
	public List<Tag> downwards() {
		List<Tag> list = new LinkedList<>();
		list.add(this);
		list.addAll(children());
		return list;
	}

	/** {@inheritDoc} */
	@Override
	public Tag getTagById(String id) {
		Tag selected = null;
		for (Tag tag : downwards()) {
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
		for (Tag tag : downwards()) {
			if (tag.getQName().equals(qName)) tags.add(tag);
		}
		return tags;
	}

}
