/**
 * 
 */
package org.gamelib.util;

import java.awt.Graphics2D;
import java.awt.Image;

import org.gamelib.resource.ResourceLoader;
import org.w3c.dom.Element;

/**
 * TODO make more standard not to only use TilED.
 * @author pwnedary
 * 
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
	private int tileSpacing = 0;
	/** The margin of the tileset */
	private int tileMargin = 0;
	
	public TileSet() {
		
	}

	/**
	 * 
	 */
	public TileSet(Element element, String mapLocation) {
		name = element.getAttribute("name");
		firstGID = Integer.parseInt(element.getAttribute("firstgid"));

		tileWidth = Integer.parseInt(element.getAttribute("tilewidth"));
		tileHeight = Integer.parseInt(element.getAttribute("tileheight"));
		String spacing = element.getAttribute("spacing");
		tileSpacing = Integer.parseInt(spacing == "" || spacing == null ? "0" : spacing);
		String margin = element.getAttribute("spacing");
		tileMargin = Integer.parseInt(margin == "" || margin == null ? "0" : margin);

		Element imageNode = (Element) element.getElementsByTagName("image").item(0);
		tilesAcross = Integer.parseInt(imageNode.getAttribute("width")) / tileWidth;
		tilesDown = Integer.parseInt(imageNode.getAttribute("height")) / tileHeight;

		image = (Image) ResourceLoader.load(mapLocation + "/" + imageNode.getAttribute("source"));
	}

	public void draw(Graphics2D g2d, int gid, int x, int y) {
		int id = gid - firstGID;
		int sx = getTileX(id) * tileWidth;
		int sy = getTileY(id) * tileHeight;
		g2d.drawImage(image, x, y, x + tileWidth, y + tileHeight, sx, sy, sx + tileWidth, sy + tileHeight, null);
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
