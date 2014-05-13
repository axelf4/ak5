/**
 * 
 */
package ak5.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.zip.GZIPInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ak5.Platform;
import ak5.graphics.Batch;

/** A map intended to parse TileED maps. Maps can be loaded with {@link FileLoader#load(String)}.
 * 
 * @see TileED TileED map editor <a href="http://mapeditor.org/">http://mapeditor.org/</a>
 * @author pwnedary */
public class TiledMap implements Map {
	private static final long serialVersionUID = 3403715541913955133L;

	/** The width of the map */
	private int width;
	/** The height of the map */
	private int height;
	/** The width of the tiles used on the map */
	private final int tileWidth;
	/** The height of the tiles used on the map */
	private final int tileHeight;
	/** The orientation of this map */
	public final int orientation;

	/** the properties of the map */
	public final Properties properties;

	/** The list of tilesets defined in the map */
	private final List<TileSet> tileSets;
	/** The list of layers defined in the map */
	public final List<Layer> layers;
	/** The list of object-groups defined in the map */
	public final List<ObjectGroup> objectGroups;

	public TiledMap(Platform platform, File file) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(platform.getResourceAsStream(file.getPath()));
			Element root = doc.getDocumentElement();

			width = Integer.parseInt(root.getAttribute("width"));
			height = Integer.parseInt(root.getAttribute("height"));
			tileWidth = Integer.parseInt(root.getAttribute("tilewidth"));
			tileHeight = Integer.parseInt(root.getAttribute("tileheight"));
			orientation = root.getAttribute("orientation").equals("orthogonal") ? TiledMap.ORTHOGONAL : TiledMap.ISOMETRIC;
			String mapLocation = file.getParent();

			properties = new Properties(); // now read the map properties
			Element propsElement = (Element) root.getElementsByTagName("properties").item(0);
			if (propsElement != null) {
				NodeList props = propsElement.getElementsByTagName("property");
				if (props != null) for (int p = 0; p < props.getLength(); p++) {
					Element propElement = (Element) props.item(p);

					String name = propElement.getAttribute("name");
					String value = propElement.getAttribute("value");
					properties.setProperty(name, value);
				}
			}

			// acquire tile-sets
			tileSets = new ArrayList<TileSet>();
			TileSet lastSet = null;
			NodeList setNodes = root.getElementsByTagName("tileset");
			for (int i = 0; i < setNodes.getLength(); i++) {
				Element element = (Element) setNodes.item(i);
				TileSet tileSet = new TileSet(platform, element, mapLocation);
				tileSet.index = i;
				if (lastSet != null) lastSet.lastGID = tileSet.firstGID - 1;
				lastSet = tileSet;
				tileSets.add(tileSet);
			}

