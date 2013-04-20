/**
 * 
 */
package org.gamelib.util;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.gamelib.backend.Graphics;
import org.gamelib.resource.FileLoader;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * A map intended to parse TileED maps. Maps can be loaded with
 * {@link FileLoader#load(String)}.
 * 
 * @see TileED TileED map editor <a
 *      href="http://mapeditor.org/">http://mapeditor.org/</a>
 * @author pwnedary
 * 
 */
public class Map {

	/** Indicates a orthogonal map */
	public static final int ORTHOGONAL = 0;
	/** Indicates an isometric map */
	public static final int ISOMETRIC = 1;

	/** The width of the map */
	private int width;
	/** The height of the map */
	private int height;
	/** The width of the tiles used on the map */
	public int tileWidth;
	/** The height of the tiles used on the map */
	public int tileHeight;
	/** The orientation of this map */
	private int orientation;
	/** The directory in which the tilesets are located. */
	public String mapLocation;
	
	/** the properties of the map */
    public Properties properties;

	/** The list of tilesets defined in the map */
	private List<TileSet> tileSets = new ArrayList<TileSet>();
	/** The list of layers defined in the map */
	public List<Layer> layers = new ArrayList<Layer>();
	/** The list of object-groups defined in the map */
	public List<ObjectGroup> objectGroups = new ArrayList<ObjectGroup>();

	public Map(int tileWidth, int tileHeight, List<TileSet> tileSets, List<Layer> layers, List<ObjectGroup> objectGroups, int orientation, String location, Properties properties) {
		for (Layer layer : layers) {
			int[][] data = layer.data;
			width = data.length > width ? data.length : width;
			height = data[0].length > height ? data[0].length : height;
		}
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.tileSets = tileSets;
		this.layers = layers;
		this.objectGroups = objectGroups;
		this.orientation = orientation;
		this.mapLocation = location;
		this.properties = properties;
	}

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

	/**
	 * Draws the selected region of the map.
	 * 
	 * @param g2d the {@link Graphics2D} object
	 * @param rx region start x
	 * @param ry region start y
	 * @param rw region width
	 * @param rh region height
	 */
	public void draw(Graphics g, int rx, int ry, int rw, int rh) {
		for (int tileSet = 0; tileSet < tileSets.size(); tileSet++) {
			TileSet set = tileSets.get(tileSet);
			for (int layer = 0; layer < layers.size(); layer++) {
				int[][] data = layers.get(layer).data;
				for (int tx = rx; tx < data.length; tx++) {
					for (int ty = ry; ty < data[0].length; ty++) {
						int gid = data[tx][ty];
						if (gid >= set.firstGID && gid <= set.lastGID) {
							set.draw(g, gid, tx * tileWidth, ty * tileHeight);
						}
					}
				}
			}
		}
	}

	/**
	 * Draws the the map.
	 * 
	 * @param g2d the {@link Graphics2D} object
	 */
	public void draw(Graphics g) {
		draw(g, 0, 0, width, height);
	}

	/**
	 * Get the gloal ID of the tile at the specified location in this layer
	 * 
	 * @param x The x coorindate of the tile
	 * @param y The y coorindate of the tile
	 * @return The global ID of the tile
	 */
	public int getTileID(int layer, int x, int y) {
		return layers.get(layer).data[x][y];
	}

	/**
	 * Set the global tile ID at a specified location
	 * 
	 * @param x The x location to set
	 * @param y The y location to set
	 * @param tile The tile value to set
	 */
	public void setTileID(int layer, int x, int y, int tile) {
		layers.get(layer).data[x][y] = tile;
	}

	/**
	 * Get a tileset by a given global ID
	 * 
	 * @param gid The global ID of the tileset to retrieve
	 * @return The tileset requested or null if no tileset matches
	 */
	public TileSet getTileSetByGID(int gid) {
		for (int i = 0; i < tileSets.size(); i++) {
			TileSet set = (TileSet) tileSets.get(i);
			if (set.contains(gid))
				return set;
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
		/**
		 * The tile data representing this data.
		 */
		public int[][] data;
		/** The width of this layer */
		public int width;
		/** The height of this layer */
		public int height;

		/** the properties of this layer */
		public Properties props;

		public Layer(Element element) {
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

	/**
	 * A group of objects on the map (objects layer)
	 * 
	 * @author kulpae
	 */
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

		/**
		 * Create a new group based on the XML definition
		 * 
		 * @param element The XML element describing the layer
		 * @throws SlickException Indicates a failure to parse the XML group
		 */
		public ObjectGroup(Element element) {
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

	/**
	 * An object from a object-group on the map
	 * 
	 * @author kulpae
	 */
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

		/**
		 * Create a new group based on the XML definition
		 * 
		 * @param element The XML element describing the layer
		 * @throws SlickException Indicates a failure to parse the XML group
		 */
		public GroupObject(Element element) {
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
}
