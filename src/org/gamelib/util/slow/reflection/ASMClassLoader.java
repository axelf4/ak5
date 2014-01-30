/**
 * 
 */
package org.gamelib.util.slow.reflection;

import java.lang.reflect.Method;
import java.security.ProtectionDomain;

/**
 * @author Axel
 */
public class ASMClassLoader extends ClassLoader {
	public Class<?> defineClass(String name, byte[] b) {
		try {
			// Attempt to load the access class in the same loader, which makes protected and default access members accessible.
			Method method = ClassLoader.class.getDeclaredMethod("defineClass", new Class[] { String.class, byte[].class, int.class, int.class, ProtectionDomain.class });
			if (!method.isAccessible()) method.setAccessible(true);
			return (Class<?>) method.invoke(getParent(), new Object[] { name, b, Integer.valueOf(0), Integer.valueOf(b.length), getClass().getProtectionDomain() });
		} catch (Exception ignored) {}
		return defineClass(name, b, 0, b.length);
	}
}
