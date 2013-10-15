/**
 * 
 */
package org.gamelib.util.slow.vfs;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarFile;

import org.gamelib.util.slow.collection.ListLib;

import com.sun.istack.internal.Nullable;

/**
 * @author pwnedary
 */
public class Vfs {
	private static List<UrlType> defaultUrlTypes = new ArrayList<>(ListLib.concat(UrlType.class, DefaultUrlTypes.values()));

	/** an abstract vfs dir */
	public interface Dir {
		String getPath();

		Iterable<File> getFiles();

		void close();
	}

	/** an abstract vfs file */
	public interface File {
		String getName();

		String getRelativePath();

		InputStream openInputStream() throws IOException;
	}

	/** a matcher and factory for a url */
	public interface UrlType {
		boolean matches(URL url) throws Exception;

		Dir createDir(URL url) throws Exception;
	}

	/** tries to create a Dir from the given url, using the defaultUrlTypes */
	public static Dir fromURL(final URL url) {
		return fromURL(url, defaultUrlTypes);
	}

	/** tries to create a Dir from the given url, using the given urlTypes */
	public static Dir fromURL(final URL url, final List<UrlType> urlTypes) {
		for (UrlType type : urlTypes) {
			try {
				if (type.matches(url)) {
					Dir dir = type.createDir(url);
					if (dir != null) return dir;
				}
			} catch (Throwable e) {
				/*
				 * if (Reflections.log != null) { Reflections.log.warn("could not create Dir using " + type + " from url " + url.toExternalForm() + ". skipping.", e); }
				 */
			}
		}

		throw new RuntimeException("could not create Vfs.Dir from url, no matching UrlType was found [" + url.toExternalForm() + "]\n" + "either use fromURL(final URL url, final List<UrlType> urlTypes) or " + "use the static setDefaultURLTypes(final List<UrlType> urlTypes) or addDefaultURLTypes(UrlType urlType) " + "with your specialized UrlType.");
	}

	/** tries to create a Dir from the given url, using the given urlTypes */
	public static Dir fromURL(final URL url, final UrlType... urlTypes) {
		return fromURL(url, new ArrayList<>(Arrays.asList(urlTypes)));
	}

	/** try to get {@link java.io.File} from url */
	public static @Nullable
	java.io.File getFile(URL url) {
		java.io.File file;
		String path;

		try {
			path = url.toURI().getSchemeSpecificPart();
			if ((file = new java.io.File(path)).exists()) return file;
		} catch (URISyntaxException e) {}

		try {
			path = URLDecoder.decode(url.getPath(), "UTF-8");
			if (path.contains(".jar!")) path = path.substring(0, path.lastIndexOf(".jar!") + ".jar".length());
			if ((file = new java.io.File(path)).exists()) return file;

		} catch (UnsupportedEncodingException e) {}

		try {
			path = url.toExternalForm();
			if (path.startsWith("jar:")) path = path.substring("jar:".length());
			if (path.startsWith("file:")) path = path.substring("file:".length());
			if (path.contains(".jar!")) path = path.substring(0, path.indexOf(".jar!") + ".jar".length());
			if ((file = new java.io.File(path)).exists()) return file;

			path = path.replace("%20", " ");
			if ((file = new java.io.File(path)).exists()) return file;

		} catch (Exception e) {}

		return null;
	}

	/**
	 * default url types used by {@link org.reflections.vfs.Vfs#fromURL(java.net.URL)}
	 * <p>
	 * <p>
	 * jarFile - creates a {@link org.reflections.vfs.ZipDir} over jar file
	 * <p>
	 * jarUrl - creates a {@link org.reflections.vfs.ZipDir} over a jar url (contains ".jar!/" in it's name)
	 * <p>
	 * directory - creates a {@link org.reflections.vfs.SystemDir} over a file system directory
	 */
	public static enum DefaultUrlTypes implements UrlType {
		jarFile {
			public boolean matches(URL url) {
				return url.getProtocol().equals("file") && url.toExternalForm().contains(".jar");
			}

			public Dir createDir(final URL url) throws Exception {
				return new ZipDir(new JarFile(getFile(url)));
			}
		},
		
		jarUrl {
			public boolean matches(URL url) {
				return "jar".equals(url.getProtocol()) || "zip".equals(url.getProtocol()) || "wsjar".equals(url.getProtocol());
			}

			public Dir createDir(URL url) throws Exception {
				try {
					URLConnection urlConnection = url.openConnection();
					if (urlConnection instanceof JarURLConnection) { return new ZipDir(((JarURLConnection) urlConnection).getJarFile()); }
				} catch (Throwable e) { /* fallback */}
				java.io.File file = getFile(url);
				if (file != null) { return new ZipDir(new JarFile(file)); }
				return null;
			}
		},

		directory {
			public boolean matches(URL url) {
				return url.getProtocol().equals("file") && !url.toExternalForm().contains(".jar");
			}

			public Dir createDir(final URL url) throws Exception {
				return new SystemDir(getFile(url));
			}
		},
		
		jarInputStream {
			public boolean matches(URL url) throws Exception {
				return url.toExternalForm().contains(".jar");
			}

			public Dir createDir(final URL url) throws Exception {
				return new JarInputDir(url);
			}
		};

	}

}
