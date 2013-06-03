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
import org.gamelib.backend.Graphics;
import org.gamelib.backend.Image;
import org.gamelib.backend.ResourceFactory;
import org.gamelib.util.Log;
import org.gamelib.util.geom.Rectangle;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.Pbuffer;

/**
 * @author pwnedary
 */
public class LWJGLBackend implements Backend {

	private LWJGLGraphics graphics;
	LWJGLResourceFactory resourceFactory;

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backends.Backend#start(org.gamelib.Game, org.gamelib.DisplayMode)
	 */
	@Override
	public void start(Game instance, Resolution resolution) {
		try {
			/*
			 * if (resolution.isFullscreen()) Display.setDisplayModeAndFullscreen(Display.getDesktopDisplayMode()); else Display.setDisplayMode(new DisplayMode(resolution.getWidth(), resolution.getHeight()));
			 */
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.create();

			GL11.glMatrixMode(GL11.GL_PROJECTION); // Resets any previous projection matrices
			GL11.glLoadIdentity();
			GL11.glOrtho(0, resolution.getWidth(), resolution.getHeight(), 0, 1, -1);
			// GL11.glOrtho(0, resolution.getWidth(), 0, resolution.getHeight(), 1, -1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			// glViewport(0, 0, resolution.getWidth(), resolution.getHeight());
		} catch (LWJGLException e) {
			Log.error("", e);
		}
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
		// Clear the screen and depth buffer
		glClearColor(1, 1, 1, 1);
		glClear(GL_COLOR_BUFFER_BIT);
		// GL11.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // 3d
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		// GL11.glEnable(GL11.GL_BLEND);
		// GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		// Game.getInstance().screen.drawHandlers(getGraphics(), delta);
		callback.draw(getGraphics(), delta);
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
		return Display.isCloseRequested();
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
		if (GLContext.getCapabilities().GL_EXT_framebuffer_object)
			return new FBOGraphics((LWJGLImage) image);
		else if ((Pbuffer.getCapabilities() & Pbuffer.PBUFFER_SUPPORTED) != 0)
			return new PbufferGraphics((LWJGLImage) image);
		else
			throw new Error("Your OpenGL card doesn't support offscreen buffers.");
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

}
