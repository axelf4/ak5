/**
 * 
 */
package org.gamelib.util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import org.gamelib.backend.Backend;
import org.gamelib.backend.Graphics;
import org.gamelib.backend.Image;
import org.w3c.dom.Element;

/**
 * A layered map, intended to be parsed from an external map editor.
 * 
 * @see TileED TileED map editor <a href="http://mapeditor.org/">http://mapeditor.org/</a>
 * @author pwnedary
 */
public interface Map extends Serializable {
	/** Indicates an orthogonal map */
	public static final int ORTHOGONAL = 0;
	/** Indicates an isometric map */
	public static final int ISOMETRIC = 1;

	/**
	 * Returns the tile at <code>(x,y),layer</code>'s ID.
	 * 
	 * @param x the x-coordinate of the tile
	 * @param y the y-coordinate of the tile
	 * @param layer the layer of the tile
	 * @return the tile's ID
	 */
	public int getTileID(int x, int y, int layer);

	/**
	 * Sets the tile ID at <code>(x,y),layer</code>.
	 * 
	 * @param x the x-coordinate of the tile to set
	 * @param y the y-coordinate of the tile to set
	 * @param layer the layer in which to set the tile in
	 * @param tile the tile ID to set
	 */
	public void setTileID(int x, int y, int layer, int tile);

	/**
	 * Draws the tile at <code>(sx,sy)</code> on the canvas at <code>(x,y)</code>.
	 * 
	 * @param g the {@link Graphics} object
	 * @param x the corner's x-coordinate of where to draw
	 * @param y the corner's y-coordinate of where to draw
	 * @param sx the tile's x-coordinate in this map
	 * @param sy the tile's y-coordinate in this map
	 * @param layer the layer of the tile to draw
	 */
	public void draw(Graphics g, int x, int y, int sx, int sy, int layer);

	/**
	 * Draws the selected region of the map.
	 * 
	 * @param g the {@link Graphics} object
	 * @param x the corner's x-coordinate of where to draw
	 * @param y the corner's y-coordinate of where to draw
	 * @param rx the region's corner's x-coordinate
	 * @param ry the region's corner's y-coordinate
	 * @param rw the region's width
	 * @param rh the region's height
	 */
	public void draw(Graphics g, int x, int y, int rx, int ry, int rw, int rh);

	/**
	 * Returns the number of tiles horizontally.
	 * 
	 * @return the number of tiles horizontally
	 */
	public int getWidth();

	/**
	 * Returns the number of tiles vertically.
	 * 
	 * @return the number of tiles vertically
	 */
	public int getHeight();

	/**
	 * Returns the tiles' width.
	 * 
	 * @return the tiles' width
	 */
	public int getTileWidth();

	/**
	 * Returns the tiles' height.
	 * 
	 * @return the tiles' height
	 */
	public int getTileHeight();

	/**
	 * Returns the number of layers.
	 * 
	 * @return the number of layers
	 */
	public int getLayerCount();

	/**
	 * TODO make more standard not to only use TilED.
	 * 
	 * @author pwnedary
	 */
	public class TileSet {
		/** The index of the tile set */
		public int index;
		/** The name of the tile set */
		public String name;
		/** The first global tile id in the set */
		public int firstGID;
		/** The local global tile id in the set */
		public int lastGID = Integer.MAX_VALUE;
		/** The width of the tiles */
		public int tileWidth;
		/** The height of the tiles */
		public int tileHeight;
		/** The image containing the tiles */
		public Image image;

		/** The number of tiles across the sprite sheet */
		public int tilesAcross;
		/** The number of tiles down the sprite sheet */
		public int tilesDown;

		/** The padding of the tiles */
		@SuppressWarnings("unused")
		private int tileSpacing = 0;
		/** The margin of the tileset */
		@SuppressWarnings("unused")
		private int tileMargin = 0;

		public TileSet() {}

		public TileSet(Backend backend, Element element, String mapLocation) {
			name = element.getAttribute("name");
			firstGID = Integer.parseInt(element.getAttribute("firstgid"));

			tileWidth = Integer.parseInt(element.getAttribute("tilewidth"));
			tileHeight = Integer.parseInt(element.getAttribute("tileheight"));
			String spacing = element.getAttribute("spacing");
			tileSpacing = Integer.parseInt(spacing == "" || spacing == null ? "0" : spacing);
			String margin = element.getAttribute("margin");
			tileMargin = Integer.parseInt(margin == "" || margin == null ? "0" : margin);

			Element imageNode = (Element) element.getElementsByTagName("image").item(0);
			tilesAcross = Integer.parseInt(imageNode.getAttribute("width")) / tileWidth;
			tilesDown = Integer.parseInt(imageNode.getAttribute("height")) / tileHeight;

			try {
				image = backend.getImage(new File(mapLocation + "/" + imageNode.getAttribute("source")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void draw(Graphics g, int x, int y, int gid) {
			int id = gid - firstGID;
			int sx = getTileX(id) * tileWidth;
			int sy = getTileY(id) * tileHeight;
			g.drawImage(image, x, y, x + tileWidth, y + tileHeight, sx, sy, sx + tileWidth, sy + tileHeight);
		}

		/**
		 * Get the x position of a tile on this sheet
		 * 
		 * @param id The tileset specific ID (i.e. not the global one)
		 * @return The index of the tile on the x-axis
		 */
		public int getTileX(int id) {
			return id % tilesAcross;
		}

		/**
		 * Get the y position of a tile on this sheet
		 * 
		 * @param id The tileset specific ID (i.e. not the global one)
		 * @return The index of the tile on the y-axis
		 */
		public int getTileY(int id) {
			return id / tilesAcross;
		}

		/**
		 * Check if this tileset contains a particular tile
		 * 
		 * @param gid The global id to seach for
		 * @return True if the ID is contained in this tileset
		 */
		public boolean contains(int gid) {
			return gid >= firstGID && gid <= lastGID;
		}
	}
}
