/**
 * 
 */
package ak5.backend.gwt;

import ak5.Loop;

import com.google.gwt.user.client.Timer;

/** @author pwnedary */
public class TimerLoop implements Loop {
	private LoopListener listener;
	private int fps;
	private long lastTimeStamp = System.currentTimeMillis();
	private float time = 0;
	private int frames;

	public TimerLoop(LoopListener listener) {
		this.listener = listener;
	}

	@Override
	public void run() {
		listener.start();
		new Timer() {
			@Override
			public void run() {
				if (!listener.keepRunning()) {
					cancel();
					listener.stop();
					return;
				}
				long currTimeStamp = System.currentTimeMillis();
				float delta = (currTimeStamp - lastTimeStamp) / 1000.0f;
				lastTimeStamp = currTimeStamp;
				time += delta;
				frames++;
				if (time > 1) {
					fps = frames;
					time = 0;
					frames = 0;
				}
				
				listener.tick(delta);
				listener.draw(delta);
			}
		}.scheduleRepeating((int) ((1f / 10) * 1000));
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
