/**
 * 
 */
package org.gamelib.util.slow.xml4j.io;

import java.util.Stack;

import org.gamelib.util.slow.xml4j.Branch;
import org.gamelib.util.slow.xml4j.Document;
import org.gamelib.util.slow.xml4j.DocumentFactory;
import org.gamelib.util.slow.xml4j.Tag;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author pwnedary
 */
public class SAXContentHandler extends DefaultHandler implements ContentHandler, LexicalHandler, DeclHandler, DTDHandler {

	/** the factory used to create the Document instances */
	private DocumentFactory documentFactory;
	/** the Document being built */
	private Document document;

	/** the {@link Locator} */
	private Locator locator;

	/** flag used to indicate that we are inside a CDATA section */
	private boolean insideCDATASection;

	/** buffer to hold contents of CDATA section across multiple characters events */
	private StringBuffer cdataText;

	// private boolean textInTextBuffer = false;

	/** the Tag we are currently on */
	private Tag currentElement;

	private Stack<Tag> elementStack = new Stack<>();

	public SAXContentHandler() {
		// TODO Auto-generated constructor stub
	}

	public SAXContentHandler(Document document) {
		this.document = document;
	}

	/** {@inheritDoc} */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes)
			throws SAXException {
		Branch branch = currentElement;
		Tag tag = DocumentFactory.newElement(qName);
		if (branch != null) branch.add(tag);

		if (elementStack.isEmpty() && document != null) document.setRoot(tag);
		if (attributes.getLength() > 0) tag.setAttributes(attributes);

		elementStack.add(tag);
		currentElement = tag;
	}

	/** {@inheritDoc} */
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		elementStack.pop();
		if (!elementStack.isEmpty()) currentElement = elementStack.peek();
		else currentElement = null;
	}

	/** {@inheritDoc} */
	@Override
	public void endDocument() throws SAXException {
		// document.getRoot().print(); // test
	}

	/** {@inheritDoc} */
	@Override
	public void elementDecl(String name, String model) throws SAXException {}

	/** {@inheritDoc} */
	@Override
	public void attributeDecl(String eName, String aName, String type, String mode, String value)
			throws SAXException {}

	/** {@inheritDoc} */
	@Override
	public void internalEntityDecl(String name, String value)
			throws SAXException {}

	/** {@inheritDoc} */
	@Override
	public void externalEntityDecl(String name, String publicId, String systemId)
			throws SAXException {}

	/** {@inheritDoc} */
	@Override
	public void startDTD(String name, String publicId, String systemId)
			throws SAXException {}

	/** {@inheritDoc} */
	@Override
	public void endDTD() throws SAXException {}

	/** {@inheritDoc} */
	@Override
	public void startEntity(String name) throws SAXException {}

	/** {@inheritDoc} */
	@Override
	public void endEntity(String name) throws SAXException {}

	/** {@inheritDoc} */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String chars = new String(ch, start, length).trim();
		if (chars.length() == 0) return;

		if (currentElement != null) {
			if (insideCDATASection) {
				cdataText.append(chars);
			}
			currentElement.addCDATA(chars);

		}
	}

	/** {@inheritDoc} */
	@Override
	public void startCDATA() throws SAXException {
		insideCDATASection = true;
		cdataText = new StringBuffer();
		System.out.println("start cdata");
	}

	/** {@inheritDoc} */
	@Override
	public void endCDATA() throws SAXException {
		insideCDATASection = false;
		currentElement.addCDATA(cdataText.toString());
	}

	/** {@inheritDoc} */
	@Override
	public void comment(char[] ch, int start, int length) throws SAXException {
		if (this.currentElement != null) this.currentElement.addComment(new String(ch, start, length));
		else document.addComment(new String(ch, start, length));
	}

}
