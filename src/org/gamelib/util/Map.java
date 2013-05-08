/**
 * 
 */
package org.gamelib.util;

import java.io.Serializable;

import org.gamelib.backend.Graphics;

/**
 * A map intended to be parsed from an external map editor.
 * 
 * @see TileED TileED map editor <a href="http://mapeditor.org/">http://mapeditor.org/</a>
 * @author pwnedary
 */
public interface Map extends Serializable {
	/** Indicates a orthogonal map */
	public static final int ORTHOGONAL = 0;
	/** Indicates an isometric map */
	public static final int ISOMETRIC = 1;

	/**
	 * Gets the ID of the tile at the specified location in this layer
	 * 
	 * @param x The x coordinate of the tile
	 * @param y The y coordinate of the tile
	 * @return The ID of the tile
	 */
	public int getTileID(int layer, int x, int y);

	/**
	 * Sets the tile ID at the specified location
	 * 
	 * @param x The x location to set
	 * @param y The y location to set
	 * @param tile The tile value to set
	 */
	public void setTileID(int layer, int x, int y, int tile);

	/**
	 * Draws the selected region of the map.
	 * 
	 * @param g2d the {@link Graphics} object
	 * @param rx region start x
	 * @param ry region start y
	 * @param rw region width
	 * @param rh region height
	 */
	public void draw(Graphics g, int rx, int ry, int rw, int rh);

	/**
	 * Draws the the map.
	 * 
	 * @param g2d the {@link Graphics} object
	 */
	public void draw(Graphics g);
}
