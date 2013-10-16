/**
 * 
 */
package org.gamelib;

import static org.gamelib.util.Log.*;

import org.gamelib.Handler.Event;
import org.gamelib.Loop.LoopListener;
import org.gamelib.backend.Backend;
import org.gamelib.backend.Graphics;
import org.gamelib.util.Instance;
import org.gamelib.util.Instance.CreationalPattern;

import com.sun.istack.internal.NotNull;

/**
 * An abstraction of a Game which handles the Backend and the Thread.
 * @author pwnedary
 */
public abstract class Game {
	/** The instance of the running game */
	@Instance(type = Game.class, pattern = CreationalPattern.SINGLETON)
	static Game instance;
	/** The {@link Backend} handling the technical stuff */
	protected Backend backend;
	/** The {@link Thread} running in the background */
	Thread thread;

	/** An instance of {@link Input} derived by the {@link Backend} */
	protected Input input;

	protected Game() {
		instance = instance == null ? this : instance;
	}

	/** Starts the game by starting the {@link Thread} and {@link Backend}. Calls {@link #initialize()}. */
	protected void start(@NotNull Backend backend) {
		this.backend = backend;
		this.input = backend.getInput();

		info("Initialized " + this.toString());
		(thread = new Thread(getLoop(), this.toString())).start();
	}

	/** Called after the engine is setup. */
	public abstract void initialize();

	/** Used as the default window title and thread name. */
	@NotNull
	@Override
	public abstract String toString();

	/** @return the {@link VideoMode} to use */
	public abstract VideoMode getResolution();

	/** @return the {@link Loop} to use */
	public Loop getLoop() {
		return new FixedTimestepLoop(new DefaultLoopListener());
	}

	class DefaultLoopListener implements LoopListener {
		/** {@inheritDoc} */
		@Override
		public void start() {
			backend.start(Game.this);
			initialize(); // initialize game lastly
		}

		/** {@inheritDoc} */
		@Override
		public void stop() {
			backend.stop();
			backend.destroy();
		}

		/** {@inheritDoc} */
		@Override
		public void tick(float delta) {
			backend.getInput().poll();
			Registry.instance().dispatch(new Event.Tick(delta));
			// Registry.instance().dispatch(new Event.AdvancedTick(delta));
		}

		/** {@inheritDoc} */
		@Override
		public void draw(float delta) {
			backend.draw(new Drawable() {
				@Override
				public void draw(Graphics g, float delta) {
					Registry.instance().dispatch(new Event.Draw(g, delta));
				}
			}, delta);
		}

		/** {@inheritDoc} */
		@Override
		public boolean shouldStop() {
			return backend.shouldClose();
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
