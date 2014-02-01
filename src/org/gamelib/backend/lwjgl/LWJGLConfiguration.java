/**
 * 
 */
package org.gamelib.backend.lwjgl;

import org.gamelib.backend.DisplayConfiguration;

/**
 * @author pwnedary
 */
public class LWJGLConfiguration extends DisplayConfiguration {
	public static final String VSYNC_KEY = "vsync",
			ORIGIN_BOTTOM_LEFT_KEY = "originBottomLeft";
	public boolean vsync = false;
	public boolean originBottomLeft = false;

	public LWJGLConfiguration(int width, int height) {
		super(width, height);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getProperty(Object key, T... def) {
		if (key instanceof String) {
			switch ((String) key) {
			case VSYNC_KEY:
				return (T) Boolean.valueOf(vsync);
			case ORIGIN_BOTTOM_LEFT_KEY:
				return (T) Boolean.valueOf(originBottomLeft);
			}
		}
		return super.getProperty(key, def);
	}

	@Override
	public <T> T setProperty(Object key, T value) {
		if (key instanceof String) {
			switch ((String) key) {
			case VSYNC_KEY:
				vsync = (Boolean) value;
				break;
			case ORIGIN_BOTTOM_LEFT_KEY:
				originBottomLeft = (Boolean) value;
				break;
			}
		}
		return super.setProperty(key, value);
	}

	@Override
	public boolean hasProperty(Object key) {
		if (!(key instanceof String)) return false;
		String string = (String) key;
		return (string.equals(VSYNC_KEY) && string.equals(ORIGIN_BOTTOM_LEFT_KEY)) || super.hasProperty(string);
	}

	/** If should use vsync */
	public boolean vsync() {
		return vsync;
	}

	public LWJGLConfiguration setVsync(boolean vsync) {
		this.vsync = vsync;
		return this;
	}

	public boolean originBottomLeft() {
		return originBottomLeft;
	}

	public LWJGLConfiguration setOriginBottomLeft(boolean originBottomLeft) {
		this.originBottomLeft = originBottomLeft;
		return this;
	}

	@Override
	public void flush() {
		super.flush();
		setProperty(VSYNC_KEY, vsync());
		setProperty(ORIGIN_BOTTOM_LEFT_KEY, originBottomLeft());
	}
}
