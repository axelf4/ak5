/**
 * 
 */
package ak5.backend.lwjgl;

import ak5.util.Configuration;
import ak5.util.VariableConfigurationImpl;

/** @author pwnedary */
public class LWJGLConfiguration extends VariableConfigurationImpl implements Configuration {
	/* The different keys. */
	public static final String WIDTH = "width", HEIGHT = "height",
			FULLSCREEN = "fullscreen", RESIZABLE = "resizable",
			TITLE = "title", VSYNC = "vsync";

	public int width;
	public int height;
	public boolean fullscreen;
	public boolean resizable;
	public String title;
	public boolean vsync;

	public LWJGLConfiguration() {
		this.width = 800;
		this.height = 600;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getProperty(Object key, T... def) {
		if (key instanceof String) switch ((String) key) {
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
		case VSYNC:
			return (T) Boolean.valueOf(vsync);
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
			case VSYNC:
				vsync = (Boolean) value;
				break;
			default:
				return super.setProperty(key, value);
			}
			return null;
		}
		return super.setProperty(key, value);
	}

	@Override
	public boolean hasProperty(Object key) {
		return key instanceof String && ((String) key).equals(VSYNC) || super.hasProperty(key);
	}

	/** If should use vsync */
	public boolean vsync() {
		return vsync;
	}

	public LWJGLConfiguration setVsync(boolean vsync) {
		this.vsync = vsync;
		return this;
	}
}
