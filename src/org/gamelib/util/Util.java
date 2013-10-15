/**
 * 
 */
package org.gamelib.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

/**
 * @author pwnedary
 */
public final class Util {

	private Util() {}

	public static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return encoding.decode(ByteBuffer.wrap(encoded)).toString();
	}
	
	public static String readFile(String path) throws IOException {
		return readFile(path, Charset.defaultCharset());
	}
	
	/**
	 * Discovers the generic type of the given {@link Iterator} based on the type of its first item (if any)
	 * @param iterator The {@link Iterator} to be analyzed
	 * @return The Class of the first item of this {@link Iterator} (if any)
	 */
	public static Class<?> discoverGenericType(Iterator<?> iterator) {
		if (!iterator.hasNext()) throw new IllegalArgumentException("Unable to introspect on an empty iterator. Use the overloaded method accepting a class instead");
		Object next = iterator.next();
		return next != null ? next.getClass() : Object.class;
	}

	/**
	 * Discovers the generic type of the given {@link Iterable} based on the type of its first item (if any
	 * @param iterable The {@link Iterable} to be analyzed
	 * @return The Class of the first item of this {@link Iterable} (if any)
	 */
	public static Class<?> discoverGenericType(Iterable<?> iterable) {
		return discoverGenericType(iterable.iterator());
	}

}
