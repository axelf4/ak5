/**
 * 
 */
package ak5.util;

import java.beans.ConstructorProperties;

/** Interval between the {@link #smallest} and the {@link #largest} values.
 * 
 * @author pwnedary */
public class Range<N extends Number & Comparable<N>> {
	/** The smallest possible in this range. */
	private N smallest;
	/** The largest possible in this range. */
	private N largest;

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
		if (n.compareTo(smallest) < 0) return smallest;
		if (n.compareTo(largest) > 0) return largest;
		return n;
	}

	@Override
	public int hashCode() {
		return smallest.hashCode() * largest.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return getClass() == obj.getClass() && smallest.equals(((Range<?>) obj).smallest) && largest.equals(((Range<?>) obj).largest);
	}

	@Override
	public String toString() {
		return smallest + "-" + largest;
	}

	/** @return the smallest */
	public N getSmallest() {
		return smallest;
	}

	/** @param smallest the smallest to set */
	public void setSmallest(N smallest) {
		this.smallest = smallest;
	}

	/** @return the largest */
	public N getLargest() {
		return largest;
	}

	/** @param largest the largest to set */
	public void setLargest(N largest) {
		this.largest = largest;
	}

	@SuppressWarnings("unchecked")
	public N getInterval() {
		if (smallest instanceof Byte && largest instanceof Byte) return (N) Byte.valueOf((byte) (largest.byteValue() - smallest.byteValue()));
		else if (smallest instanceof Double && largest instanceof Double) return (N) Double.valueOf(largest.doubleValue() - smallest.doubleValue());
		else if (smallest instanceof Float && largest instanceof Float) return (N) Float.valueOf(largest.floatValue() - smallest.floatValue());
		else if (smallest instanceof Integer && largest instanceof Integer) return (N) Integer.valueOf(largest.intValue() - smallest.intValue());
		else if (smallest instanceof Long && largest instanceof Long) return (N) Long.valueOf(largest.longValue() - smallest.longValue());
		else if (smallest instanceof Short && largest instanceof Short) return (N) Short.valueOf((short) (largest.shortValue() - smallest.shortValue()));
		else return null;
	}
}
