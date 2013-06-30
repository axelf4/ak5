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
import org.gamelib.Resolution;
import org.gamelib.backend.Backend;
import org.gamelib.backend.Graphics;
import org.gamelib.backend.Image;
import org.gamelib.backend.ResourceFactory;
import org.gamelib.util.geom.Rectangle;

/**
 * change to AWTBackend
 * @author pwnedary
 */
public class Java2DBackend implements Backend {

	private Container container;
	private Java2dPanel panel;
	Java2DResourceFactory resourceFactory;

	private Java2DGraphics graphics;

	/**
	 * 
	 */
	public Java2DBackend(Container container) {
		(this.container = container).add(panel = new Java2dPanel());
	}

	public void start(Game instance, Resolution resolution) {
		if (container instanceof JFrame) {
			((JFrame) container).setTitle(instance.toString());
			((JFrame) container).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setFullscreen((JFrame) container, resolution.fullscreen());
			if (!resolution.fullscreen())
				container.setSize(new Dimension(resolution.getWidth(), resolution.getHeight()));
		}
		if (container instanceof JApplet)
			((JApplet) container).resize(new Dimension(resolution.getWidth(), resolution.getHeight()));
	}

	private void setFullscreen(JFrame frame, boolean fullscreen) {
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
		return false;
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
		return resourceFactory == null ? resourceFactory = new Java2DResourceFactory() : resourceFactory;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public Rectangle getSize() {
		return new Rectangle(panel.getX(), panel.getY(), panel.getWidth(), panel.getHeight());
	}
}
