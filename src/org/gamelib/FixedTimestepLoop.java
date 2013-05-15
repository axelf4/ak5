/**
 * 
 */
package org.gamelib;

/**
 * @author pwnedary
 */
public class FixedTimestepLoop implements Loop {

	// This value would probably be stored elsewhere.
	final double GAME_HERTZ = 30.0; // 30.0
	// Calculate how many ns each frame should take for our target game hertz.
	final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
	// At the very most we will update the game this many times before a new render. If you're worried about visual hitches more than perfect timing, set this to 1.
	final int MAX_UPDATES_BEFORE_RENDER = 5;

	// If we are able to get as high as this FPS, don't render again.
	final double TARGET_FPS = 20; // 60
	final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;

	private LoopListener listener;
	public int fps = 60;
	private int frameCount = 0;

	/**
	 * 
	 */
	public FixedTimestepLoop(LoopListener listener) {
		this.listener = listener;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// We will need the last update time.
		double lastUpdateTime = System.nanoTime();
		// Store the last time we rendered.
		double lastRenderTime = System.nanoTime();
		// Simple way of finding FPS.
		int lastSecondTime = (int) (lastUpdateTime / 1000000000);
		listener.start();

		while (!listener.shouldStop()) {
			double now = System.nanoTime();
			int updateCount = 0;

			// Do as many game updates as we need to, potentially playing catchup.
			while (now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER) {
				// Update game
				float delta = Math.min(1.0F, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES));
				listener.tick(delta);
				lastUpdateTime += TIME_BETWEEN_UPDATES;
				updateCount++;
			}

			// If for some reason an update takes forever, we don't want to do an insane number of catchups.
			// If you were doing some sort of game that needed to keep EXACT time, you would get rid of this.
			if (now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
				lastUpdateTime = now - TIME_BETWEEN_UPDATES;
			}

			// Render. To do so, we need to calculate interpolation for a smooth render.
			float delta = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES));
			listener.draw(delta);
			frameCount++;
			lastRenderTime = now;

			// Update the frames we got.
			int thisSecond = (int) (lastUpdateTime / 1000000000);
			if (thisSecond > lastSecondTime) {
				// System.out.println("NEW SECOND " + thisSecond + " " + frameCount);
				fps = frameCount;
				frameCount = 0;
				lastSecondTime = thisSecond;
			}

			// Yield until it has been at least the target time between renders. This saves the CPU from hogging.
			while (now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
				Thread.yield();

				// This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
				// You can remove this line and it will still work (better), your CPU just climbs on certain OSes.
				// FYI on some OS's this can cause pretty bad stuttering.
				try {
					Thread.sleep(1);
				} catch (Exception e) {}

				now = System.nanoTime();
			}
		}
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
