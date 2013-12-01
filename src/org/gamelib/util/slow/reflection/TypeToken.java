/**
 * 
 */
package org.gamelib.util.slow.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * References a generic type.
 * 
 * @author pwnedary
 * @see http://gafter.blogspot.se/2006/12/super-type-tokens.html
 */
public abstract class TypeToken<T> {
	private final Type type;
	private volatile Constructor<?> constructor;

	protected TypeToken() {
		Type superclass = getClass().getGenericSuperclass();
		if (superclass instanceof ParameterizedType) this.type = ((ParameterizedType) superclass).getActualTypeArguments()[0];
		else throw new RuntimeException("Missing type parameter.");
	}

	/**
	 * Instantiates a new instance of {@code T} using the constructor.
	 * 
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	@SuppressWarnings("unchecked")
	public T newInstance(Class<?>[] parameterTypes, Object[] parameters)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException, InstantiationException {
		if (constructor == null) constructor = ((Class<?>) (type instanceof Class<?> ? type : ((ParameterizedType) type).getRawType())).getConstructor(parameterTypes);
		return (T) constructor.newInstance(parameters);
	}

	/**
	 * Instantiates a new instance of {@code T} using the default, no-arg constructor.
	 * 
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public T newInstance() throws NoSuchMethodException,
			IllegalAccessException, InvocationTargetException,
			InstantiationException {
		return newInstance(new Class<?>[] {}, new Object[] {});
	}

	/**
	 * Gets the referenced type.
	 */
	public Type getType() {
		return this.type;
	}
}
