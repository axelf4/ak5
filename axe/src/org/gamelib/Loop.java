/**
 * 
 */
package org.gamelib;

/** A {@link Runnable} that runs smoothly and takes care of drawing between updates.
 * 
 * @author pwnedary */
public interface Loop extends Runnable {
	/** @return the number of frames per seconds */
	int getFPS();

	/** @param listener the {@link LoopListener} to use */
	void setLoopListener(LoopListener listener);

	/** The listener to be notified at start and stop, when to update or draw and when to stop. */
	interface LoopListener {
		/** The loop started. */
		void start();

		/** The loop stopped. */
		void stop();

		/** The game should update it's logic. */
		void tick(float delta);

		/** The game should draw onto the canvas. */
		void draw(float delta);

		/** Returns <tt>true</tt> if the {@link Loop} should keep running.
		 * 
		 * @return whether to keep running */
		boolean keepRunning();
	}

	class FixedTimestep implements Loop {
		// This value would probably be stored elsewhere.
		private final double GAME_HERTZ = 30.0; // 30.0
		// Calculate how many ns each frame should take for our target game hertz.
		private final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
		// At the very most we will update the game this many times before a new render. If you're worried about visual hitches more than perfect timing, set this to 1.
		private final int MAX_UPDATES_BEFORE_RENDER = 5;

		// If we are able to get as high as this FPS, don't render again.
		private final double TARGET_FPS = 60;
		private final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;

		private LoopListener listener;
		public int fps = 60;
		private int frameCount = 0;

		public FixedTimestep(LoopListener listener) {
			this.listener = listener;
		}

		@Override
		public void run() {
			// We will need the last update time.
			double lastUpdateTime = System.nanoTime();
			// Store the last time we rendered.
			double lastRenderTime = System.nanoTime();
			// Simple way of finding FPS.
			int lastSecondTime = (int) (lastUpdateTime / 1000000000);
			listener.start();

			while (listener.keepRunning()) {
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
			listener.stop();
		}

		@Override
		public int getFPS() {
			return fps;
		}

		@Override
		public void setLoopListener(LoopListener listener) {
			this.listener = listener;
		}

	}
	
	class VariableTimestep implements Loop {
		final int TARGET_FPS = 60;
		final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

		long lastFPSTime;
		int fps;
		int frameCount = 0;
		LoopListener listener;

		public VariableTimestep(LoopListener listener) {
			this.listener = listener;
		}

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
					fps = frameCount;
					lastFPSTime = 0;
					frameCount = 0;
				}

				listener.tick(delta); // update the game logic
				listener.draw(delta); // draw everything

				// we want each frame to take 10 milliseconds, to do this we've recorded when we started the frame. We add 10 milliseconds to this and then factor in the current time for the wait time
				// remember this is in ms, whereas our lastLoopTime etc. vars are in ns.
				try {
					Thread.sleep((lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000);
				} catch (InterruptedException e) {}
			}
			listener.stop();
		}

		@Override
		public int getFPS() {
			return fps;
		}

		@Override
		public void setLoopListener(LoopListener listener) {
			this.listener = listener;
		}

	}
}
