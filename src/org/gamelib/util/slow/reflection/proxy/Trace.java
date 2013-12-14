/**
 * 
 */
package org.gamelib.util.slow.reflection.proxy;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

/**
 * @author pwnedary
 */
public abstract class Trace<T> extends InvocationHandler {
	private final Class<T> proxied;
	private Trace<?> next;
	Method method;
	Object[] args;

	public Trace(Class<T> proxied) {
		this.proxied = proxied;
	}

	public final Object call(Object instance) {
		trace(ProxyUtil.createProxy(proxied, this));
		try {
			for (Trace<?> element = this; element != null && element.method != null; element = element.next)
				instance = element.method.invoke(instance, args);
			return instance;
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public final Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		this.method = method;
		this.args = args;

		@SuppressWarnings("unchecked")
		Class<Object> returnType = (Class<Object>) method.getReturnType();
		Object value = ProxyUtil.isProxable(returnType) ? ProxyUtil.createProxy(returnType, next = new Trace<Object>(returnType) {
			@Override
			public void trace(Object instance) {}
		}) : getDefaultValue(returnType);

		return value;
	}

	/** Only one call on instance. */
	public abstract void trace(T instance);

	private static Object getDefaultValue(Class<?> clazz) {
		if (clazz == null) return null;
		else if (clazz == String.class) return "";
		else if (clazz.isArray()) return Array.newInstance(clazz.getComponentType(), 1);
		else if (clazz == Integer.TYPE || clazz == Integer.class) return 0;
		else if (clazz == Long.TYPE || clazz == Long.class) return 0l;
		else if (clazz == Double.TYPE || clazz == Double.class) return 0d;
		else if (clazz == Float.TYPE || clazz == Float.class) return 0f;
		else if (clazz == Character.TYPE || clazz == Character.class) return Character.forDigit(0 % Character.MAX_RADIX, Character.MAX_RADIX);
		else if (clazz == Short.TYPE || clazz == Short.class) return (short) 0;
		else if (clazz == Byte.TYPE || clazz == Byte.class) return (byte) 0;
		else if (clazz == Class.class) return clazz;
		else return null;
	}

}
