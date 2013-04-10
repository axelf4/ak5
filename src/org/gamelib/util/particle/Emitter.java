/**
 * 
 */
package org.gamelib.util.particle;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pwnedary
 *
 */
public class Emitter {
	
	private List<Particle> particles = new ArrayList<Particle>();

	/**
	 * 
	 */
	public Emitter() {
		// TODO Auto-generated constructor stub
	}
	
	public void update() {
		for (Particle p : particles) {
			p.lastPosition = p.position;
			p.position.add(p.velocity);
		}
	}

}
