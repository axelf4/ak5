/**
 * 
 */
package org.gamelib.backend.lwjgl;

import static org.lwjgl.opengl.GL11.*;

import org.gamelib.Drawable;
import org.gamelib.Game;
import org.gamelib.Input;
import org.gamelib.Resolution;
import org.gamelib.backend.Backend;
import org.gamelib.backend.BackendImpl;
import org.gamelib.backend.Graphics;
import org.gamelib.backend.Image;
import org.gamelib.backend.ResourceFactory;
import org.gamelib.util.Color;
import org.gamelib.util.Log;
import org.gamelib.util.geom.Rectangle;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.Pbuffer;
import org.lwjgl.util.glu.GLU;

/**
 * @author pwnedary
 */
public class LWJGLBackend extends BackendImpl implements Backend {

	private LWJGLGraphics graphics;
	LWJGLResourceFactory resourceFactory;

	static void init2d(int width, int height) {
		GL11.glMatrixMode(GL11.GL_PROJECTION); // resets any previous projection matrices
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, 1, -1); // 0,0:top-left
		// GL11.glOrtho(0, width, 0, height, 1, -1); // 0,0:bottom-left
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		// GL11.glDisable(GL11.GL_DEPTH_TEST);
		glViewport(0, 0, width, height);
	}

	static void init3d(int width, int height) {
		GL11.glMatrixMode(GL11.GL_PROJECTION); // resets any previous projection matrices
		GL11.glLoadIdentity();
		GLU.gluPerspective(45.0f, (float) width / (float) height, 0.1f, 100.0f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glClearDepth(1.0f);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backends.Backend#getGraphics()
	 */
	@Override
	public Graphics getGraphics() {
		return graphics == null ? graphics = new LWJGLGraphics() : graphics;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backends.Backend#getInput()
	 */
	@Override
	public Input getInput() {
		return new LWJGLInput();
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backends.Backend#screenUpdate()
	 */
	@Override
	public void screenUpdate(Drawable callback, float delta) {
		Graphics g = getGraphics();
		g.setColor(Color.WHITE);
		g.clear();
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		// Game2.getInstance().screen.drawHandlers(getGraphics(), delta);
		callback.draw(g, delta);
		Display.update();
	}

	public org.lwjgl.opengl.DisplayMode convertModes(DisplayMode mode) {
		return new org.lwjgl.opengl.DisplayMode(mode.getWidth(), mode.getHeight());
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backends.Backend#getTime()
	 */
	@Override
	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backends.Backend#shouldClose()
	 */
	@Override
	public boolean shouldClose() {
		return Display.isCloseRequested() || super.shouldClose();
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backends.Backend#setTitle(java.lang.String)
	 */
	@Override
	public void setTitle(String s) {
		Display.setTitle(s);
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backends.Backend#getGraphics(org.gamelib.graphics.Image)
	 */
	@Override
	public Graphics getGraphics(Image image) {
		if (GLContext.getCapabilities().GL_EXT_framebuffer_object) return new FBOGraphics((LWJGLImage) image);
		else if ((Pbuffer.getCapabilities() & Pbuffer.PBUFFER_SUPPORTED) != 0) return new PbufferGraphics((LWJGLImage) image);
		else throw new Error("Your OpenGL card doesn't support offscreen buffers.");
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backend.Backend#getResourceFactory()
	 */
	@Override
	public ResourceFactory getResourceFactory() {
		return resourceFactory == null ? resourceFactory = new LWJGLResourceFactory() : resourceFactory;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		Display.destroy();
		if (resourceFactory != null) resourceFactory.destroy();
	}

	@Override
	public Rectangle getSize() {
		return new Rectangle(Display.getX(), Display.getY(), Display.getWidth(), Display.getHeight());
	}

	/** {@inheritDoc} */
	@Override
	public void start(Game game) {
		try {
			Resolution resolution = game.getResolution();
			org.lwjgl.opengl.DisplayMode targetDisplayMode = null;
			if (resolution.fullscreen()) {
				org.lwjgl.opengl.DisplayMode[] modes = Display.getAvailableDisplayModes();
				int freq = 0;

				for (int i = 0; i < modes.length; i++) {
					org.lwjgl.opengl.DisplayMode current = modes[i];

					if ((current.getWidth() == resolution.getWidth()) && (current.getHeight() == resolution.getHeight())) {
						if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
							if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
								targetDisplayMode = current;
								freq = targetDisplayMode.getFrequency();
							}
						}
						// if we've found a match for bpp and frequency against the original display mode then it's probably best to go for this one since it's most likely compatible with the monitor
						if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) && (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
							targetDisplayMode = current;
							break;
						}
					}
				}
			} else targetDisplayMode = new org.lwjgl.opengl.DisplayMode(resolution.getWidth(), resolution.getHeight());
			/*
			 * if (targetDisplayMode == null) return false;
			 */

			Display.setDisplayMode(targetDisplayMode);
			Display.setFullscreen(resolution.fullscreen());
			// Display.setDisplayMode(new DisplayMode(800, 600));
			Display.setVSyncEnabled(true);
			Display.create();

			/*
			 * GL11.glMatrixMode(GL11.GL_PROJECTION); // Resets any previous projection matrices GL11.glLoadIdentity(); GL11.glOrtho(0, resolution.getWidth(), resolution.getHeight(), 0, 1, -1); // 0,0-top-left // GL11.glOrtho(0, resolution.getWidth(), 0, resolution.getHeight(), 1, -1); // 0,0-bottom-left GL11.glMatrixMode(GL11.GL_MODELVIEW); // glViewport(0, 0, resolution.getWidth(), resolution.getHeight());
			 */
			
			super.start(game);
		} catch (LWJGLException e) {
			Log.error("", e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public int getWidth() {
		return Display.getWidth();
	}

	/** {@inheritDoc} */
	@Override
	public int getHeight() {
		return Display.getHeight();
	}

}
