/**
 * 
 */
package org.gamelib.util.slow.reflection;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * TODO
 * @author pwnedary
 */
public class ClassPath {
	private static final String CLASS_FILE_EXTENSION = ".class";
	
	private final Set<URL> resources = new HashSet<>();

	public ClassPath(ClassLoader... classLoaders) {
		for (ClassLoader classLoader : classLoaders) {
			while (classLoader != null) {
				// if (classLoader instanceof URLClassLoader) {
				// URL[] urls = ((URLClassLoader) classLoader).getURLs();
				// if (urls != null) resources.addAll(Arrays.asList(urls));
				// }
				if (classLoader instanceof URLClassLoader) resources.addAll(Arrays.asList(((URLClassLoader) classLoader).getURLs()));
				classLoader = classLoader.getParent();
			}
		}
	}

	public ClassPath() {
		this(Reflection.classLoaders((ClassLoader[]) null));
	}

	public Set<URL> getClasses() {
		return resources;
	}
}
