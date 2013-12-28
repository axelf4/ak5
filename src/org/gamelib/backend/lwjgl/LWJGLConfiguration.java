/**
 * 
 */
package org.gamelib.backend.lwjgl;

import org.gamelib.backend.Configuration.DisplayConfiguration;

/**
 * @author pwnedary
 */
public class LWJGLConfiguration extends DisplayConfiguration {
	public static final String VSYNC_KEY = "vsync",
			ORIGIN_BOTTOM_LEFT_KEY = "originBottomLeft";
	private boolean vsync = false;
	private boolean originBottomLeft = false;

	public LWJGLConfiguration(int width, int height) {
		super(width, height);
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
