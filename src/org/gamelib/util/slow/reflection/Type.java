/**
 * 
 */
package org.gamelib.util.slow.reflection;

import java.lang.annotation.Annotation;

import org.gamelib.util.slow.collection.Row;
import org.gamelib.util.slow.reflection.Type.Annotated;
import org.gamelib.util.slow.reflection.Type.Method.Constructor;

/**
 * @author pwnedary
 * @see NativeType
 */
public interface Type<T> extends Reflective {
	/**
	 * Returns the fully qualified name of this class.
	 * 
	 * @return the name of this class.
	 */
	String getName();

	String getSimpleName();

	Class<T> getType();

	void load(ClassLoader... classLoaders);

	Field field(String name) throws ReflectiveOperationException;

	Method method(String name, Class<?>... parameterTypes)
			throws ReflectiveOperationException;

	Constructor<T> constructor(Class<?>... parameterTypes)
			throws ReflectiveOperationException;

	Package getPackage();

	interface Field {
		/**
		 * Returns the value of the field represented by this {@link Field} object.
		 * 
		 * @return the value of this field
		 */
		Object get();

		/**
		 * Sets the field represented by this {@link Field} object to {@code value}.
		 * 
		 * @param value the new value for this field being modified
		 * @throws IllegalArgumentException if <code>value</code> isn't assignable from this field's type
		 */
		void set(Object value) throws IllegalArgumentException;
	}

	interface Method extends Annotated {
		Object call(Object... args) throws ReflectiveOperationException;

		Parameter parameter(int id);

		Class<?> getReturnType();

		/** Represents a method or constructor parameter. */
		interface Parameter extends Annotated {
			/** Returns the type of the parameter. */
			Class<?> getType();
		}

		interface Constructor<T> extends Method {
			/**
			 * @param args Arguments passed into the constructor.
			 */
			T newInstance(Object... initargs)
					throws ReflectiveOperationException;
		}
	}

	interface Package extends Annotated {
		String getName();

		Row<Type<?>> getClasses(ClassLoader... classLoaders);
	}

	/** Enumeration of class and member access modifiers in canonical order. */
	enum Modifier {
		PUBLIC, PRIVATE, PROTECTED, STATIC, FINAL, SYNCHRONIZED, VOLATILE, TRANSIENT, NATIVE, INTERFACE, ABSTRACT, STRICT;
	}

	interface Annotated {
		Annotation[] getAnnotations();

		<A extends Annotation> A getAnnotation(Class<A> annotationClass);
	}
}

interface Reflective extends Annotated {}