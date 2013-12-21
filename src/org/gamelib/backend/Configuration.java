/**
 * 
 */
package org.gamelib.backend;

/**
 * TODO fix hierarchy from like ConfigurationImpl to WindowConfiguration to Java2DConfiguration or LWJGLConfiguration TODO comment java-doc
 * @author pwnedary
 */
public interface Configuration {
	/** @return the canvas's width */
	int getWidth();

	/** @return the canvas's height */
	int getHeight();

	boolean fullscreen();

	/**
	 * Returns, if using a window; allowing it to be resized
	 * @return if the window is resizable
	 */
	boolean resizable();

	public static class WindowConfiguration implements Configuration {
		/** The width of the canvas */
		public final int width;
		/** The height of the canvas */
		public final int height;
		public boolean fullscreen = false;
		public boolean resizable = false;

		public WindowConfiguration(int width, int height) {
			this.width = width;
			this.height = height;
		}

		@Override
		public int getWidth() {
			return width;
		}

		@Override
		public int getHeight() {
			return height;
		}

		@Override
		public boolean fullscreen() {
			return fullscreen;
		}

		@Override
		public boolean resizable() {
			return resizable;
		}
	}
}
