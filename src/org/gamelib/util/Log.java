/**
 * 
 */
package org.gamelib.util;

import java.awt.Graphics2D;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;

/**
 * @author pwnedary
 * 
 */
public class Log {
	/** The output stream for dumping the log out on */
	public static PrintStream out = System.out;
	/** The output stream for dumping the errors out on */
	public static PrintStream err = System.err;
	/** True if we're doing verbose logging INFO and DEBUG */
	@SuppressWarnings("unused")
	@Deprecated
	private static boolean verbose = true;
	public static final int LEVEL_NONE = 0;
	public static final int LEVEL_DEBUG = 3;
	public static final int LEVEL_INFO = 2;
	public static final int LEVEL_ERROR = 1;
	private static int level = Integer.parseInt(System.getProperty("gamelib.default_log_level", "3"));
	/** the start-time for {@link #startProfiling(String)} */
	private static final HashMap<String, Long> startTimes = new HashMap<String, Long>();

	/**
	 * Sets the log level. {@link #LEVEL_NONE} will mute all log output.
	 * {@link #LEVEL_ERROR} will only let error messages through.
	 * {@link #LEVEL_INFO} will let all non-debug messages through, and
	 * {@link #LEVEL_DEBUG} will let all messages through.
	 * 
	 * @param level {@link #LEVEL_NONE}, {@link #LEVEL_ERROR},
	 *            {@link #LEVEL_INFO}, {@link #LEVEL_DEBUG}.
	 */
	public static void setLogLevel(int level) {
		Log.level = level;
	}

	private static String timeStamp() {
		return new Date().toString();
	}

	public static void error(String message, Throwable e) {
		if (level >= LEVEL_ERROR) {
			err.println(timeStamp() + " ERROR: " + message);
			err.println(timeStamp() + " ERROR: " + e.getMessage());
			e.printStackTrace(err);
		}
	}

	public static void info(String message) {
		if (level >= LEVEL_INFO)
			out.println(timeStamp() + " INFO: " + message);
	}

	public static void debug(String message) {
		if (level >= LEVEL_DEBUG)
			out.println(timeStamp() + " DEBUG: " + message);
	}

	public static void startProfiling(String key) {
		startTimes.put(key, System.nanoTime());
	}

	public static long stopProfiling(String key) {
		if (!startTimes.containsKey(key))
			throw new RuntimeException("no start time called " + key);
		return System.nanoTime() - startTimes.remove(key);
	}

	public static void drawPieChart(Graphics2D g2d, int x, int y, int width, int height, long time) {
		// TODO draw pie-chart of startTimes times
	}
}
