/**
 * 
 */
package org.gamelib.util;

import java.io.Serializable;

/**
 * A path determined by a pathfinding algorithm.
 * 
 * @author pwnedary
 * 
 */
public interface Path extends Serializable {

	/** @return the number of steps */
	public int getLength();

	/** @return the step at the given index */
	public Step getStep(int i);

	/** Append a step to the path. */
	public void appendStep(Step step);

	/** A single step within the path */
	public class Step implements Serializable {
		private static final long serialVersionUID = 9152451330690034687L;
		/** The x coordinate at the given step */
		private int x;
		/** The y coordinate at the given step */
		private int y;

		/**
		 * Create a new step
		 * 
		 * @param x The x coordinate of the new step
		 * @param y The y coordinate of the new step
		 */
		public Step(int x, int y) {
			this.x = x;
			this.y = y;
		}

		/**
		 * Get the x coordinate of the new step
		 * 
		 * @return The x coordinate of the new step
		 */
		public int getX() {
			return x;
		}

		/**
		 * Get the y coordinate of the new step
		 * 
		 * @return The y coordinate of the new step
		 */
		public int getY() {
			return y;
		}

		/**
		 * @see Object#hashCode()
		 */
		public int hashCode() {
			return x * y;
		}

		/**
		 * @see Object#equals(Object)
		 */
		public boolean equals(Object other) {
			if (other instanceof Step) {
				Step o = (Step) other;

				return (o.x == x) && (o.y == y);
			}

			return false;
		}
	}

}
