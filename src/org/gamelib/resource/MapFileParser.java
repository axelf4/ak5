/**
 * 
 */
package org.gamelib.resource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.zip.GZIPInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.gamelib.util.Log;
import org.gamelib.util.Map;
import org.gamelib.util.Map.Layer;
import org.gamelib.util.Map.ObjectGroup;
import org.gamelib.util.TileSet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Axel
 * 
 */
public class MapFileParser implements FileParser {

	/** The code used to decode Base64 encoding */
	private static byte[] baseCodes = new byte[256];

	/**
	 * Static initialiser for the codes created against Base64
	 */
	static {
		for (int i = 0; i < 256; i++)
			baseCodes[i] = -1;
		for (int i = 'A'; i <= 'Z'; i++)
			baseCodes[i] = (byte) (i - 'A');
		for (int i = 'a'; i <= 'z'; i++)
			baseCodes[i] = (byte) (26 + i - 'a');
		for (int i = '0'; i <= '9'; i++)
			baseCodes[i] = (byte) (52 + i - '0');
		baseCodes['+'] = 62;
		baseCodes['/'] = 63;
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.resource.FileParser#parse(java.io.InputStream) */
	@Override
	public Object parse(File file) throws IOException {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(FileLoader.getResourceStream(file.getPath()));
			Element root = doc.getDocumentElement();

			int orientation = root.getAttribute("orientation").equals("orthogonal") ? Map.ORTHOGONAL : Map.ISOMETRIC;
			int width = Integer.parseInt(root.getAttribute("width"));
			int height = Integer.parseInt(root.getAttribute("height"));
			int tileWidth = Integer.parseInt(root.getAttribute("tilewidth"));
			int tileHeight = Integer.parseInt(root.getAttribute("tileheight"));
			String mapLocation = file.getParent();

			// now read the map properties
			Properties props = new Properties();
			Element propsElement = (Element) root.getElementsByTagName("properties").item(0);
			if (propsElement != null) {
				NodeList properties = propsElement.getElementsByTagName("property");
				if (properties != null) {
					props = new Properties();
					for (int p = 0; p < properties.getLength(); p++) {
						Element propElement = (Element) properties.item(p);

						String name = propElement.getAttribute("name");
						String value = propElement.getAttribute("value");
						props.setProperty(name, value);
					}
				}
			}

			List<TileSet> tileSets = new ArrayList<TileSet>();
			TileSet tileSet = null;
			TileSet lastSet = null;
			NodeList setNodes = root.getElementsByTagName("tileset");
			for (int i = 0; i < setNodes.getLength(); i++) {
				Element element = (Element) setNodes.item(i);
				tileSet = new TileSet(element, mapLocation); // TODO not
																// hardcode
				tileSet.index = i;
				if (lastSet != null)
					lastSet.lastGID = tileSet.firstGID - 1;
				lastSet = tileSet;
				tileSets.add(tileSet);
			}

			List<Layer> layers = new ArrayList<Layer>();
			NodeList layerNodes = root.getElementsByTagName("layer");
			for (int i = 0; i < layerNodes.getLength(); i++) {
				Element element = (Element) layerNodes.item(i);
				Layer layer = new Map.Layer(element);
				layer.index = i;
				Element dataNode = (Element) element.getElementsByTagName("data").item(0);
				String encoding = dataNode.getAttribute("encoding");
				String compression = dataNode.getAttribute("compression");

				if (encoding.equals("base64") && compression.equals("gzip")) {
					try {
						Node cdata = dataNode.getFirstChild();
						char[] enc = cdata.getNodeValue().trim().toCharArray();
						byte[] dec = decodeBase64(enc);
						GZIPInputStream is = new GZIPInputStream(new ByteArrayInputStream(dec));

						for (int y = 0; y < height; y++) {
							for (int x = 0; x < width; x++) {
								int tileId = 0;
								tileId |= is.read();
								tileId |= is.read() << 8;
								tileId |= is.read() << 16;
								tileId |= is.read() << 24;

								layer.data[x][y] = tileId;
							}
						}
					} catch (IOException e) {
						Log.error("", e);
						throw new RuntimeException("Unable to decode base 64 block");
					}
				} else {
					throw new RuntimeException("unsupport tiled map type: " + encoding + "," + compression + " (only gzip base64 supported)");
				}
				layers.add(layer);
			}

			// acquire object-groups
			List<ObjectGroup> objectGroups = new ArrayList<ObjectGroup>();
			NodeList objectGroupNodes = root.getElementsByTagName("objectgroup");
			for (int i = 0; i < objectGroupNodes.getLength(); i++) {
				Element element = (Element) objectGroupNodes.item(i);
				ObjectGroup objectGroup = new Map.ObjectGroup(element);
				objectGroup.index = i;

				objectGroups.add(objectGroup);
			}

			return new Map(tileWidth, tileHeight, tileSets, layers, objectGroups, orientation, mapLocation, props);
		} catch (ParserConfigurationException | SAXException e) {
			Log.error("couldn't parse map", e);
			return null;
		}
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.resource.FileParser#getExtensions() */
	@Override
	public String[] getExtensions() {
		return new String[] { "tmx" };
	}

	/**
	 * Decode a Base64 string as encoded by TilED
	 * 
	 * @param data The string of character to decode
	 * @return The byte array represented by character encoding
	 */
	private byte[] decodeBase64(char[] data) {
		int temp = data.length;
		for (int ix = 0; ix < data.length; ix++) {
			if ((data[ix] > 255) || baseCodes[data[ix]] < 0) {
				--temp;
			}
		}

		int len = (temp / 4) * 3;
		if ((temp % 4) == 3)
			len += 2;
		if ((temp % 4) == 2)
			len += 1;

		byte[] out = new byte[len];

		int shift = 0;
		int accum = 0;
		int index = 0;

		for (int ix = 0; ix < data.length; ix++) {
			int value = (data[ix] > 255) ? -1 : baseCodes[data[ix]];

			if (value >= 0) {
				accum <<= 6;
				shift += 6;
				accum |= value;
				if (shift >= 8) {
					shift -= 8;
					out[index++] = (byte) ((accum >> shift) & 0xff);
				}
			}
		}

		if (index != out.length) {
			throw new RuntimeException("Data length appears to be wrong (wrote " + index + " should be " + out.length + ")");
		}

		return out;
	}

}
