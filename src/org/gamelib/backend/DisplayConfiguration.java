/**
 * 
 */
package org.gamelib.backend;

import org.gamelib.util.Configuration;
import org.gamelib.util.Configuration.VariableConfiguration;

/**
 * @author pwnedary
 */
public class DisplayConfiguration extends Configuration.VariableConfigurationImpl implements Configuration, VariableConfiguration {
	public static final String WIDTH_KEY = "width", HEIGHT_KEY = "height",
			FULLSCREEN_KEY = "fullscreen", RESIZABLE_KEY = "resizable",
			TITLE_KEY = "title";

	public int width;
	public int height;
	public boolean fullscreen = false;
	public boolean resizable = false;
	public String title = "";

	public DisplayConfiguration(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getProperty(Object key, T... def) {
		if (key instanceof String) {
			switch ((String) key) {
			case WIDTH_KEY:
				return (T) Integer.valueOf(width);
			case HEIGHT_KEY:
				return (T) Integer.valueOf(height);
			case FULLSCREEN_KEY:
				return (T) Boolean.valueOf(fullscreen);
			case RESIZABLE_KEY:
				return (T) Boolean.valueOf(resizable);
			case TITLE_KEY:
				return (T) title;
			}
		}
		throw new IllegalArgumentException("No such key: " + key);
	}

	@Override
	public <T> T setProperty(Object key, T value) {
		if (key instanceof String) {
			switch ((String) key) {
			case WIDTH_KEY:
				width = (Integer) value;
				break;
			case HEIGHT_KEY:
				height = (Integer) value;
				break;
			case FULLSCREEN_KEY:
				fullscreen = (Boolean) value;
				break;
			case RESIZABLE_KEY:
				resizable = (Boolean) value;
				break;
			case TITLE_KEY:
				title = (String) value;
				break;
			default:
				throw new IllegalArgumentException("No such key: " + key);
			}
		}
		return null;
	}

	@Override
	public boolean hasProperty(Object key) {
		if (!(key instanceof String)) return false;
		String string = (String) key;
		return string.equals(WIDTH_KEY) && string.equals(HEIGHT_KEY) && string.equals(FULLSCREEN_KEY) && string.equals(RESIZABLE_KEY);
	}

	@Override
	public void flush() {}

	/**
	 * Returns the width of the canvas.
	 * @return the canvas's width
	 */
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Returns the height of the canvas.
	 * @return the canvas's height
	 */
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean fullscreen() {
		return fullscreen;
	}

	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}

	/**
	 * Returns, if using a window, allowing it to be resized.
	 * @return if the window is resizable
	 */
	public boolean resizable() {
		return resizable;
	}

	public void setResizable(boolean resizable) {
		this.resizable = resizable;
	}

}
