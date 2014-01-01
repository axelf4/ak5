/**
 * 
 */
package org.gamelib.ui;

/**
 * @author Axel
 */
public enum Align {
	/***/
	LEFT(0, 1),
	/***/
	RIGHT(2, 1),
	/***/
	TOP(1, 0),
	/***/
	BOTTOM(1, 2),
	/***/
	TOPLEFT(0, 0),
	/***/
	TOPRIGHT(2, 0),
	/***/
	BOTTOMLEFT(0, 2),
	/***/
	BOTTOMRIGHT(2, 2),
	/***/
	CENTER(1, 1),
	/***/
	FILL(1, 1);

	final byte hpos;
	final byte vpos;

	private Align(int hpos, int vpos) {
		this.hpos = (byte) hpos;
		this.vpos = (byte) vpos;
	}
}
