/**
 * 
 */
package org.gamelib.util.slow.reflection;

import java.lang.reflect.Field;

/**
 * @author pwnedary
 */
public interface Access<T> {
	T get();

	void set(T value);

	public static class AccessFactory {
		public static <T> Access<T> getAccess(final Object instance, String name) {
			Field field;
			try {
				field = instance.getClass().getDeclaredField(name);
				return getAccess(instance, field);
			} catch (NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
				return null;
			}
		}

		@SuppressWarnings("unchecked")
		public static <T> Access<T> getAccess(final Object instance, final Field field) {
			try {
				if (!field.isAccessible()) field.setAccessible(true);
				return new Access<T>() {
					@Override
					public T get() {
						try {
							return (T) field.get(instance);
						} catch (IllegalArgumentException
								| IllegalAccessException e) {
							e.printStackTrace();
							return null;
						}
					}

					@Override
					public void set(T value) {
						try {
							field.set(instance, value);
						} catch (IllegalArgumentException
								| IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				};
			} catch (SecurityException e) {
				e.printStackTrace();
				return null;
			}
		}
	}
}