			// acquire layers
			layers = new ArrayList<Layer>();
			NodeList layerNodes = root.getElementsByTagName("layer");
			for (int i = 0; i < layerNodes.getLength(); i++) {
				Element element = (Element) layerNodes.item(i);
				Layer layer = new TiledMap.Layer(element);
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

						for (int y = 0; y < height; y++)
							for (int x = 0; x < width; x++) {
								int tileId = 0;
								tileId |= is.read();
								tileId |= is.read() << 8;
								tileId |= is.read() << 16;
								tileId |= is.read() << 24;

								layer.data[x][y] = tileId;
							}
					} catch (IOException e) {
						throw new RuntimeException("Unable to decode base 64 block");
					}
				} else throw new RuntimeException("unsupport tiled map type: " + encoding + "," + compression + " (only gzip base64 supported)");
				layers.add(layer);
			}

			// acquire object-groups
			objectGroups = new ArrayList<ObjectGroup>();
			NodeList objectGroupNodes = root.getElementsByTagName("objectgroup");
			for (int i = 0; i < objectGroupNodes.getLength(); i++) {
				ObjectGroup objectGroup = new TiledMap.ObjectGroup((Element) objectGroupNodes.item(i));
				objectGroup.index = i;

				objectGroups.add(objectGroup);
			}
		} catch (IOException | ParserConfigurationException | SAXException e) {
			throw new Error("couldn't parse map");
		}
	}

	public TiledMap(int tileWidth, int tileHeight, List<TileSet> tileSets, List<Layer> layers, List<ObjectGroup> objectGroups, int orientation, Properties properties) {
		for (Layer layer : layers) {
			int[][] data = layer.data;
			width = data.length > width ? data.length : width;
			height = data[0].length > height ? data[0].length : height;
		}
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		(this.tileSets = new ArrayList<>()).addAll(tileSets);
		(this.layers = new ArrayList<>()).addAll(layers);
		(this.objectGroups = new ArrayList<>()).addAll(objectGroups);
		this.orientation = orientation;
		this.properties = properties;
	}

	@Override
	public void draw(Batch batch, int x, int y, int sx, int sy, int layer) {
		int gid = getTileID(sx, sy, layer);
		getTileSetByGID(gid).draw(batch, gid, x, y);
	}

	@Override
	public void draw(Batch batch, int x, int y, int rx, int ry, int rw, int rh) {
		for (int tileSet = 0; tileSet < tileSets.size(); tileSet++) {
			TileSet set = tileSets.get(tileSet);

			if (orientation == ORTHOGONAL) for (int layer = 0; layer < layers.size(); layer++) {
				int[][] data = layers.get(layer).data;
				for (int tx = rx; tx < data.length; tx++)
					for (int ty = ry; ty < data[0].length; ty++) {
						int gid = data[tx][ty];
						if (gid >= set.firstGID && gid <= set.lastGID) set.draw(batch, gid, tx * tileWidth, ty * tileHeight);
					}
			}
			else if (orientation == ISOMETRIC) throw new UnsupportedOperationException();
		}
	}

	@Override
	public int getTileID(int x, int y, int layer) {
		return layers.get(layer).data[x][y];
	}

	@Override
	public void setTileID(int x, int y, int layer, int tile) {
		layers.get(layer).data[x][y] = tile;
	}

	/** Get a tileset by a given global ID
	 * 
	 * @param gid The global ID of the tileset to retrieve
	 * @return The tileset requested or null if no tileset matches */
	public TileSet getTileSetByGID(int gid) {
		for (int i = 0; i < tileSets.size(); i++) {
			TileSet set = (TileSet) tileSets.get(i);
			if (set.contains(gid)) return set;
		}
		return null;
	}

	public Layer getLayer(int layer) {
		return layers.get(layer);
	}

	public static class Layer {
		/** The index of this layer */
		public int index;
		/** The name of this layer - read from the XML */
		public String name;
		/** The tile data representing this data. */
		public int[][] data;
		/** The width of this layer */
		public int width;
		/** The height of this layer */
		public int height;

		/** the properties of this layer */
		public Properties props;

		private Layer(Element element) {
			name = element.getAttribute("name");
			width = Integer.parseInt(element.getAttribute("width"));
			height = Integer.parseInt(element.getAttribute("height"));
			data = new int[width][height];

			// now read the layer properties
			Element propsElement = (Element) element.getElementsByTagName("properties").item(0);
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
		}
	}

	/** A group of objects on the map (objects layer)
	 * 
	 * @author kulpae */
	public static class ObjectGroup {
		/** The index of this group */
		public int index;
		/** The name of this group - read from the XML */
		public String name;
		/** The Objects of this group */
		public List<GroupObject> objects;
		/** The width of this layer */
		public int width;
		/** The height of this layer */
		public int height;

		/** the properties of this group */
		public Properties props;

		/** Create a new group based on the XML definition
		 * 
		 * @param element The XML element describing the layer
		 * @throws SlickException Indicates a failure to parse the XML group */
		private ObjectGroup(Element element) {
			name = element.getAttribute("name");
			width = Integer.parseInt(element.getAttribute("width"));
			height = Integer.parseInt(element.getAttribute("height"));
			objects = new ArrayList<GroupObject>();

			// now read the layer properties
			Element propsElement = (Element) element.getElementsByTagName("properties").item(0);
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

			NodeList objectNodes = element.getElementsByTagName("object");
			for (int i = 0; i < objectNodes.getLength(); i++) {
				Element objElement = (Element) objectNodes.item(i);
				GroupObject object = new GroupObject(objElement);
				object.index = i;
				objects.add(object);
			}
		}
	}

	/** An object from a object-group on the map
	 * 
	 * @author kulpae */
	public static class GroupObject {
		/** The index of this object */
		public int index;
		/** The name of this object - read from the XML */
		public String name;
		/** The type of this object - read from the XML */
		public String type;
		/** The x-coordinate of this object */
		public int x;
		/** The y-coordinate of this object */
		public int y;
		/** The width of this object */
		public int width;
		/** The height of this object */
		public int height;
		/** The image source */
		public String image;

		/** the properties of this group */
		public Properties props;

		/** Create a new group based on the XML definition
		 * 
		 * @param element The XML element describing the layer
		 * @throws SlickException Indicates a failure to parse the XML group */
		private GroupObject(Element element) {
			name = element.getAttribute("name");
			type = element.getAttribute("type");
			x = Integer.parseInt(element.getAttribute("x"));
			y = Integer.parseInt(element.getAttribute("y"));
			width = Integer.parseInt(element.getAttribute("width"));
			height = Integer.parseInt(element.getAttribute("height"));

			Element imageElement = (Element) element.getElementsByTagName("image").item(0);
			if (imageElement != null) {
				image = imageElement.getAttribute("source");
			}

			// now read the layer properties
			Element propsElement = (Element) element.getElementsByTagName("properties").item(0);
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
		}
	}

	/** Decode a Base64 string as encoded by TilED
	 * 
	 * @param data The string of character to decode
	 * @return The byte array represented by character encoding */
	private static byte[] decodeBase64(char[] data) {
		int temp = data.length;
		for (int ix = 0; ix < data.length; ix++) {
			if ((data[ix] > 255) || baseCodes[data[ix]] < 0) {
				--temp;
			}
		}

		int len = (temp / 4) * 3;
		if ((temp % 4) == 3) len += 2;
		if ((temp % 4) == 2) len += 1;

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

		if (index != out.length) { throw new RuntimeException("Data length appears to be wrong (wrote " + index + " should be " + out.length + ")"); }

		return out;
	}

	/** The code used to decode Base64 encoding */
	private static final byte[] baseCodes = new byte[256];

	/** Static initializer for the codes created against Base64 */
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

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getTileWidth() {
		return tileWidth;
	}

	@Override
	public int getTileHeight() {
		return tileHeight;
	}

	@Override
	public int getLayerCount() {
		return layers.size();
	}
}
