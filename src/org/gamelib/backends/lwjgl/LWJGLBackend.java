/**
 * 
 */
package org.gamelib.backends.lwjgl;

import java.awt.Point;

import org.gamelib.DisplayMode;
import org.gamelib.Game;
import org.gamelib.Input;
import org.gamelib.backends.Backend;
import org.gamelib.graphics.Graphics;
import org.gamelib.graphics.Image;
import org.gamelib.resource.FileLoader;
import org.gamelib.util.Log;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

/**
 * @author pwnedary
 * 
 */
public class LWJGLBackend extends Backend {

	private LWJGLGraphics graphics;

	public LWJGLBackend() {
		FileLoader.addFileParser(new LWJGLImageFileParser());
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.backends.Backend#start(org.gamelib.Game,
	 * org.gamelib.DisplayMode) */
	@Override
	public void start(Game instance, DisplayMode mode) {
		try {
			// Display.setDisplayModeAndFullscreen(convertModes(mode));
			Display.setDisplayMode(new org.lwjgl.opengl.DisplayMode(800, 600));
			Display.create();

			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			// GL11.glOrtho(0, mode.getWidth(), 0, mode.getHeight(), 1, -1);
			GL11.glOrtho(0, mode.getWidth(), mode.getHeight(), 0, -1, 1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
		} catch (LWJGLException e) {
			Log.error("", e);
		}
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.backends.Backend#getGraphics() */
	@Override
	public Graphics getGraphics() {
		return graphics == null ? graphics = new LWJGLGraphics() : graphics;
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.backends.Backend#getInput() */
	@Override
	public Input getInput() {
		return new LWJGLInput();
	}

	private class LWJGLInput extends Input {

		/* (non-Javadoc)
		 * 
		 * @see org.gamelib.Input#poll() */
		@Override
		public void poll() {
			while (Keyboard.next())
				// pressedKeys.put(Keyboard.getEventKey(),
				// Keyboard.getEventKeyState());
				keyEvent(Keyboard.getEventKeyState() ? KEY_PRESSED : KEY_RELEASED, translateKeyCode(Keyboard.getEventKey()));
			// mousePosition.setLocation(org.lwjgl.input.Mouse.getX(),
			// org.lwjgl.input.Mouse.getY());
			Point p = new Point(org.lwjgl.input.Mouse.getX(), Display.getHeight() - org.lwjgl.input.Mouse.getY());
			while (org.lwjgl.input.Mouse.next()) {
				boolean pressed = org.lwjgl.input.Mouse.getEventButtonState();
				int button = org.lwjgl.input.Mouse.getEventButton();
				if (org.lwjgl.input.Mouse.getEventDX() != 0 || org.lwjgl.input.Mouse.getEventDY() != 0) // moved
				mouseEvent(pressed ? MOUSE_DRAGGED : MOUSE_MOVED, button, p);
				else {
					mouseEvent(pressed ? MOUSE_PRESSED : MOUSE_RELEASED, button, p);
					if (!pressed) {
						mouseEvent(MOUSE_CLICKED, button, p);
					}
				}
			}
		}

		/* (non-Javadoc)
		 * 
		 * @see org.gamelib.Input#translateBackendKeyCode(int) */
		@Override
		public int translateKeyCode(int keyCode) {
			return keyCode;
		}
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.backends.Backend#screenUpdate() */
	@Override
	public void screenUpdate() {
		// Clear the screen and depth buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		Game.getInstance().screen.drawHandlers(Game.getBackend().getGraphics());
		Display.update();
	}

	public org.lwjgl.opengl.DisplayMode convertModes(DisplayMode mode) {
		return new org.lwjgl.opengl.DisplayMode(mode.getWidth(), mode.getHeight());
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.backends.Backend#getTime() */
	@Override
	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.backends.Backend#shouldClose() */
	@Override
	public boolean shouldClose() {
		return Display.isCloseRequested();
	}

	/* (non-Javadoc)
	 * 
	 * @see java.lang.Object#finalize() */
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		Display.destroy();
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.backends.Backend#setTitle(java.lang.String) */
	@Override
	public void setTitle(String s) {
		Display.setTitle(s);
	}

	/* (non-Javadoc)
	 * @see org.gamelib.backends.Backend#getGraphics(org.gamelib.graphics.Image)
	 */
	@Override
	public Graphics getGraphics(Image img) {
		return new FBOGraphics((LWJGLImage)img);
	}

}
