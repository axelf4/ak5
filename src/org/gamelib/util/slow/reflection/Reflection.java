/**
 * 
 */
package org.gamelib.util.slow.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.gamelib.util.slow.reflection.model.Type;
import org.gamelib.util.slow.reflection.model.Type.ClassType;
import org.gamelib.util.slow.reflection.proxy.Invocation;
import org.gamelib.util.slow.reflection.proxy.Proxies;
import org.gamelib.util.slow.vfs.Vfs;

/**
 * @author pwnedary
 */
public final class Reflection {
	public static <T> T on(Class<T> clazz) {
		return Proxies.createProxy(clazz, new Invocation(clazz));
	}

	public static <T> T on(Class<T> clazz, Object[] conArgs) {
		return Proxies.createProxy(clazz, new Invocation(clazz), conArgs);
	}

	public static <T> T on(Class<T> clazz, Class<?>[] conArgTypes, Object[] conArgs) {
		return Proxies.createProxy(clazz, new Invocation(clazz), conArgTypes, conArgs);
	}

	// @formatter:off

	/** returns {@code Thread.currentThread().getContextClassLoader()} */
	public static ClassLoader contextClassLoader() { return Thread.currentThread().getContextClassLoader();	}

	/** returns {@code Reflections.class.getClassLoader()} */
	public static ClassLoader staticClassLoader() { return Reflection.class.getClassLoader(); }
	
	// @formatter:on

	/** returns given classLoaders, if not null, otherwise defaults to both {@link #contextClassLoader()} and {@link #staticClassLoader()} */
	public static ClassLoader[] classLoaders(ClassLoader... classLoaders) {
		if (classLoaders != null && classLoaders.length != 0) return classLoaders;
		else {
			ClassLoader contextClassLoader = contextClassLoader(), staticClassLoader = staticClassLoader();
			return contextClassLoader != null ? staticClassLoader != null && contextClassLoader != staticClassLoader ? new ClassLoader[] { contextClassLoader, staticClassLoader } : new ClassLoader[] { contextClassLoader } : new ClassLoader[] {};
		}
	}

	/**
	 * returns urls using {@link java.net.URLClassLoader#getURLs()} up the default classloaders parent hierarchy
	 * <p>
	 * using {@link #classLoaders(ClassLoader...)} to get default classloaders
	 **/
	public static Set<URL> forClassLoader() {
		return forClassLoader(classLoaders());
	}

	/**
	 * returns urls using {@link java.net.URLClassLoader#getURLs()} up the classloader parent hierarchy
	 * <p>
	 * if optional {@link ClassLoader}s are not specified, then both {@link #contextClassLoader()} and {@link #staticClassLoader()} are used for {@link ClassLoader#getResources(String)}
	 */
	public static Set<URL> forClassLoader(ClassLoader... classLoaders) {
		final Set<URL> result = new HashSet<>();
		final ClassLoader[] loaders = classLoaders(classLoaders);
		for (ClassLoader classLoader : loaders) {
			while (classLoader != null) {
				if (classLoader instanceof URLClassLoader) {
					URL[] urls = ((URLClassLoader) classLoader).getURLs();
					if (urls != null) result.addAll(Arrays.asList(urls));
				}
				classLoader = classLoader.getParent();
			}
		}
		return result;
	}

	/**
	 * tries to resolve a java type name to a Class
	 * <p>
	 * if optional {@link ClassLoader}s are not specified, then both {@link org.reflections.util.ClasspathHelper#contextClassLoader()} and {@link org.reflections.util.ClasspathHelper#staticClassLoader()} are used
	 */
	public static Class<?> forName(String className, ClassLoader... classLoaders) {
		/*
		 * for (ClassLoader classLoader : classLoaders(classLoaders)) { try { return classLoader.loadClass(typeName); } catch (Throwable e) { /* continue * /} }
		 */
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException | NoClassDefFoundError e) {
			return null;
		}
		// throw new RuntimeException("could not get type for name " + className);
	}

	public static void test123() {
		for (URL url : forClassLoader()) {
			for (Vfs.File file : Vfs.fromURL(url).getFiles()) {
				if (!file.getName().endsWith(".class")) continue;
				try {
					// File file = new File(url.getPath());
					Class clazz = (Class) getAdapter().getOrCreateClass(file);
					if (clazz == null) continue;
					for (Annotation annotation : clazz.getAnnotations()) {
						System.err.println(annotation.annotationType());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static Adapter<?, ?, ?> getAdapter() {
		return new ReflectionAdapter();
	}

	public static void setFieldFinal(Field field, boolean setFinal) {
		try {
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			if (setFinal) modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
			else modifiersField.setInt(field, field.getModifiers() | Modifier.FINAL);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static void setFieldValue(Field field, Object target, Object value) {
		try {
			field.setAccessible(true);
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
			field.set(target, value);
		} catch (IllegalAccessException e) {
			try {
				Method getReflectionFactory = Class.forName("sun.reflect.ReflectionFactory").getDeclaredMethod("getReflectionFactory");
				Object reflectionFactory = getReflectionFactory.invoke(null);
				Method newFieldAccessor = Class.forName("sun.reflect.ReflectionFactory").getDeclaredMethod("newFieldAccessor", Field.class, boolean.class);
				Method fieldAccessorSet = Class.forName("sun.reflect.FieldAccessor").getDeclaredMethod("set", Object.class, Object.class);
				Object fieldAccessor = newFieldAccessor.invoke(reflectionFactory, field, false);
				fieldAccessorSet.invoke(fieldAccessor, target, value);
			} catch (ReflectiveOperationException e1) {
				e1.printStackTrace();
			}
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
	}

	private static Object getConstructorAccessor(Class<?> enumClass, Class<?>[] additionalParameterTypes)
			throws Exception {
		Class<?>[] parameterTypes = new Class[additionalParameterTypes.length + 2];
		parameterTypes[0] = String.class;
		parameterTypes[1] = int.class;
		System.arraycopy(additionalParameterTypes, 0, parameterTypes, 2, additionalParameterTypes.length);

		Method getReflectionFactory = Class.forName("sun.reflect.ReflectionFactory").getDeclaredMethod("getReflectionFactory");
		Object reflectionFactory = getReflectionFactory.invoke(null);
		Method newConstructorAccessor = Class.forName("sun.reflect.ReflectionFactory").getDeclaredMethod("newConstructorAccessor", Constructor.class);
		return newConstructorAccessor.invoke(reflectionFactory, enumClass.getDeclaredConstructor(parameterTypes));
	}

	public static <T> T newInstance(Class<T> type, Class<?>[] inittypes, Object[] initargs) {
		try {
			return type.cast(type.getConstructor(inittypes).newInstance(initargs));
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			try {
				Method newInstance = Class.forName("sun.reflect.ConstructorAccessor").getDeclaredMethod("newInstance", Object[].class);
				return type.cast(newInstance.invoke(getConstructorAccessor(type, inittypes), new Object[] { initargs }));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}

	public static <T> Type<T> type(Class<T> type) {
		return new ClassType<T>(type);
	}

}
