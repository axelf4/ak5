/**
 * 
 */
package org.gamelib;

/** @author pwnedary */
public interface Audio {
	/** Plays this sound. */
	public void play();

	/** Pauses this sound. */
	public void pause();

	/** Stops this sound being played. */
	public void stop();

	/** Starts looping playback from the current position. */
	public void loop();

	/** @return if the sound is playing */
	public boolean isPlaying();
}
