/**
 * 
 */
package org.gamelib.util.slow.reflection;

import java.util.List;

import org.gamelib.util.slow.vfs.Vfs.File;

/**
 * @author pwnedary
 */
public interface Adapter<C, F, M> {

	// String getClassName(final C clazz);

	// String getSuperclassName(final C clazz);

	// List<String> getInterfacesNames(final C clazz);

	List<F> getFields(final C clazz);

	List<M> getMethods(final C clazz);

	// String getMethodName(final M method);

	// List<String> getParameterNames(final M method);

	// List<String> getClassAnnotationNames(final C aClass);

	// List<String> getFieldAnnotationNames(final F field);

	// List<String> getMethodAnnotationNames(final M method);

	// List<String> getParameterAnnotationNames(final M method, final int parameterIndex);

	C getReturnType(final M method);

	// String getFieldName(final F field);

	C getOrCreateClass(File file) throws Exception;

	// String getMethodModifier(M method);

	// String getMethodKey(C cls, M method);

	// String getMethodFullKey(C cls, M method);

	boolean isPublic(Object o);
	
}
