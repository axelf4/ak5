/**
 * 
 */
package org.gamelib.util.slow.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.gamelib.util.slow.reflection.proxy.Invocation;
import org.gamelib.util.slow.reflection.proxy.IterableProxy;
import org.gamelib.util.slow.reflection.proxy.ProxyUtil;

import static org.gamelib.util.Util.*;
import static org.gamelib.util.slow.reflection.proxy.ProxyUtil.*;

/**
 * @author pwnedary
 */
@SuppressWarnings("unchecked")
public class ListLib {

	public static <T> T on(Class<T> clazz) {
		return ProxyUtil.createProxy(clazz, new Invocation(clazz));
	}

	public static <T> T on(Class<T> clazz, Object... conArgs) {
		return ProxyUtil.createProxy(clazz, new Invocation(clazz), conArgs);
	}

	public static <T> T on(Class<T> clazz, Class<?>[] conArgTypes, Object[] conArgs) {
		return ProxyUtil.createProxy(clazz, new Invocation(clazz), conArgTypes, conArgs);
	}

	/** Transforms the {@link Collection} into a single object having the same methods. */
	public static <T extends Object> T each(Iterable<T> iterable) {
		return (T) createProxy(discoverGenericType(iterable), new IterableProxy<T>(iterable));
	}

	/** Transforms the {@link Collection} into a single object having the same methods. */
	public static <T extends Object> T each(Iterable<T> iterable, Class<T> clazz) {
		return createProxy(clazz, new IterableProxy<T>(iterable));
	}

	/** @return a col containing the objects */
	public static <T1 extends Object, T2 extends T1> List<T1> concat(Class<T1> clazz, T2... objects) {
		List<T1> list = new ArrayList<>(objects.length);
		for (T2 object : objects)
			if (object instanceof Collection<?>)
				list.addAll((Collection<? extends T1>) object);
			else
				list.add(object);
		return list;
	}

	/* Functions */

	public static <T extends Object> Sorter<T> sorter(final List<T> list) {
		return new SorterImpl<>(list);
	}

	public static <T> List<T> filter(Iterable<T> iterable, Matcher<? extends Object> matcher, Object argument) {
		Invocation invoke = Invocation.from(argument);
		List<T> filtered = new LinkedList<>();
		for (Iterator<T> iterator = iterable.iterator(); iterator.hasNext();) {
			T element = (T) iterator.next();
			Object object = invoke.invoke(element);
			if (matcher.matches(object)) filtered.add(element);
		}
		return filtered;
	}

	public static <T extends Object, T3 extends Object> Getter<T, T3> get(final List<T> list, T3 instance, Object argument) {
		return new Getter<T, T3>(list, instance, argument);
	}

}
