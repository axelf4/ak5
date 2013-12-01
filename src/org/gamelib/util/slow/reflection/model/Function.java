/**
 * 
 */
package org.gamelib.util.slow.reflection.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author pwnedary
 */
public interface Function {

	public Object call(Object... params);

	public static class FunctionImpl implements Function {
		private final Object instance;
		private final Method method;

		public FunctionImpl(Object instance, Method method) {
			this.instance = instance;
			this.method = method;
		}

		@Override
		public Object call(Object... params) {
			try {
				return method.invoke(Modifier.isStatic(method.getModifiers()) ? null : instance, params);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

}
