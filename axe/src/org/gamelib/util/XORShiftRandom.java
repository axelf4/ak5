/**
 * 
 */
package org.gamelib.util;

import java.util.Random;

/**
 * @author pwnedary
 */
public class XORShiftRandom extends Random {
	private static final long serialVersionUID = 7334697838133688082L;

	private long seed;

	public XORShiftRandom(long seed) {
		this.seed = seed;
	}

	public XORShiftRandom() {
		this(System.nanoTime());
	}

	@Override
	public synchronized void setSeed(long seed) {
		this.seed = seed;
	}

	/** Generates the next pseudorandom number. Not thread safe. */
	@Override
	protected int next(int bits) {
		long x = this.seed;
		x ^= x << 21;
		x ^= x >>> 35;
		x ^= x << 4;
		this.seed = x;
		return (int) (x & (1L << bits) - 1);
		// return (int) (x >>> (32 - bits));
	}

}
