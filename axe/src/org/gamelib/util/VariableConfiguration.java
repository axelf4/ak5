/**
 * 
 */
package org.gamelib.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

/** A marker interface, which defines that it's implementors will be using variables for storing properties. No
 * restrictions on their visibility.
 * 
 * @author pwnedary */
public interface VariableConfiguration extends Configuration {
	class VariableConfigurationImpl implements VariableConfiguration {
		@SuppressWarnings("unchecked")
		@Override
		public <T> T getProperty(Object key, T... def) {
			try {
				Field field = getClass().getDeclaredField((String) key);
				return (T) field.get(this);
			} catch (NoSuchFieldException e) {
				return (T) (def.length == 0 ? getDefaultValue(def.getClass().getComponentType()) : (def.length == 1 ? def[0] : def));
			} catch (ReflectiveOperationException e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		public <T> T setProperty(Object key, T value) {
			try {
				Field field = getClass().getDeclaredField((String) key);
				@SuppressWarnings("unchecked")
				T old = (T) field.get(this);
				field.set(this, value);
				return old;
			} catch (NoSuchFieldException e) {
				throw new IllegalArgumentException("No such key: " + key);
			} catch (ReflectiveOperationException e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		public boolean hasProperty(Object key) {
			try {
				getClass().getDeclaredField((String) key);
				return true;
			} catch (NoSuchFieldException e) {
				return false;
			}
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
			else return null;
		}
	}

}
