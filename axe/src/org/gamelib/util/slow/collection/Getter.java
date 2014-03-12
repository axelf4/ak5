/**
 * 
 */
package org.gamelib.util.slow.collection;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.gamelib.util.slow.reflection.proxy.Invocation;
import org.gamelib.util.slow.reflection.proxy.InvocationHandler;
import org.gamelib.util.slow.reflection.proxy.Proxies;

/**
 * @author pwnedary
 *
 */
public class Getter<T, T3 extends Object> {
	
	Iterable<? extends T> list;
	Class<T> clazz;
	T3 instance;
	List<Object> values = new LinkedList<>();

	public Getter(Iterable<? extends T> list, T3 instance, Object argument) {
		this.list = list;
		this.instance = instance;
		
		Invocation invoke = Invocation.from(argument);
		for (Iterator<? extends T> iterator = list.iterator(); iterator.hasNext();) {
			T obj = (T) iterator.next();
			Object value = invoke.invoke(obj);
			values.add(value);
		}
	}
	
	public T value(Class<T> clazz) {
		this.clazz = clazz;
		return Proxies.createProxy(clazz, new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				Object first = null;
				for (Iterator<? extends T> iterator = list.iterator(); iterator.hasNext();) {
					T obj = (T) iterator.next();
					Object value = method.invoke(obj, args);
					if (first == null) first = value;
					values.add(value);
				}
				return first;
				// return (0);
			}
		});
	}
	
	final InvocationHandler GET_INVOCATION_HANDLER = new InvocationHandler() {
		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			Object first = null;
			for (Iterator<Object> iterator = values.iterator(); iterator.hasNext();) {
				Object obj = (Object) iterator.next();
				Object value = method.invoke(instance, obj.toString());
				if (first == null) first = value;
			}
			return first;
		}
	};
	
	public <T2 extends Object> T2 get(Class<T2> clazz) {
		return (T2) Proxies.createProxy(clazz, GET_INVOCATION_HANDLER);
	}
	
	public <T2 extends Object> T2 get(Class<T2> clazz, Object... conArgs) {
		return (T2) Proxies.createProxy(clazz, GET_INVOCATION_HANDLER, conArgs);
	}
	
	public <T2 extends Object> T2 get(Class<T2> clazz, Class<?>[] conArgsTypes, Object[] conArgs) {
		return (T2) Proxies.createProxy(clazz, GET_INVOCATION_HANDLER, conArgsTypes, conArgs);
	}

}
