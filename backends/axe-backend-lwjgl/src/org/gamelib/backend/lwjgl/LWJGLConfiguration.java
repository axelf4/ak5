/**
 * 
 */
package org.gamelib.backend.lwjgl;

import org.gamelib.backend.DisplayConfiguration;

/** @author pwnedary */
public class LWJGLConfiguration extends DisplayConfiguration {
	public static final String VSYNC = "vsync";
	public boolean vsync = false;

	public LWJGLConfiguration(int width, int height) {
		super(width, height);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getProperty(Object key, T... def) {
		if (key instanceof String) {
			switch ((String) key) {
			case VSYNC:
				return (T) Boolean.valueOf(vsync);
			}
		}
		return super.getProperty(key, def);
	}

	@Override
	public <T> T setProperty(Object key, T value) {
		if (key instanceof String) {
			switch ((String) key) {
			case VSYNC:
				vsync = (Boolean) value;
				break;
			}
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
