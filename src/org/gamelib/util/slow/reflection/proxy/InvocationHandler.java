/**
 * 
 */
package org.gamelib.util.slow.reflection.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * An interceptor that seamlessly manages invocations on both a native Java proxy and a cglib one.
 * 
 * @author pwnedary
 */
public abstract class InvocationHandler implements java.lang.reflect.InvocationHandler, MethodInterceptor {

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
			throws Throwable {
		return invoke(obj, method, args);
	}

}
