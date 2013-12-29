/**
 * 
 */
package org.gamelib;

import org.gamelib.Handler.Event;
import org.gamelib.Loop.LoopListener;
import org.gamelib.backend.Backend;
import org.gamelib.backend.Configuration;
import org.gamelib.backend.Graphics;
import org.gamelib.backend.Input;
import org.gamelib.util.Instance;
import org.gamelib.util.Instance.CreationalPattern;
import org.gamelib.util.Logger.Log;

/**
 * An abstraction of a Game which handles the Backend and the Thread.
 * @author pwnedary
 */
public abstract class Game {
	/** The instance of the running game */
	@Instance(type = Game.class, pattern = CreationalPattern.SINGLETON)
	private static Game instance;
	/** The {@link Backend} handling the technical stuff */
	public Backend backend;
	/** The {@link Thread} running in the background */
	Thread thread;

	/** An instance of {@link Input} derived by the {@link Backend} */
	protected Input input;
	private boolean running = true;

	protected Game() {
		instance = instance == null ? this : instance;
	}

	/** Starts the game by starting the {@link Thread} and {@link Backend}. Calls {@link #initialize()}. */
	protected void start(Backend backend) {
		this.backend = backend;
		Log.info("Initialized " + this.toString());
		(thread = new Thread(getLoop(), this.toString())).start();
	}

	/** Stops the game programmatically by stopping the {@link Backend} after updates and rendering has returned. */
	public void stop() {
		running = false;
	}

	/** Called after the engine is setup. */
	public abstract void initialize();

	/** Used as the default window title and thread name. */
	@Override
	public abstract String toString();

	/**
	 * Returns the {@link Configuration} to use when starting this game.
	 * @return the Configuration to use
	 */
	public abstract Configuration getConfiguration();

	/** @return the {@link Loop} to use */
	public Loop getLoop() {
		return new FixedTimestepLoop(new DefaultLoopListener());
	}

	private class DefaultLoopListener implements LoopListener {
		@Override
		public void start() {
			// backend.start(Game.this);
			backend.start(getConfiguration());
			backend.setTitle(Game.this.toString());
			input = backend.getInput();
			initialize(); // initialize game lastly
		}

		@Override
		public void stop() {
			backend.stop();
		}

		@Override
		public void tick(float delta) {
			backend.getInput().poll();
			Registry.instance().dispatch(new Event.Tick(delta));
			// Registry.instance().dispatch(new Event.AdvancedTick(delta));
		}

		@Override
		public void draw(float delta) {
			backend.draw(new Drawable() {
				@Override
				public void draw(Graphics g, float delta) {
					Registry.instance().dispatch(new Event.Draw(g, delta));
				}
			}, delta);
		}

		@Override
		public boolean shouldStop() {
			return !running || backend.shouldClose();
		}
	}

	/** @return the instance of the running game */
	public static final Game instance() {
		return instance;
	}

	/** @return the instance of the running game's {@link Backend} */
	public static Backend getBackend() {
		return instance().backend;
	}

}
