/**
 * 
 */
package org.gamelib.backend.gwt;

import org.gamelib.Loop;

import com.google.gwt.animation.client.AnimationScheduler;
import com.google.gwt.animation.client.AnimationScheduler.AnimationCallback;

/** @author pwnedary */
public class RequestAnimationFrameLoop implements Loop, AnimationCallback {
	private LoopListener listener;
	private AnimationScheduler scheduler = AnimationScheduler.get();

	private double last;
	private int fps = 0;

	public RequestAnimationFrameLoop(LoopListener listener) {
		this.listener = listener;
	}

	@Override
	public void run() {
		listener.start();
		scheduler.requestAnimationFrame(this);
	}

	@Override
	public int getFPS() {
		return fps;
	}

	@Override
	public void setLoopListener(LoopListener listener) {
		this.listener = listener;
	}

	@Override
	public void execute(double timestamp) {
		float delta = Math.min(1.0f, (float) (timestamp - last) / 1000);
		last = timestamp;
		fps = (int) (1000 / delta);

		listener.tick(delta);
		listener.draw(delta);

		if (listener.keepRunning())
		// AnimationHandle handle = 
		scheduler.requestAnimationFrame(this);
		else listener.stop();
	}
}
