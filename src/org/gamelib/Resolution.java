/**
 * 
 */
package org.gamelib;

import java.beans.ConstructorProperties;

/**
 * @author pwnedary
 *
 */
public class Resolution {
	
	public static final Resolution r800x600 = new Resolution(800, 600);
	public static final Resolution r800x600F = new Resolution(800, 600, true);
	public static final Resolution FULLSCREEN = new Resolution(true);
	
	/** The width of the screen. */
	final int width;
	/** The height of the screen. */
	final int height;

	/** Whether fullscreen. */
	final boolean fullscreen;
	
	/**
	 * 
	 */
	@ConstructorProperties({ "width", "height" })
	public Resolution(int width, int height, boolean fullscreen) {
		this.width = width;
		this.height = height;
		this.fullscreen = fullscreen;
	}

	/**
	 * 
	 */
	public Resolution(int width, int height) {
		this(width, height, false);
	}
	
	/**
	 * 
	 */
	public Resolution(boolean fullscreen) {
		this.width = -1;
		this.height = -1;
		this.fullscreen = true;
	}
	
	public boolean fullscreen() {
		return fullscreen;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

}
