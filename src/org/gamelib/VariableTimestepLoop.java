/**
 * 
 */
package org.gamelib;

/**
 * @author Axel
 */
public class VariableTimestepLoop implements Loop {

	final int TARGET_FPS = 60;
	final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

	long lastFPSTime;
	int fps;
	int frameCount = 0;
	LoopListener listener;

	public VariableTimestepLoop(LoopListener listener) {
		this.listener = listener;
	}

	/** {@inheritDoc} */
	@Override
	public void run() {
		listener.start();
		long lastLoopTime = System.nanoTime();

		// keep looping round til the game ends
		while (listener.keepRunning()) {
			// work out how long its been since the last update, for interpolation
			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			float delta = updateLength / ((float) OPTIMAL_TIME);

			// update the frame counter
			lastFPSTime += updateLength;
			frameCount++;

			// update our FPS counter if a second has passed since we last recorded
			if (lastFPSTime >= 1000000000) {
				// System.out.println("(FPS: " + fps + ")");
				fps = frameCount;
				lastFPSTime = 0;
				frameCount = 0;
			}

			listener.tick(delta); // update the game logic
			listener.draw(delta); // draw everything

			// we want each frame to take 10 milliseconds, to do this
			// we've recorded when we started the frame. We add 10 milliseconds
			// to this and then factor in the current time for the
			// wait time
			// remember this is in ms, whereas our lastLoopTime etc. vars are in ns.
			try {
				Thread.sleep((lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000);
			} catch (InterruptedException e) {}
		}
		listener.stop();
	}

	/** {@inheritDoc} */
	@Override
	public int getFPS() {
		return fps;
	}

	/** {@inheritDoc} */
	@Override
	public void setLoopListener(LoopListener listener) {
		this.listener = listener;
	}

}
