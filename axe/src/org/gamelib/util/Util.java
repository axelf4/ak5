/**
 * 
 */
package org.gamelib.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author pwnedary
 */
public final class Util {
	private Util() {}

	public static String readFile(String path, Charset encoding)
			throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return encoding.decode(ByteBuffer.wrap(encoded)).toString();
	}

	public static String readFile(String path) throws IOException {
		return readFile(path, Charset.defaultCharset());
	}
}
