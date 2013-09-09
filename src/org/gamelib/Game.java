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
 * TODO: rename to Game2 TODO: add BasicGame impl
 * @author pwnedary
 */
public abstract class Game {
	@Instance(type = Game.class, pattern = CreationalPattern.SINGLETON)
	static Game instance;
	protected Backend backend;
	Thread thread;

	protected Input input;
	
	protected Game() {
		instance = instance == null ? this : instance;
	}

	protected void start(@NotNull Backend backend) {
		this.backend = backend;
		this.input = backend.getInput();

		info("Initialized " + this.toString());
		(thread = new Thread(getLoop(), this.toString())).start();
	}

	public abstract void initialize();

	/** {@inheritDoc} */
	@NotNull
	@Override
	public abstract String toString();

	public abstract VideoMode getResolution();

	public Loop getLoop() {
		return new FixedTimestepLoop(new DefaultLoopListener());
	}

	class DefaultLoopListener implements LoopListener {
		/** {@inheritDoc} */
		@Override
		public void start() {
			backend.start(Game.this);
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
			backend.screenUpdate(new Drawable() {
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
	
	public static final Game instance() {
		return instance;
	}

	public Backend getBackend() {
		return backend;
	}

}
