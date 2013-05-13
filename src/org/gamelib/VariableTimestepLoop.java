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

	/**
	 * 
	 */
	public VariableTimestepLoop(LoopListener listener) {
		this.listener = listener;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		listener.start();
		long lastLoopTime = System.nanoTime();

		// keep looping round til the game ends
		while (!listener.shouldStop()) {
			// work out how long its been since the last update, this
			// will be used to calculate how far the entities should
			// move this loop
			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			float delta = updateLength / ((float) OPTIMAL_TIME);

			// update the frame counter
			lastFPSTime += updateLength;
			frameCount++;

			// update our FPS counter if a second has passed since
			// we last recorded
			if (lastFPSTime >= 1000000000) {
				// System.out.println("(FPS: " + fps + ")");
				fps = frameCount;
				lastFPSTime = 0;
				frameCount = 0;
			}

			// update the game logic
			// doGameUpdates(delta);
			listener.tick(delta);

			// draw everyting
			// render();
			listener.draw(delta);

			// we want each frame to take 10 milliseconds, to do this
			// we've recorded when we started the frame. We add 10 milliseconds
			// to this and then factor in the current time to give
			// us our final value to wait for
			// remember this is in ms, whereas our lastLoopTime etc. vars are in ns.
			try {
				Thread.sleep((lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000);
			} catch (InterruptedException e) {}
		}
		listener.stop();
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Loop#getFPS()
	 */
	@Override
	public int getFPS() {
		return fps;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Loop#setLoopListener(org.gamelib.Loop.LoopListener)
	 */
	@Override
	public void setLoopListener(LoopListener listener) {
		this.listener = listener;
	}

}
