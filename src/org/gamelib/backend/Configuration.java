/**
 * 
 */
package org.gamelib.backend;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pwnedary
 */
public interface Configuration {

	/**
	 * Returns the property associated with <code>key</code>. In the event of no property associated with <code>key</code> the returned value is:
	 * <ul>
	 * <li><code>null</code> if <code>def.length == 0</code></li>
	 * <li><code>def[0]</code> if <code>def.length == 1</code></li>
	 * <li>and otherwise <code>def</code></li>
	 * </ul>
	 * @param key the key associated with the value to return
	 * @param def the default if no property is associated with key
	 * @return the property associated with key
	 */
	<T> T getProperty(Object key, @SuppressWarnings("unchecked") T... def);

	/**
	 * Sets <code>value</code> to be associated with <code>key</code> and returns the previously associated value.
	 * @param key the key which to associate with
	 * @param value the value to be associated with key
	 * @return the previous association
	 */
	<T> T setProperty(Object key, T value);

	/**
	 * Returns whether there already is a value associated with <code>key</code>.
	 * @return whether there already is a value associated with key
	 */
	boolean hasProperty(Object key);

	/**
	 * Forces changes to become available from {@link #getProperty(Object, Object...)}.
	 */
	void flush();

	public static abstract class ConfigurationImpl implements Configuration {
		private Map<Object, Object> properties = new HashMap<>();

		@SuppressWarnings("unchecked")
		@Override
		public <T> T getProperty(Object key, T... def) {
			return (T) (hasProperty(key) ? properties.get(key) : (def.length == 0 ? getDefaultValue(def.getClass().getComponentType()) : (def.length == 1 ? def[0] : def)));
		}

		@SuppressWarnings("unchecked")
		private static <T> T getDefaultValue(Class<T> clazz) {
			if (clazz == String.class) return (T) "";
			else if (clazz.isArray()) return (T) Array.newInstance(clazz.getComponentType(), 0);
			else if (clazz == Integer.TYPE || clazz == Integer.class) return (T) new Integer(0);
			else if (clazz == Long.TYPE || clazz == Long.class) return (T) new Long(0);
			else if (clazz == Double.TYPE || clazz == Double.class) return (T) new Double(0);
			else if (clazz == Float.TYPE || clazz == Float.class) return (T) new Float(0);
			else if (clazz == Character.TYPE || clazz == Character.class) return (T) new Character((char) 0);
			else if (clazz == Short.TYPE || clazz == Short.class) return (T) new Short((short) 0);
			else if (clazz == Byte.TYPE || clazz == Byte.class) return (T) new Byte((byte) 0);
			else if (clazz == Boolean.TYPE || clazz == Boolean.class) return (T) Boolean.FALSE;
			else return null;
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
	}

	public static class Configurations {
		public static Configuration newConfiguration() {
			return new ConfigurationImpl() {};
		}
	}

	public static class DisplayConfiguration extends ConfigurationImpl {
		public static final String WIDTH_KEY = "width", HEIGHT_KEY = "height",
				FULLSCREEN_KEY = "fullscreen", RESIZABLE_KEY = "resizable";

		private int width;
		private int height;
		private boolean fullscreen;
		private boolean resizable;

		public DisplayConfiguration(int width, int height) {
			setWidth(width);
			setHeight(height);
			// setFullscreen(false); // setup defaults
			// setResizable(false);
		}

		/**
		 * Returns the width of the canvas.
		 * @return the canvas's width
		 */
		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		/**
		 * Returns the height of the canvas.
		 * @return the canvas's height
		 */
		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public boolean fullscreen() {
			return fullscreen;
		}

		public void setFullscreen(boolean fullscreen) {
			this.fullscreen = fullscreen;
		}

		/**
		 * Returns, if using a window, allowing it to be resized.
		 * @return if the window is resizable
		 */
		public boolean resizable() {
			return resizable;
		}

		public void setResizable(boolean resizable) {
			this.resizable = resizable;
		}

		@Override
		public void flush() {
			super.flush();
			setProperty(WIDTH_KEY, getWidth());
			setProperty(HEIGHT_KEY, getHeight());
			setProperty(FULLSCREEN_KEY, fullscreen());
			setProperty(RESIZABLE_KEY, resizable());
		}
	}
}
