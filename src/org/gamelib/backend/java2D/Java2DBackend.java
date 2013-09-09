/**
 * 
 */
package org.gamelib.backend.java2D;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JApplet;
import javax.swing.JFrame;

import org.gamelib.Drawable;
import org.gamelib.Game;
import org.gamelib.Input;
import org.gamelib.VideoMode;
import org.gamelib.backend.Backend;
import org.gamelib.backend.BackendImpl;
import org.gamelib.backend.Graphics;
import org.gamelib.backend.Image;
import org.gamelib.backend.ResourceFactory;
import org.gamelib.util.geom.Rectangle;

/**
 * change to AWTBackend
 * @author pwnedary
 */
public class Java2DBackend extends BackendImpl implements Backend {

	private Container container;
	private Java2DPanel panel;
	Java2DResourceFactory resourceFactory;

	private Java2DGraphics graphics;

	/**
	 * 
	 */
	public Java2DBackend(Container container) {
		(this.container = container).add(panel = new Java2DPanel());
	}

	void setFullscreen(JFrame frame, boolean fullscreen) {
		GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		frame.setUndecorated(fullscreen);
		frame.setResizable(!fullscreen);
		if (fullscreen) {
			// Determine if full-screen mode is supported directly
			/*
			 * if (graphicsDevice.isFullScreenSupported()) { // Full-screen mode is supported } else { // Full-screen mode will be simulated }
			 */
			try {
				// Enter full-screen mode
				// gs.setFullScreenWindow(win);
				graphicsDevice.setFullScreenWindow(frame);
				frame.validate();
			} finally {
				// Exit full-screen mode
				// graphicsDevice.setFullScreenWindow(null);
			}
		} else {
			graphicsDevice.setFullScreenWindow(null);
			frame.setVisible(true);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backends.Backend#getGraphics()
	 */
	@Override
	public Graphics getGraphics() {
		return graphics == null && true ? graphics = new Java2DGraphics(panel.g2d, panel.getWidth(), panel.getHeight()) : graphics;
	}

	public Input getInput() {
		return new Java2DInput(panel);
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backends.Backend#screenUpdate()
	 */
	@Override
	public void screenUpdate(Drawable callback, float delta) {
		panel.delta = delta;
		panel.callback = callback;
		panel.repaint();
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backends.Backend#getTime()
	 */
	@Override
	public long getTime() {
		return System.nanoTime() / 1000000;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backends.Backend#shouldClose()
	 */
	@Override
	public boolean shouldClose() {
		return false || super.shouldClose();
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backends.Backend#setTitle(java.lang.String)
	 */
	@Override
	public void setTitle(String s) {
		if (container instanceof JFrame) ((JFrame) container).setTitle(s);
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backends.Backend#getGraphics(org.gamelib.graphics.Image)
	 */
	@Override
	public Graphics getGraphics(Image img) {
		return new Java2DGraphics(((Java2DImage) img).bufferedImage.getGraphics(), img.getWidth(), img.getHeight());
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backend.Backend#getResourceFactory()
	 */
	@Override
	public ResourceFactory getResourceFactory() {
		return resourceFactory == null ? resourceFactory = new Java2DResourceFactory(panel) : resourceFactory;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Destroyable#destroy()
	 */
	@Override
	public void destroy() {}

	@Override
	public Rectangle getSize() {
		return new Rectangle(panel.getX(), panel.getY(), panel.getWidth(), panel.getHeight());
	}

	/** {@inheritDoc} */
	@Override
	public void start(Game game) {
		VideoMode videoMode = game.getResolution();
		if (container instanceof JFrame) {
			((JFrame) container).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setFullscreen((JFrame) container, videoMode.fullscreen());
			if (!videoMode.fullscreen()) container.setSize(new Dimension(videoMode.getWidth(), videoMode.getHeight()));
		} else if (container instanceof JApplet) ((JApplet) container).resize(new Dimension(videoMode.getWidth(), videoMode.getHeight()));
		
		super.start(game); // let BackendImpl init Game
		
		try {
			((Java2DResourceFactory) getResourceFactory()).tracker.waitForAll(); // wait for loading files
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/** {@inheritDoc} */
	@Override
	public int getWidth() {
		return panel.getWidth();
	}

	/** {@inheritDoc} */
	@Override
	public int getHeight() {
		return panel.getHeight();
	}
}
