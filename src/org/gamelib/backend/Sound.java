/**
 * 
 */
package org.gamelib.backend;

/**
 * A playable sound.
 * @author pwnedary
 */
public interface Sound {
	/** If {@link #loop(int)} should loop continuously. */
	public static final int LOOP_CONTINUOUSLY = -1;

	/** Plays this sound. */
	public void play();

	/** Pauses this sound. */
	public void pause();

	/** Stops this sound being played. */
	public void stop();

	/** Starts looping playback from the current position. */
	public void loop(int count);

	/** @return if the sound is playing */
	public boolean playing();
}
