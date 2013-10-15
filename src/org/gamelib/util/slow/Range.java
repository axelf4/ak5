/**
 * 
 */
package org.gamelib.util.slow;

import java.beans.ConstructorProperties;

/**
 * Interval between the {@link #smallest} and the {@link #largest} values. 
 * @author pwnedary
 */
public class Range<N extends Number & Comparable<N>> {

	/** The smallest possible in this range. */
	private N smallest;
	/** The largest possible in this range. */
	private N largest;

	/**
	 * 
	 */
	@ConstructorProperties(value = { "smallest", "largest" })
	public Range(N smallest, N largest) {
		this.smallest = smallest.compareTo(largest) < 0 ? smallest : largest;
		this.largest = largest.compareTo(smallest) > 0 ? largest : smallest;
	}

	/** @return whether this range contains the number */
	public boolean contains(N n) {
		return smallest.compareTo(n) <= 0 && largest.compareTo(n) >= 0;
	}

	/** @return the number inside of this range */
	public N clamp(N n) {
		n = smallest.compareTo(n) < 0 ? n : smallest;
		n = largest.compareTo(n) > 0 ? n: largest;
		return n;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return smallest.hashCode() * largest.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return getClass() == obj.getClass() && smallest.equals(((Range<N>) obj).smallest) && largest.equals(((Range<N>) obj).largest);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return smallest + "-" + largest;
	}

	/**
	 * @return the smallest
	 */
	public N getSmallest() {
		return smallest;
	}

	/**
	 * @param smallest the smallest to set
	 */
	public void setSmallest(N smallest) {
		this.smallest = smallest;
	}

	/**
	 * @return the largest
	 */
	public N getLargest() {
		return largest;
	}

	/**
	 * @param largest the largest to set
	 */
	public void setLargest(N largest) {
		this.largest = largest;
	}

}
