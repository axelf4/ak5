/**
 * 
 */
package org.gamelib.util.slow.xml4j;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.gamelib.util.slow.xml4j.io.SAXContentHandler;
import org.gamelib.util.slow.xml4j.tree.DocumentImpl;
import org.gamelib.util.slow.xml4j.tree.TagImpl;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/**
 * @author pwnedary
 */
public class DocumentFactory {

	private static final String SAX_STRING_INTERNING = "http://xml.org/sax/features/string-interning";
	private static final String SAX_NAMESPACE_PREFIXES = "http://xml.org/sax/features/namespace-prefixes";
	private static final String SAX_NAMESPACES = "http://xml.org/sax/features/namespaces";
	private static final String SAX_DECL_HANDLER = "http://xml.org/sax/properties/declaration-handler";
	private static final String SAX_LEXICAL_HANDLER = "http://xml.org/sax/properties/lexical-handler";
	private static final String SAX_LEXICALHANDLER = "http://xml.org/sax/handlers/LexicalHandler";

	private DocumentFactory() {}

	public static DocumentFactory newInstance() {
		return new DocumentFactory();
	}

	public Document parse(InputSource source) {
		Document document = null;
		try {
			document = new DocumentImpl();

			XMLReader reader = getXMLReader(false, false);
			SAXContentHandler handler = new SAXContentHandler(document);
			reader.setContentHandler(handler);
			reader.setProperty(SAX_LEXICAL_HANDLER, handler);

			reader.parse(source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return document;
	}

	private static XMLReader getXMLReader(boolean validating, boolean awareness)
			throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(validating);
		factory.setNamespaceAware(awareness);

		SAXParser parser = factory.newSAXParser();

		return parser.getXMLReader();
	}
	
	public static Tag newElement(String qName) {
		return new TagImpl(qName);
	}
}
