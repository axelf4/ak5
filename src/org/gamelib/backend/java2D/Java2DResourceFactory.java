/**
 * 
 */
package org.gamelib.backend.java2D;

import java.awt.Component;
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

import org.gamelib.backend.Image;
import org.gamelib.backend.ResourceFactory;
import org.gamelib.backend.Sound;

/**
 * @author pwnedary
 */
public class Java2DResourceFactory implements ResourceFactory {

	MediaTracker tracker;
	/** The ids used by {@link #tracker} */
	int nextAvailableId = 0;

	/**
	 * 
	 */
	public Java2DResourceFactory(Component comp) {
		this.tracker = new MediaTracker(comp);
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
		Clip clip = null;
		Java2DSound sound = null;
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(getResourceAsStream(file.getPath()));
			// load the sound into memory (a Clip)
			clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class, stream.getFormat()));
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
