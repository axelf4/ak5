/**
 * 
 */
package org.gamelib.util.slow.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.gamelib.util.slow.collection.Row;
import org.gamelib.util.slow.reflection.Type.Method.Constructor;

/**
 * @author pwnedary
 */
public class NativeType<T> implements Type<T> {
	protected Class<T> type;
	protected final String name;
	protected final T instance;

	public NativeType(Class<T> type) {
		this.type = type;
		this.instance = null;
		this.name = type.getName();
	}

	public NativeType(Class<T> type, T instance) {
		this.type = type;
		this.instance = instance;
		this.name = type.getName();
	}

	public NativeType(String name) {
		this.name = name;
		this.type = null;
		this.instance = null;
	}

	public NativeType(String name, T instance) {
		this.name = name;
		this.instance = instance;
	}

	@SuppressWarnings("unchecked")
	public NativeType(T instance) {
		this.type = (Class<T>) instance.getClass();
		this.name = type.getName();
		this.instance = instance;
	}

	@Override
	public String getName() {
		return name != null ? name : type.getName();
	}

	@Override
	public String getSimpleName() {
		return name != null ? name.substring(Math.max(name.lastIndexOf('.'), name.lastIndexOf('$')) + 1) : type.getSimpleName();
	}

	@Override
	public Class<T> getType() {
		return type;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getDefaultValue() {
		if (type == Integer.TYPE || type == Integer.class) return (T) new Integer(0);
		else if (type == Long.TYPE || type == Long.class) return (T) new Long(0);
		else if (type == Double.TYPE || type == Double.class) return (T) new Double(0);
		else if (type == Float.TYPE || type == Float.class) return (T) new Float(0);
		else if (type == Character.TYPE || type == Character.class) return (T) new Character((char) 0);
		else if (type == Short.TYPE || type == Short.class) return (T) new Short((short) 0);
		else if (type == Byte.TYPE || type == Byte.class) return (T) new Byte((byte) 0);
		else if (type == Boolean.TYPE || type == Boolean.class) return (T) Boolean.FALSE;
		else if (type == String.class) return (T) "";
		else if (type.isArray()) return (T) Array.newInstance(type.getComponentType(), 0);
		else return null;
	}

	@Override
	public void load(ClassLoader... classLoaders) {
		if (classLoaders.length == 0) classLoaders = new ClassLoader[] { getClass().getClassLoader() };
		for (int i = 0; i < classLoaders.length; i++) {
			ClassLoader classLoader = classLoaders[i];
			try {
				classLoader.loadClass(type.getName());
			} catch (ClassNotFoundException e) {
				continue;
			}
			break;
		}
	}

	@Override
	public Field field(String name) throws ReflectiveOperationException {
		try {
			return new NativeField(type.getDeclaredField(name));
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Method method(String name, Class<?>... parameterTypes) {
		try {
			return new NativeMethod(type.getDeclaredMethod(name, parameterTypes));
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Constructor<T> constructor(Class<?>... parameterTypes) {
		try {
			return new NativeMethod().new NativeConstructor(type.getConstructor(parameterTypes));
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public org.gamelib.util.slow.reflection.Type.Package getPackage() {
		return new NativePackage(type.getPackage());
	}

	@Override
	public Annotation[] getAnnotations() {
		return type.getAnnotations();
	}

	@Override
	public <T1 extends Annotation> T1 getAnnotation(Class<T1> annotationClass) {
		return type.getAnnotation(annotationClass);
	}

	public class NativeField implements Field {
		private final java.lang.reflect.Field field;

		public NativeField(java.lang.reflect.Field field) {
			this.field = field;
			field.setAccessible(true);
		}

		@Override
		public Object get() {
			try {
				return field.get(field);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		public void set(Object value) {
			try {
				field.set(instance, value);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	private class NativeMethod implements Method {
		private final java.lang.reflect.Method method;
		private final Class<?>[] parameterTypes;
		private final Annotation[][] parameterAnnotations;

		public NativeMethod(java.lang.reflect.Method method) {
			this.method = method;
			parameterTypes = method.getParameterTypes();
			parameterAnnotations = method.getParameterAnnotations();
		}

		public NativeMethod() {
			this(null, null);
		}

		public NativeMethod(Class<?>[] parameterTypes, Annotation[][] parameterAnnotations) {
			this.method = null;
			this.parameterTypes = parameterTypes;
			this.parameterAnnotations = parameterAnnotations;
		}

		@Override
		public Object call(Object... args) throws ReflectiveOperationException {
			return method.invoke(instance, args);
		}

		@Override
		public Parameter parameter(int id) {
			if (id > parameterTypes.length) throw new IllegalArgumentException("No such parameter, index: " + id + '.');
			return new NativeParameter(id);
		}

		@Override
		public Class<?> getReturnType() {
			return method.getReturnType();
		}

		@Override
		public Annotation[] getAnnotations() {
			return method.getAnnotations();
		}

		@Override
		public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
			return method.getAnnotation(annotationClass);
		}

		private class NativeParameter implements Parameter {
			private final int id;

			public NativeParameter(int id) {
				this.id = id;
			}

			@Override
			public Class<?> getType() {
				return parameterTypes[id];
			}

			@Override
			public Annotation[] getAnnotations() {
				return parameterAnnotations[id];
			}

			@SuppressWarnings("unchecked")
			@Override
			public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
				for (Annotation annotation : getAnnotations())
					if (annotationClass.equals(annotation.annotationType())) return (A) annotation;
				return null;
			}
		}

		public class NativeConstructor extends NativeMethod implements Constructor<T> {
			private final java.lang.reflect.Constructor<T> constructor;

			public NativeConstructor(java.lang.reflect.Constructor<T> constructor) {
				super(constructor.getParameterTypes(), constructor.getParameterAnnotations());
				this.constructor = constructor;
			}

			@Override
			public T newInstance(Object... initargs)
					throws ReflectiveOperationException {
				return constructor.newInstance(initargs);
			}

			@Override
			public Object call(Object... args)
					throws ReflectiveOperationException {
				return newInstance(args);
			}

			@Override
			public Class<?> getReturnType() {
				return type;
			}

			@Override
			public Annotation[] getAnnotations() {
				return constructor.getAnnotations();
			}

			@Override
			public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
				return constructor.getAnnotation(annotationClass);
			}
		}
	}

	public class NativePackage implements Package {
		private final java.lang.Package _package;

		public NativePackage(java.lang.Package _package) {
			this._package = _package;
		}

		public NativePackage(String name) {
			this._package = java.lang.Package.getPackage(name);
		}

		@Override
		public String getName() {
			return _package.getName();
		}

		@Override
		public Annotation[] getAnnotations() {
			return _package.getAnnotations();
		}

		@Override
		public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
			return _package.getAnnotation(annotationClass);
		}

		@Override
		public Row<Type<?>> getClasses(ClassLoader... classLoaders) {
			// TODO make getClasses work
			final Set<URL> resources = new HashSet<>();
			for (ClassLoader classLoader : classLoaders) {
				while (classLoader != null) {
					if (classLoader instanceof URLClassLoader) resources.addAll(Arrays.asList(((URLClassLoader) classLoader).getURLs()));
					classLoader = classLoader.getParent();
				}
			}
			for (URL url : resources) {
				System.out.println(url);
			}
			return null;
		}
	}
}