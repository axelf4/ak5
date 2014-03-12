/**
 * 
 */
package org.gamelib.util.slow.reflection.proxy;

import java.lang.reflect.Method;
import java.util.Iterator;

/**
 * An {@link InvocationHandler} that delegates the invocation to every member.
 * @author pwnedary
 */
public class IterableProxy<T> extends InvocationHandler {

	/** Members to invoke. */
	Iterable<T> iterable;

	/** Creates a new {@link IterableProxy} using the specified {@link Iterable}. */
	public IterableProxy(Iterable<T> iterable) {
		this.iterable = iterable;
	}

	/** {@inheritDoc} */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object answer = null;
		for (Iterator<T> iterator = iterable.iterator(); iterator.hasNext();) {
			T obj = (T) iterator.next();
			Object value = method.invoke(obj, args);
			if (answer == null) answer = value; // return first
		}
		return answer;
	}

}
