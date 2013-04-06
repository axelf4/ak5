/**
 * 
 */
package org.gamelib;

import org.gamelib.Handler.Event;

/**
 * @author Axel
 * 
 */
public class FixedTimestepLoop implements Runnable {

	// This value would probably be stored elsewhere.
	final double GAME_HERTZ = 30.0; // 30.0
	// Calculate how many ns each frame should take for our target game hertz.
	final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
	// At the very most we will update the game this many times before a new
	// render.
	// If you're worried about visual hitches more than perfect timing, set this
	// to 1.
	final int MAX_UPDATES_BEFORE_RENDER = 5;

	// If we are able to get as high as this FPS, don't render again.
	final double TARGET_FPS = 60;
	final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;

	private Screen screen;
	private boolean isRunning;
	public int fps = 60;
	private int frameCount = 0;

	/**
	 * 
	 */
	public FixedTimestepLoop(Screen screen) {
		// TODO Auto-generated constructor stub
		this.screen = screen;
	}

	/* (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run() */
	public void run(Math math) {
		isRunning = true;
		while (isRunning) {
			try {
				HandlerRegistry.instance().invokeHandlers(new Event.Tick());
				// screen.repaint();
				screen.update();
				long sleepTime = 50;// figure out sleepTime 5000000
				long now = System.nanoTime(), diff;
				while ((diff = System.nanoTime() - now) < sleepTime) {
					if (diff < sleepTime * 0.8)
						Thread.sleep(1);
					else
						Thread.yield();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run() */
	public void run() {
		// TODO Auto-generated method stub

		// We will need the last update time.
		double lastUpdateTime = System.nanoTime();
		// Store the last time we rendered.
		double lastRenderTime = System.nanoTime();

		// Simple way of finding FPS.
		int lastSecondTime = (int) (lastUpdateTime / 1000000000);

		isRunning = true;
		while (isRunning) {
			double now = System.nanoTime();
			int updateCount = 0;

			// Do as many game updates as we need to, potentially playing
			// catchup.
			while (now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER) {
				// Update game
				HandlerRegistry.instance().invokeHandlers(new Event.Tick());
				lastUpdateTime += TIME_BETWEEN_UPDATES;
				updateCount++;
			}

			// If for some reason an update takes forever, we don't want to do
			// an insane number of catchups.
			// If you were doing some sort of game that needed to keep EXACT
			// time, you would get rid of this.
			if (now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
				lastUpdateTime = now - TIME_BETWEEN_UPDATES;
			}

			// Render. To do so, we need to calculate interpolation for a
			// smooth render.
			float interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES));
			// drawGame(interpolation);
			screen.interpolation = interpolation;
			// screen.repaint(0);
			screen.update();
			frameCount++;
			lastRenderTime = now;

			// Update the frames we got.
			int thisSecond = (int) (lastUpdateTime / 1000000000);
			if (thisSecond > lastSecondTime) {
				// System.out.println("NEW SECOND " + thisSecond + " " +
				// frameCount);
				fps = frameCount;

				// Drawing fps
				screen.fps = fps;

				frameCount = 0;
				lastSecondTime = thisSecond;
			}

			// Yield until it has been at least the target time between renders.
			// This saves the CPU from hogging.
			while (now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
				Thread.yield();

				// This stops the app from consuming all your CPU. It makes this
				// slightly less accurate, but is worth it.
				// You can remove this line and it will still work (better),
				// your CPU just climbs on certain OSes.
				// FYI on some OS's this can cause pretty bad stuttering. Scroll
				// down and have a look at different peoples' solutions to this.
				try {
					Thread.sleep(1);
				} catch (Exception e) {
				}

				now = System.nanoTime();
			}
		}
	}

}
