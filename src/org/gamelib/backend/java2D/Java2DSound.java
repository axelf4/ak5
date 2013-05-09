/**
 * 
 */
package org.gamelib.backend.java2D;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.gamelib.Game;
import org.gamelib.backend.Sound;

/**
 * @author pwnedary
 */
public class Java2DSound implements Sound, LineListener {

	private Clip clip;

	/**
	 * 
	 */
	public Java2DSound(Clip clip) {
		this.clip = clip;
		clip.addLineListener(this);
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backend.Sound#play()
	 */
	@Override
	public void play() {
		clip.start();
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backend.Sound#pause()
	 */
	@Override
	public void pause() {
		clip.stop();
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backend.Sound#stop()
	 */
	@Override
	public void stop() {
		clip.stop();
		clip.setFramePosition(0);
	}
	
	/* (non-Javadoc)
	 * @see org.gamelib.backend.Sound#loop(int)
	 */
	@Override
	public void loop(int count) {
		clip.loop(count);
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backend.Sound#playing()
	 */
	@Override
	public boolean playing() {
		return clip.isRunning();
	}

	/** @deprecated in favor for factory. */
	public static Sound load(File file) throws IOException {
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

	/* (non-Javadoc)
	 * @see javax.sound.sampled.LineListener#update(javax.sound.sampled.LineEvent)
	 */
	@Override
	public void update(LineEvent event) {
		// if (event.getSource() != clip) return;
		if (event.getLine() != clip) return;
		if (event.getType() == LineEvent.Type.STOP) {
			clip.setFramePosition(0);
		}
	}

}
