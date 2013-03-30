/**
 * 
 */
package org.gamelib.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

/**
 * @author pwnedary
 * 
 */
public class Particle implements Cloneable {

	public float x, y, hspeed, vspeed;
	public int life;

	/**
	 * 
	 */
	public Particle(int life) {
		this.life = life;

		Random random = new Random(System.currentTimeMillis());
		hspeed = (float) Math.cos(Math.toRadians(random.nextInt(360))) * 10;
		vspeed = (float) Math.sin(Math.toRadians(random.nextInt(360))) * 10;
	}

	/**
	 * @param g2d
	 */
	public void draw(Graphics2D g2d) {
		Log.debug("particle rolls" + x);
		g2d.setColor(Color.GREEN);
		g2d.drawRect((int) x, (int) y, 1, 1);
	}

	/**
	 * @return
	 */
	public boolean update() {
		x += hspeed;
		y += vspeed;

		return --life < 0;
	}

	public Particle create() throws CloneNotSupportedException {
		return (Particle) this.clone();
	}

}
