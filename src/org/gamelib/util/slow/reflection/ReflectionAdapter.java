/**
 * 
 */
package org.gamelib.util.slow.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import org.gamelib.util.slow.vfs.Vfs.File;

/**
 * @author pwnedary
 */
public class ReflectionAdapter implements Adapter<Class, Field, Method> {
	/** {@inheritDoc} */
	@Override
	public List<Field> getFields(Class clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public List<Method> getMethods(Class clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Class getReturnType(Method method) {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Class getOrCreateClass(File file) throws Exception {
		String name = file.getRelativePath().replace("/", ".").replace(".class", "");
		System.out.println("class name: " + name);
		return Reflection.forName(name, (ClassLoader) null);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isPublic(Object o) {
		Integer mod = o instanceof Class ? ((Class<?>) o).getModifiers() : o instanceof Member ? ((Member) o).getModifiers() : null;
		return mod != null && Modifier.isPublic(mod);

	}
}
