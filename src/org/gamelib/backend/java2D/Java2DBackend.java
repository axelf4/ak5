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
 * The {@link Backend} using Java2D for rendering and resources.
 * @author pwnedary
 */
public class Java2DBackend extends BackendImpl implements Backend {

	private Container container;
	private Java2DPanel panel;
	Java2DResourceFactory resourceFactory;

	private Java2DGraphics graphics;

	public Java2DBackend(Container container) {
		(this.container = container).add(panel = new Java2DPanel());
	}

	void setFullscreen(JFrame frame, boolean fullscreen) {
		GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		frame.setUndecorated(fullscreen);
		frame.setResizable(!fullscreen);
		if (fullscreen) try {
			graphicsDevice.setFullScreenWindow(frame); // Enter full-screen mode
			frame.validate();
		} finally {
			// graphicsDevice.setFullScreenWindow(null); // Exit full-screen mode
		}
		else {
			graphicsDevice.setFullScreenWindow(null);
			frame.setVisible(true);
		}
	}

	/** {@inheritDoc} */
	@Override
	public Graphics getGraphics() {
		return graphics == null && true ? graphics = new Java2DGraphics(panel.g2d, panel.getWidth(), panel.getHeight()) : graphics;
	}

	public Input getInput() {
		return new Java2DInput(panel);
	}

	/** {@inheritDoc} */
	@Override
	public void draw(Drawable callback, float delta) {
		panel.delta = delta;
		panel.callback = callback;
		panel.repaint();
	}

	/** {@inheritDoc} */
	@Override
	public long getTime() {
		return System.nanoTime() / 1000000;
	}

	/** {@inheritDoc} */
	@Override
	public boolean shouldClose() {
		return false || super.shouldClose();
	}

	/** {@inheritDoc} */
	@Override
	public void setTitle(String s) {
		if (container instanceof JFrame) ((JFrame) container).setTitle(s);
	}

	/** {@inheritDoc} */
	@Override
	public Graphics getGraphics(Image img) {
		return new Java2DGraphics(((Java2DImage) img).bufferedImage.getGraphics(), img.getWidth(), img.getHeight());
	}

	/** {@inheritDoc} */
	@Override
	public ResourceFactory getResourceFactory() {
		return resourceFactory == null ? resourceFactory = new Java2DResourceFactory(panel) : resourceFactory;
	}

	/** {@inheritDoc} */
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

		super.start(game); // let BackendImpl initialize Game

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
