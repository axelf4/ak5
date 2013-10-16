/**
 * 
 */
package org.gamelib;

/**
 * A {@link Runnable} that runs smoothly and takes care of drawing between updates.
 * @author pwnedary
 */
public interface Loop extends Runnable {
	/** @return the number of frames per seconds */
	public int getFPS();

	/** @param listener the {@link LoopListener} to use */
	public void setLoopListener(LoopListener listener);

	/** The listener to be notified at start and stop, when to update or draw and when to stop. */
	public interface LoopListener {
		/** The loop started. */
		public void start();

		/** The loop stopped. */
		public void stop();

		/** The game should update it's logic. */
		public void tick(float delta);

		/** The game should draw onto the canvas. */
		public void draw(float delta);

		/** @return whether to stop */
		public boolean shouldStop();
	}

}
