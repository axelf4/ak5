/**
 * 
 */
package org.gamelib;

/**
 * {@link Runnable} that runs smoothly.
 * @author pwnedary
 */
public interface Loop extends Runnable {
	/** @return the number of frames per seconds */
	public int getFPS();

	/** @param listener the {@link LoopListener} to use */
	public void setLoopListener(LoopListener listener);

	public interface LoopListener {
		public void start();

		public void tick(float delta);

		public void draw(float delta);

		public boolean shouldStop();
	}

}
