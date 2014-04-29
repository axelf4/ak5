/**
 * 
 */
package org.gamelib.backend;

import java.io.InputStream;

import org.gamelib.Drawable;
import org.gamelib.Event;
import org.gamelib.Handler;
import org.gamelib.Input.Key;
import org.gamelib.Loop;
import org.gamelib.Loop.LoopListener;
import org.gamelib.graphics.GL10;
import org.gamelib.util.Configuration;

/**
 * @author pwnedary
 *
 */
public abstract class BackendImpl implements Backend {
	protected Configuration configuration;

	/* * The {@link Thread} running in the background * / private Thread thread; */
	protected boolean running;
	protected Handler handler;
	protected Loop loop;

	@Override
	public void start(Configuration configuration, Handler handler) {
		this.configuration = configuration;
		this.handler = handler;

		running = true;
		// new Thread(loop = configuration.getProperty("loop", new FixedTimestepLoop(new DefaultLoopListener())), handler.toString()).start();
		loop = configuration.getProperty("loop", new Loop.FixedTimestep(new DefaultLoopListener()));
		loop.run();
	}

	protected abstract void start();

	@Override
	public void stop() {
		this.running = false;
	}

	@Override
	public boolean keepRunning() {
		return running;
	}

	@Override
	public Loop getLoop() {
		return loop;
	}

	@Override
	public InputStream getResourceAsStream(CharSequence name) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(name.toString());
	}

	public class DefaultLoopListener implements LoopListener {
		@Override
		public void start() {
			BackendImpl.this.start();
			handler.handle(new Event.Create()); // Initialize game lastly
		}

		@Override
		public void stop() {
			handler.handle(new Event.Dispose());
			dispose();
		}

		@Override
		public void tick(float delta) {
			getInput().poll();
			if (getInput().keyPressed(Key.KEY_ESCAPE)) BackendImpl.this.stop();
			handler.handle(new Event.Tick(delta));
		}

		@Override
		public void draw(float delta) {
			BackendImpl.this.draw(new Drawable() {
				@Override
				public void draw(GL10 gl, float delta) {
					handler.handle(new Event.Draw(gl, delta));
				}
			}, delta);
		}

		@Override
		public boolean keepRunning() {
			return BackendImpl.this.keepRunning();
		}
	}
}
