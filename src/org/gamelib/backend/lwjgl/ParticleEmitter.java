/**
 * 
 */
package org.gamelib.backend.lwjgl;

import org.gamelib.util.Emitter;
import org.gamelib.util.geom.Vector2;

/**
 * @author pwnedary
 */
public class ParticleEmitter implements Emitter {

	Particle[] particles;
	/** the amount of living */
	int living;

	/**
	 * 
	 */
	public ParticleEmitter() {
		particles = new Particle[MAX_PARTICLES];
	}

	public void kill(int index) {
		if (living > 0) living--;
		Particle toKill = particles[index];
		particles[index] = particles[living];
		particles[living] = toKill; // re-use instance
	}

	public static class Particle {
		float type;
		Vector2 pos;
		Vector2 vel;
		float lifetimeMillis;
	}

}
