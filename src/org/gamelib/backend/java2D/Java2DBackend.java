/**
 * 
 */
package org.gamelib.backend.java2D;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JFrame;

import org.gamelib.DisplayMode;
import org.gamelib.Game;
import org.gamelib.Input;
import org.gamelib.backend.Backend;
import org.gamelib.backend.Graphics;
import org.gamelib.backend.Image;
import org.gamelib.backend.ResourceFactory;
import org.gamelib.backend.Sound;

/**
 * change to AWTBackend
 * 
 * @author pwnedary
 */
public class Java2DBackend implements Backend {

	private Container container;
	private Java2dPanel panel;

	private Java2DGraphics graphics;

	/**
	 * 
	 */
	public Java2DBackend(Container container) {
		(this.container = container).add(panel = new Java2dPanel());
	}

	public void start(Game instance, DisplayMode mode) {
		if (container instanceof JFrame) {
			((JFrame) container).setTitle(instance.toString());
			((JFrame) container).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setFullscreen((JFrame) container, mode.isFullscreen());
			if (!mode.isFullscreen())
				container.setSize(new Dimension(mode.getWidth(), mode.getHeight()));
		}
		if (container instanceof JApplet)
			((JApplet) container).resize(new Dimension(mode.getWidth(), mode.getHeight()));
	}

	private void setFullscreen(JFrame frame, boolean fullscreen) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice graphicsDevice = ge.getDefaultScreenDevice();
		frame.setUndecorated(fullscreen);
		frame.setResizable(!fullscreen);
		if (fullscreen) {
			// Determine if full-screen mode is supported directly
			if (graphicsDevice.isFullScreenSupported()) {
				// Full-screen mode is supported
			} else {
				// Full-screen mode will be simulated
			}

			try {
				// Enter full-screen mode
				// gs.setFullScreenWindow(win);
				graphicsDevice.setFullScreenWindow(frame);
				frame.validate();
				// ...
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
		return graphics == null ? graphics = new Java2DGraphics(panel.g2d, panel.getWidth(), panel.getHeight()) : graphics;
	}

	public Input getInput() {
		return new Java2DInput(panel);
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backends.Backend#screenUpdate()
	 */
	@Override
	public void screenUpdate(float delta) {
		panel.delta = delta;
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
		if (container instanceof JFrame)
			((JFrame) container).setTitle(s);
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
	 * @see org.gamelib.backends.Backend#createImage(int, int)
	 */
	@Override
	public Image createImage(int width, int height) {
		// return new Java2DImage(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
		GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		return new Java2DImage(config.createCompatibleImage(width, height, Transparency.TRANSLUCENT));
	}

	/* (non-Javadoc)
	 * @see org.gamelib.backend.Backend#getImage(java.io.File)
	 */
	@Override
	public Image getImage(File file) throws IOException {
		return new Java2DImage(ImageIO.read(file));
	}

	/* (non-Javadoc)
	 * @see org.gamelib.backend.Backend#getSound(java.io.File)
	 */
	@Override
	public Sound getSound(File file) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.gamelib.backend.Backend#getResourceFactory()
	 */
	@Override
	public ResourceFactory getResourceFactory() {
		return new Java2DResourceFactory();
	}
}
