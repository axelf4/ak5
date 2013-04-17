/**
 * 
 */
package org.gamelib.resource;

import java.applet.Applet;
import java.awt.Container;
import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.gamelib.backend.Backend;
import org.gamelib.util.Log;

/**
 * change to FileLoader
 * 
 * @author Axel
 * 
 */
public class FileLoader {

	/** Indicates if we're running on a headless system. */
	private static boolean headless;
	private static final Map<String, FileParser> parsers = new HashMap<String, FileParser>();
	public static Backend backend;

	public static Container container;

	static {
		addFileParser(new ImageFileParser(), new MapFileParser());
	}

	public static Object load(String path) {
		String extension = path.substring(path.lastIndexOf('.') + 1);
		try {
			return parsers.get(extension).parse(new File(path));
		} catch (IOException e) {
			Log.error("couldn't load resource", e);
			return null;
		} catch (NullPointerException e) {
			Log.error("no parser for type " + extension, e);
			return null;
		}
	}

	public static InputStream getResourceStream(String path)
			throws FileNotFoundException {
		/*if (container instanceof Frame) return new FileInputStream(path);
		else if (container instanceof Applet) {
			System.out.println(new File(((Applet) container).getCodeBase().getPath(), path).toString());
			return new FileInputStream(new File(((Applet) container).getCodeBase().getPath(), path));
		} else
			return null;*/
		return new FileInputStream(path);
	}

	/**
	 * @return if headless
	 */
	public static boolean isHeadless() {
		return headless;
	}

	/**
	 * Indicate if we're running on a headless system where we don't want the
	 * images loaded.
	 * 
	 * @param h True if we're running on a headless system
	 */
	public static void setHeadless(boolean h) {
		headless = h;
	}

	public static void addFileParser(FileParser... fileParsers) {
		for (FileParser parser : fileParsers)
			for (String extension : parser.getExtensions()) {
				parsers.put(extension, parser);
			}
	}

}
