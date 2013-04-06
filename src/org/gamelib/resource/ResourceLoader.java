/**
 * 
 */
package org.gamelib.resource;

import java.applet.Applet;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gamelib.util.Log;

/**
 * change to FileLoader
 * @author Axel
 * 
 */
public class ResourceLoader {

	/** Indicates if we're running on a headless system. */
	private static boolean headless;
	private static final Map<String, FileParser> parsers = new HashMap<String, FileParser>();
	public static final List<Image> images = new ArrayList<Image>();
	
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

	public static InputStream getResourceAsStream(String path)
			throws FileNotFoundException {
		if (container instanceof Frame)
			return new FileInputStream(path);
		else if (container instanceof Applet) {
			System.out.println(new File(((Applet) container).getCodeBase().getPath(), path).toString());
			return new FileInputStream(new File(((Applet) container).getCodeBase().getPath(), path));
		} else
			return null;
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
	
	public static void addFileParser(FileParser... toAdd) {
		for (FileParser parser : toAdd)
		for (String extension : parser.getExtensions()) {
			parsers.put(extension, parser);
		}
	}

}
