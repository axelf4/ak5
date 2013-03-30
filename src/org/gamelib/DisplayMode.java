/**
 * 
 */
package org.gamelib;

import java.beans.ConstructorProperties;

/**
 * @author Axel Color
 */
public class DisplayMode {
	
	public static final DisplayMode r800x600 = new DisplayMode(800, 600);
	public static final DisplayMode FULLSCREEN = new DisplayMode(true);

	/** The width of the screen. */
	int width;
	/** The height of the screen. */
	int height;

	/** Whether fullscreen. */
	boolean fullscreen;

	/**
	 * 
	 */
	@ConstructorProperties({ "width", "height" })
	public DisplayMode(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	/**
	 * 
	 */
	@ConstructorProperties({ "fullscreen" })
	public DisplayMode(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}

}
