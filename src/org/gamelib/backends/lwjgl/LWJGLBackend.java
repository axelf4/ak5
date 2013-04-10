/**
 * 
 */
package org.gamelib.backends.lwjgl;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;

import org.gamelib.DisplayMode;
import org.gamelib.Game;
import org.gamelib.Graphics;
import org.gamelib.Input;
import org.gamelib.backends.Backend;
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

	private class LWJGLGraphics implements Graphics {

		/** The current color */
		private Color currentColor = Color.white;

		/* (non-Javadoc)
		 * 
		 * @see org.gamelib.Graphics#setColor(java.awt.Color) */
		@Override
		public void setColor(Color c) {
			this.currentColor = c;
			GL11.glColor3f(currentColor.getRed() / 255, currentColor.getGreen() / 255, currentColor.getBlue() / 255);
		}

		/* (non-Javadoc)
		 * 
		 * @see org.gamelib.Graphics#drawImage(java.awt.Image, int, int, int,
		 * int, int, int, int, int) */
		@Override
		public void drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2) {
			// TODO Auto-generated method stub

		}

		/* (non-Javadoc)
		 * 
		 * @see org.gamelib.Graphics#drawLine(int, int, int, int) */
		@Override
		public void drawLine(int x1, int y1, int x2, int y2) {
			GL11.glColor3f(currentColor.getRed() / 255, currentColor.getGreen() / 255, currentColor.getBlue() / 255);
			GL11.glBegin(GL11.GL_LINE_STRIP);

			GL11.glVertex2d(x1, y1);
			GL11.glVertex2d(x2, y2);
			GL11.glEnd();
		}

		/* (non-Javadoc)
		 * 
		 * @see org.gamelib.Graphics#drawRect(int, int, int, int) */
		@Override
		public void drawRect(int x, int y, int width, int height) {
			drawLine(x, y, x + width, y);
			drawLine(x + width, y, x + width, y + height);
			drawLine(x + width, y + height, x, y + height);
			drawLine(x, y + height, x, y);
		}

		/* (non-Javadoc)
		 * 
		 * @see org.gamelib.Graphics#fillRect(int, int, int, int) */
		@Override
		public void fillRect(int x, int y, int width, int height) {
			GL11.glColor3f(currentColor.getRed() / 255, currentColor.getGreen() / 255, currentColor.getBlue() / 255);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(x, y);
			GL11.glVertex2f(x + width, y);
			GL11.glVertex2f(x + width, y + height);
			GL11.glVertex2f(x, y + height);
			GL11.glEnd();
		}

		/* (non-Javadoc)
		 * 
		 * @see org.gamelib.Graphics#drawString(java.lang.String, int, int) */
		@Override
		public void drawString(String str, int x, int y) {
			// TODO Auto-generated method stub

		}
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
			switch (keyCode) {
			case Keyboard.KEY_W:
				return Input.Key.VK_W;
			case Keyboard.KEY_A:
				return Input.Key.VK_A;
			case Keyboard.KEY_S:
				return Input.Key.VK_S;
			case Keyboard.KEY_D:
				return Input.Key.VK_D;
			case Keyboard.KEY_LEFT:
				return Input.Key.VK_LEFT;
			case Keyboard.KEY_RIGHT:
				return Input.Key.VK_RIGHT;
			case Keyboard.KEY_UP:
				return Input.Key.VK_UP;
			case Keyboard.KEY_DOWN:
				return Input.Key.VK_DOWN;
			case Keyboard.KEY_SPACE:
				return Input.Key.VK_SPACE;

			default:
				return -1;
			}
		}
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.backends.Backend#screenUpdate() */
	@Override
	public void screenUpdate() {
		// Clear the screen and depth buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
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

}
