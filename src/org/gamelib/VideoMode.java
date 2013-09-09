/**
 * 
 */
package org.gamelib;

import java.beans.ConstructorProperties;

/**
 * The dimensions of the screen and wether fullscreen or not.
 * @author pwnedary
 */
public class VideoMode {

	public static final VideoMode WINDOW_800X600 = new VideoMode(800, 600, false);
	public static final VideoMode FULLSCREEN_1366X768 = new VideoMode(1366, 768, true);

	/** The width of the screen. */
	final int width;
	/** The height of the screen. */
	final int height;

	/** Whether fullscreen. */
	final boolean fullscreen;

	/**
	 * 
	 */
	@ConstructorProperties({ "width", "height", "fullscreen" })
	public VideoMode(int width, int height, boolean fullscreen) {
		this.width = width;
		this.height = height;
		this.fullscreen = fullscreen;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean fullscreen() {
		return fullscreen;
	}

}
