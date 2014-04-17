/**
 * 
 */
package org.gamelib.util;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

/** @author pwnedary */
public interface Configuration {
	/** Returns the property associated with <code>key</code>. In the event of no property associated with
	 * <code>key</code> the returned value is:
	 * <ul>
	 * <li><code>null</code> if <code>def.length == 0</code></li>
	 * <li><code>def[0]</code> if <code>def.length == 1</code></li>
	 * <li>and otherwise <code>def</code></li>
	 * </ul>
	 * 
	 * @param key the key associated with the value to return
	 * @param def the default if no property is associated with key
	 * @return the property associated with key */
	<T> T getProperty(Object key, @SuppressWarnings("unchecked") T... def);

	/** Sets <code>value</code> to be associated with <code>key</code> and optionally returns the previously associated
	 * value (or <code>null</code>).
	 * 
	 * @param key the key which to associate with
	 * @param value the value to be associated with key
	 * @return the previous association (or <code>null</code>) */
	<T> T setProperty(Object key, T value);

	/** Returns whether there already is a value associated with <code>key</code>.
	 * 
	 * @return whether there already is a value associated with key */
	boolean hasProperty(Object key);

	/** Forces changes to become available from {@link #getProperty(Object, Object...)}. */
	void flush();

	/** A marker interface, which defines that it's implementors will be using a {@link Map} structure for storing
	 * properties. */
	interface HashConfiguration extends Configuration {}

	/*  * A marker interface, which defines that it's implementors will be using variables for storing properties. No
	 * restrictions on their visibility. */
	// interface VariableConfiguration extends Configuration {}

	class HashConfigurationImpl implements HashConfiguration {
		private final Map<Object, Object> properties = new HashMap<>();

		@SuppressWarnings("unchecked")
		@Override
		public <T> T getProperty(Object key, T... def) {
			return (T) (hasProperty(key) ? properties.get(key) : (def.length == 0 ? getDefaultValue(def.getClass().getComponentType()) : (def.length == 1 ? def[0] : def)));
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> T setProperty(Object key, T value) {
			return (T) properties.put(key, value);
		}

		@Override
		public boolean hasProperty(Object key) {
			return properties.containsKey(key);
		}

		@Override
		public void flush() {}

		@SuppressWarnings("unchecked")
		public <T> T getDefaultValue(Class<T> type) {
			if (type == Integer.TYPE || type == Integer.class) return (T) new Integer(0);
			else if (type == Long.TYPE || type == Long.class) return (T) new Long(0);
			else if (type == Double.TYPE || type == Double.class) return (T) new Double(0);
			else if (type == Float.TYPE || type == Float.class) return (T) new Float(0);
			else if (type == Character.TYPE || type == Character.class) return (T) new Character((char) 0);
			else if (type == Short.TYPE || type == Short.class) return (T) new Short((short) 0);
			else if (type == Byte.TYPE || type == Byte.class) return (T) new Byte((byte) 0);
			else if (type == Boolean.TYPE || type == Boolean.class) return (T) Boolean.FALSE;
			else if (type == String.class) return (T) "";
			else if (type.isArray()) return (T) Array.newInstance(type.getComponentType(), 0);
			else return null;
		}
	}

	public static class Configurations {
		public static Configuration newConfiguration() {
			return new HashConfigurationImpl();
		}
	}
}
