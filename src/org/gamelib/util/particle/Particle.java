/**
 * 
 */
package org.gamelib.util.particle;

import org.gamelib.util.geom.Vector2;

/**
 * @author pwnedary
 *
 */
public class Particle {
	
	public Vector2 position, lastPosition, velocity;
	
	private long startTime;

	/**
	 * 
	 */
	public Particle(int x, int y) {
		startTime = System.currentTimeMillis();
		lastPosition = position = new Vector2(x, y);
	}

}
