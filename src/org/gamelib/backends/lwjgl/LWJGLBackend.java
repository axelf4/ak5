/**
 * 
 */
package org.gamelib.backends.lwjgl;

import java.awt.Color;
import java.awt.Image;

import org.gamelib.DisplayMode;
import org.gamelib.Game;
import org.gamelib.Graphics;
import org.gamelib.Input;
import org.gamelib.backends.Backend;
import org.gamelib.util.Log;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
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
			Display.setDisplayMode(new org.lwjgl.opengl.DisplayMode(800,600));
			Display.create();
			
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0, 800, 0, 600, 1, -1);
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
		return null;
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
