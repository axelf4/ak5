/**
 * 
 */
package org.gamelib.backend.gwt;

import org.gamelib.util.Configuration;
import org.gamelib.util.slow.reflection.NativeType;

/** @author pwnedary */
public class GWTConfiguration implements Configuration {
	/* The different keys. */
	public static final String WIDTH = "width", HEIGHT = "height",
			FULLSCREEN = "fullscreen", RESIZABLE = "resizable",
			TITLE = "title";
	public int width;
	public int height;
	public boolean fullscreen = false;
	public boolean resizable = false;
	public String title = "";

	public GWTConfiguration(int width, int height) {
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
		return (T) (def.length == 0 ? new NativeType<>(def.getClass().getComponentType()).getDefaultValue() : (def.length == 1 ? def[0] : def));
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
}
