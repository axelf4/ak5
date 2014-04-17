/**
 * 
 */
package org.gamelib.backend;

import org.gamelib.util.Configuration;
import org.gamelib.util.VariableConfiguration;

/**
 * @author pwnedary
 */
public class DisplayConfiguration extends VariableConfiguration.VariableConfigurationImpl implements Configuration, VariableConfiguration {
	/* The different keys. */
	public static final String WIDTH = "width", HEIGHT = "height",
			FULLSCREEN = "fullscreen", RESIZABLE = "resizable",
			TITLE = "title";

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
			case WIDTH:
				return (T) Integer.valueOf(width);
			case HEIGHT:
				return (T) Integer.valueOf(height);
			case FULLSCREEN:
				return (T) Boolean.valueOf(fullscreen);
			case RESIZABLE:
				return (T) Boolean.valueOf(resizable);
			case TITLE:
				return (T) title;
			}
		}
		return super.getProperty(key, def);
	}

	@Override
	public <T> T setProperty(Object key, T value) {
		if (key instanceof String) {
			switch ((String) key) {
			case WIDTH:
				width = (Integer) value;
				break;
			case HEIGHT:
				height = (Integer) value;
				break;
			case FULLSCREEN:
				fullscreen = (Boolean) value;
				break;
			case RESIZABLE:
				resizable = (Boolean) value;
				break;
			case TITLE:
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
		return string.equals(WIDTH) && string.equals(HEIGHT) && string.equals(FULLSCREEN) && string.equals(RESIZABLE);
	}

	@Override
	public void flush() {}

	/**
	 * Returns the width of the canvas.
	 * 
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
	 * 
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
	 * 
	 * @return if the window is resizable
	 */
	public boolean resizable() {
		return resizable;
	}

	public void setResizable(boolean resizable) {
		this.resizable = resizable;
	}

}
