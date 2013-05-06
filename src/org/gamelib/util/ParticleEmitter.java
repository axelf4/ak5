/**
 * 
 */
package org.gamelib.util;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.gamelib.Handler;
import org.gamelib.Registry;

/**
 * @author pwnedary
 * 
 */
public class ParticleEmitter implements Handler {

	public static final List<ParticleEmitter> emitters = new ArrayList<ParticleEmitter>();
	static {
		Registry.instance().register(new ParticleEmitter());
	}

	private final List<Particle> particles = new ArrayList<Particle>();
	private float x, y;

	/**
	 * 
	 */
	public ParticleEmitter(int x, int y) {
		this.x = x;
		this.y = y;
		emitters.add(this);
	}
	
	/**
	 * 
	 */
	public ParticleEmitter() {
	}

	public void addParticles(Particle particle, int amount) {
		try {
			for (int i = 0; i < amount; i++) {
				particles.add(particle.create());
			}
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.Handler#handle(org.gamelib.Handler.Event) */
	@Override
	public void handle(Event event) {
		if (event instanceof Event.Tick) {
			for (Iterator<ParticleEmitter> iterator = emitters.iterator(); iterator.hasNext();) {
				ParticleEmitter emitter = (ParticleEmitter) iterator.next();

				for (Iterator<Particle> iterator2 = emitter.particles.iterator(); iterator2.hasNext();) {
					Particle particle = (Particle) iterator2.next();
					if (particle.update())
						iterator2.remove();
				}
				if (emitter.particles.size() <= 0) {
					iterator.remove();
				}
			}
		} else if (event instanceof Event.Draw) {
			Graphics2D g2d = ((Event.Draw) event).graphics2d;
			for (Iterator<ParticleEmitter> iterator2 = emitters.iterator(); iterator2.hasNext();) {
				ParticleEmitter emitter = (ParticleEmitter) iterator2.next();
				for (Iterator<Particle> iterator = emitter.particles.iterator(); iterator.hasNext();) {
					Particle particle = (Particle) iterator.next();
					particle.draw(g2d);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.Handler#handlers(java.util.List) */
	@Override
	public void handlers(List<Class<? extends Event>> list) {
		list.add(Event.Tick.class);
		list.add(Event.Draw.class);
	}

}
