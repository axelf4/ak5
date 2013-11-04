/**
 * 
 */
package org.gamelib.backend.java2D;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MediaTracker;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
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
import org.gamelib.backend.Sound;
import org.gamelib.util.geom.Rectangle;

/**
 * The {@link Backend} using Java2D for rendering and resources.
 * @author pwnedary
 */
public class Java2DBackend extends BackendImpl implements Backend {

	private Container container;
	private Java2DPanel panel;
	MediaTracker tracker;
	/** The ids used by {@link #tracker} */
	int nextAvailableId = 0;

	private Java2DGraphics graphics;

	public Java2DBackend(Container container) {
		(this.container = container).add(panel = new Java2DPanel());
		tracker = new MediaTracker(panel);
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
			tracker.waitForAll(); // wait for loading files
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

	/** {@inheritDoc} */
	@Override
	public InputStream getResourceAsStream(String name) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
	}

	/** {@inheritDoc} */
	@Override
	public Image getImage(File file) throws IOException {
		// return new Java2DImage(ImageIO.read(file));
		// return new Java2DImage(ImageIO.read(getResourceAsStream(file.getPath())));
		BufferedImage image = ImageIO.read(getResourceAsStream(file.getPath()));
		tracker.addImage(image, nextAvailableId++);
		return new Java2DImage(image);
	}

	/** {@inheritDoc} */
	@Override
	public Image createImage(int width, int height) {
		// return new Java2DImage(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
		return new Java2DImage(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT));
	}

	/** {@inheritDoc} */
	@Override
	public Sound getSound(File file) throws IOException {
		Java2DSound sound = null;
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(getResourceAsStream(file.getPath()));
			Clip clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class, stream.getFormat())); // load the sound into memory (a Clip)
			sound = new Java2DSound(clip);
			clip.open(stream);
		} catch (UnsupportedAudioFileException | LineUnavailableException e) {
			e.printStackTrace();
		}
		return sound;
	}

	/** {@inheritDoc} */
	@Override
	public Image getImage(BufferedImage img) {
		return new Java2DImage(img);
	}
}
