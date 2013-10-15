/**
 * 
 */
package org.gamelib.util.slow.xml4j;

import org.xml.sax.Attributes;

/**
 * @author pwnedary
 */
public interface Tag extends Branch, Node {
	public void print();

	public String getQName();

	// public void setQName(String qName);

	// public void setAttribute(String qName, String value);
	// public String getAttribute(String qName);
	/** @return the attribute by qName, or creates one if not found */
	public Attr getAttribute(String qName);

	public void setAttributes(Attributes attributes);

	public void addCDATA(String string);

	public void addComment(String cdata);
}
