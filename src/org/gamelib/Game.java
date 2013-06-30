/**
 * 
 */
package org.gamelib;

import static org.gamelib.util.Log.*;

import javax.swing.JFrame;

import org.gamelib.Handler.Event;
import org.gamelib.Loop.LoopListener;
import org.gamelib.backend.Backend;
import org.gamelib.backend.Graphics;
import org.gamelib.backend.java2D.Java2DBackend;
import org.gamelib.resource.FileLoader;

/**
 * TODO distribute backend better
 * @author Axel
 */
public abstract class Game {

	protected static Game instance;

	Backend backend;
	@SuppressWarnings("unused")
	private Registry registry;
	public Input input;
	protected Thread thread;
	protected Loop loop;
	boolean requestedStop;

	/**
	 * 
	 */
	protected Game() {
		instance = instance == null ? this : instance;
	}

	protected void start(Backend backend) {
		// (this.backend = backend).start(this, getDisplayMode());
		this.backend = backend;
		if (instance.toString() == null) throw new Error("toString can't be null");
		backend.setTitle(instance.toString());

		registry = Registry.instance();
		input = backend.getInput();
		FileLoader.container = null;

		// instance.initialize();
		info("Initialized " + instance.toString());
		(thread = new Thread(getLoop(), this.toString())).start();
	}

	protected void start() {
		// (container = new JFrame()).add(screen = new Screen());
		start(new Java2DBackend(new JFrame()));
	}
	
	public void stop() {
		requestedStop = true;
	}

	/**
	 * TODO add java-doc
	 */
	protected abstract void initialize();

	public abstract String toString();

	/** @deprecated in favor of {@link #getResolution} */
	public DisplayMode getDisplayMode() {
		return DisplayMode.r800x600;
	}

	public Resolution getResolution() {
		return Resolution.r800x600;
	}

	public static Game getInstance() {
		return instance;
	}

	public static Backend getBackend() {
		return instance.backend;
	}

	public Loop getLoop() {
		return loop == null ? loop = new FixedTimestepLoop(new DefaultLoopListener(this)) : loop;
	}

	public static class DefaultLoopListener implements LoopListener {
		private Game game;

		public DefaultLoopListener(Game game) {
			this.game = game;
		}

		/*
		 * (non-Javadoc)
		 * @see org.gamelib.Loop.LoopListener#start()
		 */
		@Override
		public void start() {
			Game.getBackend().start(game, game.getResolution());
			// game.screen = new Screen(game.getResolution());
			game.initialize();
		}

		/*
		 * (non-Javadoc)
		 * @see org.gamelib.Loop.LoopListener#stop()
		 */
		@Override
		public void stop() {
			game.backend.destroy();
		}

		/*
		 * (non-Javadoc)
		 * @see org.gamelib.Loop.LoopListener#tick(float)
		 */
		@Override
		public void tick(float delta) {
			game.input.poll();
			// Registry.instance().dispatch(new Event.Tick(delta));
			Registry.instance().dispatch(new Event.AdvancedTick(delta));
		}

		/*
		 * (non-Javadoc)
		 * @see org.gamelib.Loop.LoopListener#draw(float)
		 */
		@Override
		public void draw(float delta) {
			final Backend backend = Game.getBackend();
			backend.screenUpdate(new Drawable() {
				@Override
				public void draw(Graphics g, float delta) {
					Registry.instance().dispatch(new Event.Draw(g, delta));
				}
			}, delta);
		}

		/*
		 * (non-Javadoc)
		 * @see org.gamelib.Loop.LoopListener#shouldStop()
		 */
		@Override
		public boolean shouldStop() {
			return Game.getBackend().shouldClose() || game.requestedStop;
		}
	}
}
