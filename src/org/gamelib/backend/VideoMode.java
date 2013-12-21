/**
 * 
 */
package org.gamelib.backend;

import java.beans.ConstructorProperties;

/**
 * The dimensions of the screen and whether fullscreen or not.
 * @author pwnedary
 * @deprecated replaced by {@link Configuration}
 */
@Deprecated
public class VideoMode {
	public static final VideoMode WINDOW_800X600 = new VideoMode(800, 600, false);
	public static final VideoMode FULLSCREEN_1366X768 = new VideoMode(1366, 768, true);

	/** The width of the screen */
	public final int width;
	/** The height of the screen */
	public final int height;
	/** Whether fullscreen */
	public final boolean fullscreen;
	/** Whether to use vsync */
	public boolean vsync = false;
	/** If using window; allow it to be resized */
	public boolean resizable = true;

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
