/**
 * 
 */
package org.gamelib.backend.lwjgl;

import org.gamelib.backend.Configuration.WindowConfiguration;

/**
 * @author pwnedary
 */
public class LWJGLConfiguration extends WindowConfiguration {
	/** If should use vsync */
	public boolean vsync = false;
	public boolean originBottomLeft = false;

	public LWJGLConfiguration(int width, int height) {
		super(width, height);
	}

}
