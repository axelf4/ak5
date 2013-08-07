/**
 * 
 */
package org.gamelib.backend;

import org.gamelib.Game;
import org.gamelib.Resolution;
import org.gamelib.util.geom.Rectangle;

/**
 * @author pwnedary
 */
public abstract class BackendImpl implements Backend {

	protected Game game;
	protected Resolution resolution;
	boolean shouldClose = false;

	/** Initializes getters from Game, and initializes Game. */
	@Override
	public void start(Game game) {
		this.game = game;
		this.resolution = game.getResolution();
		if (game.toString().equals(game.getClass().toString())) throw new Error();
		else setTitle(game.toString());
		game.initialize();
	}
	
	/** {@inheritDoc} */
	@Override
	public void stop() {
		this.shouldClose = true;
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean shouldClose() {
		return shouldClose;
	}

	/** {@inheritDoc} is deprecated and is going to ve removed */
	@Override
	public Rectangle getSize() {
		return null;
	}

}
