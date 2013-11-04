/**
 * 
 */
package org.gamelib.backend;

import java.io.InputStream;

import org.gamelib.Game;
import org.gamelib.VideoMode;

/**
 * @author pwnedary
 */
public abstract class BackendImpl implements Backend {

	protected Game game;
	protected VideoMode videoMode;
	boolean shouldStop = false;

	/** Initializes getters from Game, then initializes Game. */
	@Override
	public void start(Game game) {
		this.game = game;
		this.videoMode = game.getResolution();
		if (game.toString().equals(game.getClass().toString())) throw new RuntimeException(game + " haven't overriden toString.");
		else setTitle(game.toString());
	}
	
	/** {@inheritDoc} */
	@Override
	public void stop() {
		this.shouldStop = true;
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean shouldClose() {
		return shouldStop;
	}
	
	@Override
	public InputStream getResourceAsStream(String name) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
	}

}
