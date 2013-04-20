/**
 * 
 */
package org.gamelib.backend;

/**
 * @author pwnedary
 */
public interface Sound {

	/** Plays this sound. */
	public void play();
	
	/** Pauses this sound. */
	public void pause();

	/** Stops this sound being played. */
	public void stop();

	/** @return if the sound is playing */
	public boolean playing();

}
