/**
 * 
 */
package org.gamelib.util.slow.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;

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

	/**
	 * Discovers the generic type of the given {@link Iterator} based on the type of its first item (if any)
	 * 
	 * @param iterator The {@link Iterator} to be analyzed
	 * @return The Class of the first item of this {@link Iterator} (if any)
	 */
	public static Class<?> discoverGenericType(Iterator<?> iterator) {
		if (!iterator.hasNext()) throw new IllegalArgumentException("Unable to introspect on an empty iterator. Use the overloaded method accepting a class instead");
		Object next = iterator.next();
		return next != null ? next.getClass() : Object.class;
	}

	/**
	 * Discovers the generic type of the given {@link Iterable} based on the type of its first item (if any
	 * 
	 * @param iterable The {@link Iterable} to be analyzed
	 * @return The Class of the first item of this {@link Iterable} (if any)
	 */
	public static Class<?> discoverGenericType(Iterable<?> iterable) {
		return discoverGenericType(iterable.iterator());
	}
}
