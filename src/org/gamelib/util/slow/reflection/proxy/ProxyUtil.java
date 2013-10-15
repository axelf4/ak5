/**
 * 
 */
package org.gamelib.util.slow.reflection.proxy;

import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * @author pwnedary
 */
@SuppressWarnings("unchecked")
public class ProxyUtil {

	/**
	 * Creates a dynamic proxy
	 * @param clazz The class to be proxied
	 * @param handler The {@link InvocationHandler} that manages the invocations to the created proxy
	 * @param interfaces The interfaces that has to be implemented by the new proxy
	 * @return The newly created proxy
	 */
	public static <T> T createProxy(Class<T> clazz, InvocationHandler handler, Class<?>... interfaces) {
		if (clazz.isInterface() && Proxy.isProxyClass(clazz)) return (T) createNativeJavaProxy(clazz.getClassLoader(), handler, interfaces);
		if (!isProxable(clazz)) return null;
		return (T) createEnhancer(clazz, handler, interfaces).create();
	}
	
	/**
	 * Creates a dynamic proxy
	 * @param clazz The class to be proxied
	 * @param handler The {@link InvocationHandler} that manages the invocations to the created proxy
	 * @param interfaces The interfaces that has to be implemented by the new proxy
	 * @return The newly created proxy
	 */
	public static <T> T createProxy(Class<T> clazz, InvocationHandler handler, Object[] conArgs, Class<?>... interfaces) {
		if (conArgs == null) throw new IllegalArgumentException("conArgs cannot be null, use simples createProxy instead");
		Class<?>[] conArgsTypes = new Class<?>[conArgs.length];
		for (int i = 0; i < conArgsTypes.length; i++)
			conArgsTypes[i] = conArgs[i].getClass();
		return createProxy(clazz, handler, conArgsTypes, conArgs, interfaces);
	}
	
	/**
	 * Creates a dynamic proxy
	 * @param clazz The class to be proxied
	 * @param handler The {@link InvocationHandler} that manages the invocations to the created proxy
	 * @param interfaces The interfaces that has to be implemented by the new proxy
	 * @return The newly created proxy
	 */
	public static <T> T createProxy(Class<T> clazz, InvocationHandler handler, Class<?>[] conArgsTypes, Object[] conArgs, Class<?>... interfaces) {
		if (!isProxable(clazz)) return null;
		return (T) createEnhancer(clazz, handler, interfaces).create(conArgsTypes, conArgs);
	}

	/**
	 * Check if the given class is nor final neither a primitive one
	 * @param clazz The class to be checked
	 * @return True if the class is proxable, false otherwise
	 */
	public static boolean isProxable(Class<?> clazz) {
		return !clazz.isPrimitive() && !Modifier.isFinal(clazz.getModifiers()) && !clazz.isAnonymousClass();
	}

	private static Object createNativeJavaProxy(ClassLoader loader, java.lang.reflect.InvocationHandler handler, Class<?>... interfaces) {
		return Proxy.newProxyInstance(loader, interfaces, handler);
	}

	private static Enhancer createEnhancer(Class<?> clazz, MethodInterceptor interceptor, Class<?>... interfaces) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(interceptor);
		if (interfaces != null && interfaces.length > 0) enhancer.setInterfaces(interfaces);
		return enhancer;
	}

}
