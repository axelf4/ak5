/**
 * 
 */
package org.gamelib.util;

import java.lang.reflect.Field;

import org.gamelib.util.slow.reflection.NativeType;

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
				return (T) (def.length == 0 ? new NativeType<>(def.getClass().getComponentType()).getDefaultValue() : (def.length == 1 ? def[0] : def));
			} catch (ReflectiveOperationException e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		public <T> T setProperty(Object key, T value) {
			try {
				Field field = getClass().getDeclaredField((String) key);
				@SuppressWarnings("unchecked") T old = (T) field.get(this);
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
	}

}
