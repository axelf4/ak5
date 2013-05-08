/**
 * 
 */
package org.gamelib.backend.java2D;

import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
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

import org.gamelib.Game;
import org.gamelib.backend.Image;
import org.gamelib.backend.ResourceFactory;
import org.gamelib.backend.Sound;

/**
 * @author pwnedary
 *
 */
public class Java2DResourceFactory implements ResourceFactory {

	@Override
	public InputStream getResourceAsStream(String name) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
	}

	/* (non-Javadoc)
	 * @see org.gamelib.backend.ResourceFactory#getImage(java.io.File)
	 */
	@Override
	public Image getImage(File file) throws IOException {
		// return new Java2DImage(ImageIO.read(file));
		return new Java2DImage(ImageIO.read(getResourceAsStream(file.getPath())));
	}

	/* (non-Javadoc)
	 * @see org.gamelib.backend.ResourceFactory#createImage(int, int)
	 */
	@Override
	public Image createImage(int width, int height) {
		// return new Java2DImage(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
		return new Java2DImage(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT));
	}

	/* (non-Javadoc)
	 * @see org.gamelib.backend.ResourceFactory#getSound(java.io.File)
	 */
	@Override
	public Sound getSound(File file) throws IOException {
		Clip clip = null;
		Java2DSound sound = null;
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(Game.getBackend().getResourceFactory().getResourceAsStream(file.getPath()));

			// load the sound into memory (a Clip)
			DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat());
			clip = (Clip) AudioSystem.getLine(info);
			sound = new Java2DSound(clip);
			clip.open(stream);
		} catch (UnsupportedAudioFileException | LineUnavailableException e) {
			e.printStackTrace();
		}
		return sound;
	}

}
