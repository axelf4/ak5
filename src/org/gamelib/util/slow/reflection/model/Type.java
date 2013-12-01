/**
 * 
 */
package org.gamelib.util.slow.reflection.model;

/**
 * @author pwnedary
 */
public interface Type<T> {

	/** @return a new instance of this type */
	public T instance();

	public Function func(String name, Class<?>... params);

	public static class ClassType<T> implements Type<T> {
		private final Class<T> type;
		private final T instance;

		public ClassType(Class<T> type) {
			this.type = type;
			this.instance = instance();
		}

		@SuppressWarnings("unchecked")
		public ClassType(String name) throws ClassNotFoundException {
			this((Class<T>) Class.forName(name));
		}

		@SuppressWarnings("unchecked")
		public ClassType(T instance) {
			this.type = (Class<T>) instance.getClass();
			this.instance = instance;
		}

		@Override
		public T instance() {
			try {
				return type.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		public Function func(String name, Class<?>... params) {
			try {
				return new Function.FunctionImpl(instance, type.getDeclaredMethod(name, params));
			} catch (NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

}
