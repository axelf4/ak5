/**
 * 
 */
package org.gamelib.util.slow.collection;

/**
 * @author pwnedary
 */
public final class Matchers {
	public static Matcher<Number> greaterThan(final double d) {
		return new Matcher<Number>() {
			@Override
			public boolean matches(Object obj) {
				return ((Number) obj).doubleValue() > d;
			}
		};
	}

	public static Matcher<Number> lesserThan(final double d) {
		return new Matcher<Number>() {
			@Override
			public boolean matches(Object obj) {
				return ((Number) obj).doubleValue() < d;
			}
		};
	}
}
