/**
 * 
 */
package org.gamelib.util.slow.reflection.proxy;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author pwnedary
 */
public class Invocation extends InvocationHandler {
	static final Map<Object, Invocation> INVOCATION_BY_PLACEHOLDER = new WeakHashMap<>();

	final Class<?> proxiedClass;
	Method method;
	Object[] args;
	Invocation previous;
	int hashCode;

	public Invocation(Class<?> proxiedClass) {
		this.proxiedClass = proxiedClass;
	}

	private Invocation(Class<?> proxiedClass, Invocation previous) {
		this.proxiedClass = proxiedClass;
	}

	public Object invoke(Object instance) {
		if (previous != null) instance = previous.invoke(instance);
		try {
			return method.invoke(instance, args);
		} catch (ReflectiveOperationException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Object invoke(Object instance, Object[] args) {
		if (previous != null) instance = previous.invoke(instance);
		try {
			return method.invoke(instance, args);
		} catch (ReflectiveOperationException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/** {@inheritDoc} */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		if (method.equals(Object.class.getMethod("hashCode"))) return 0;
		// else if (method.equals(Object.class.getMethod("hashCode"))) return sequence.hashCode();
		else if (method.equals(Object.class.getMethod("equals", Object.class))) return false;
		// else if (method.equals(Object.class.getMethod("equals", Object.class))) return sequence.equals(args[0]);

		this.method = method;
		this.args = args;

		Class<?> returnType = method.getReturnType();
		Invocation next = new Invocation(returnType, this);
		Object value = ProxyUtil.isProxable(returnType) ? ProxyUtil.createProxy(returnType, next) : getDefaultValue(returnType, INVOCATION_BY_PLACEHOLDER.size());
		INVOCATION_BY_PLACEHOLDER.put(value, this);
		return value;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "";
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean equals(Object object) {
		return object != null && proxiedClass == ((Invocation) object).proxiedClass && method == ((Invocation) object).method && args == ((Invocation) object).args;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		if (hashCode != 0) return hashCode;
		hashCode = 13 * proxiedClass.hashCode() + 17 * method.hashCode();
		if (args != null) hashCode += 19 * args.length;
		if (previous != null) hashCode += 23 * previous.hashCode();
		return hashCode;
	}
	
	public static <T> T on(Class<T> clazz) {
		return ProxyUtil.createProxy(clazz, new Invocation(clazz));
	}

	public static Invocation from(Object placeholder) {
		return INVOCATION_BY_PLACEHOLDER.get(placeholder);
	}

	private static Object getDefaultValue(Class<?> clazz, Integer placeholderId) {
		if (clazz == null) return null;
		if (clazz == String.class) return String.valueOf(placeholderId);
		if (Date.class.isAssignableFrom(clazz)) return new Date(placeholderId);
		if (clazz.isArray()) return Array.newInstance(clazz.getComponentType(), 1);
		if (clazz == Integer.TYPE || clazz == Integer.class) return placeholderId;
		if (clazz == Long.TYPE || clazz == Long.class) return placeholderId.longValue();
		if (clazz == Double.TYPE || clazz == Double.class) return placeholderId.doubleValue();
		if (clazz == Float.TYPE || clazz == Float.class) return new Float(placeholderId % 1000000);
		if (clazz == Character.TYPE || clazz == Character.class) return Character.forDigit(placeholderId % Character.MAX_RADIX, Character.MAX_RADIX);
		if (clazz == Short.TYPE || clazz == Short.class) return placeholderId.shortValue();
		if (clazz == Byte.TYPE || clazz == Byte.class) return placeholderId.byteValue();
		return null;
	}

}
