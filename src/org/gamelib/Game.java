/**
 * 
 */
package org.gamelib;

import org.gamelib.backend.Backend;
import org.gamelib.util.Instance;
import org.gamelib.util.Instance.CreationalPattern;

/**
 * An abstraction of a Game that holds the Backend.
 * 
 * @author pwnedary
 */
public abstract class Game {
	/** The instance of the running game */
	@Instance(type = Game.class, pattern = CreationalPattern.SINGLETON)
	private static Game instance;
	/** The {@link Backend} handling the technical stuff */
	protected Backend backend;

	public Game() {
		instance = this;
	}

	/** @return the instance of the running game */
	public static final Game instance() {
		return instance;
	}

	/** @return the instance of the running game's {@link Backend} */
	public static final Backend getBackend() {
		return instance.backend;
	}
}
