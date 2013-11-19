/**
 * 
 */
package org.gamelib.network;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author pwnedary
 */
public enum Side {
	CLIENT(), SERVER();

	/**
	 * @return if this is the server environment
	 */
	public boolean isServer() {
		return this == SERVER;
	}

	/**
	 * @return if this is the client environment
	 */
	public boolean isClient() {
		return this == CLIENT;
	}

	/**
	 * @author pwnedary
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR })
	public @interface SideOnly {
		public Side value();
	}
}
