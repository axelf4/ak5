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
 */
public class Logger {
	/** The output stream for dumping the log out on */
	public static PrintStream out = System.out;
	/** The output stream for dumping the errors out on */
	public static PrintStream err = System.err;
	/** Current acceptance of messages */
	private Level level = Level.valueOf(System.getProperty("gamelib.default_log_level", "ERROR"));

	/** the start times for {@link #startProfiling(String)} */
	@Deprecated
	private static final HashMap<String, Long> startTimes = new HashMap<String, Long>();

	public static enum Level {
		/** No messages */
		NONE("NONE", Integer.MAX_VALUE), /** Trace messages */
		TRACE("TRACE", 0), /** Info messages */
		INFO("INFO", 1), /** Error messages */
		ERROR("ERROR", 2, System.err);

		/** Textual representation of this level. */
		private final String name;
		/** The integer value of the level. */
		private final int value;
		/** Standard stream for this kind of message. */
		private PrintStream stream;

		private Level(String name, int value, PrintStream stream) {
			this.name = name;
			this.value = value;
			this.stream = stream;
		}

		private Level(String name, int value) {
			this(name, value, System.out);
		}

		/** @return the textual representation of this level */
		public String getName() {
			return name;
		}

		/** @return the integer value of the level */
		public int getValue() {
			return value;
		}

		/** @return the standard stream for this kind of message */
		public PrintStream getStream() {
			return stream;
		}
	}

	/**
	 * Sets the log level, the acceptance of messages, as found in {@link Level}.
	 * 
	 * @param level the level as found in {@link Level}
	 */
	public void setLogLevel(Level level) {
		this.level = level;
	}

	/**
	 * Logs and/or prints <code>message</code> with {@link Level} <code>level</code>.
	 * 
	 * @param level the message's level
	 * @param the message to log
	 */
	public void log(Level level, String message) {
		if (this.level.getValue() >= level.getValue()) level.stream.println(level.getName() + ": " + message);
	}

	public void error(String message, Throwable e) {
		if (level.getValue() >= Level.ERROR.getValue()) {
			log(Level.ERROR, message);
			e.printStackTrace(err);
		}
	}

	public void info(String message) {
		log(Level.INFO, message);
	}

	public void debug(String message) {
		log(Level.TRACE, message);
	}

	public void setPrintStreamForLevel(PrintStream stream, Level... levels) {
		for (Level level : levels)
			level.stream = stream;
	}

	public static class Log {
		private static final Logger LOG = new Logger();

		/**
		 * Sets the log level, the acceptance of messages, as found in {@link Level}.
		 * 
		 * @param level the level as found in {@link Level}
		 */
		public void setLogLevel(Level level) {
			LOG.setLogLevel(level);
		}

		/**
		 * Logs and/or prints <code>message</code> with {@link Level} <code>level</code>.
		 * 
		 * @param level the message's level
		 * @param the message to log
		 */
		public static void log(Level level, String message) {
			LOG.log(level, message);
		}

		public static void error(String message, Throwable e) {
			LOG.error(message, e);
		}

		public static void info(String message) {
			LOG.info(message);
		}

		public static void debug(String message) {
			LOG.debug(message);
		}
	}

	public static class StopWatch {
		private static long NANO_TO_MILLI = 1000000l;
		private long startTime;
		private long elapsedTime;

		public void start() {
			startTime = System.nanoTime();
			elapsedTime = -1l;
		}

		public long stop() {
			return elapsedTime = (System.nanoTime() - startTime) / NANO_TO_MILLI;
		}

		public long getStartTime() {
			return startTime / NANO_TO_MILLI;
		}

		public long getElapsedTime() {
			return elapsedTime == -1 ? (System.nanoTime() - startTime) / NANO_TO_MILLI : elapsedTime;
		}
	}

	/** OLD METHODS */

	/** @return a timestamp for this time */
	public static String timestamp() {
		return new Date().toString();
	}

	@Deprecated
	public static void startProfiling(String key) {
		startTimes.put(key, System.nanoTime());
	}

	@Deprecated
	public static long stopProfiling(String key) {
		if (!startTimes.containsKey(key)) throw new RuntimeException("no start time called " + key);
		return System.nanoTime() - startTimes.remove(key);
	}

	@Deprecated
	public static void drawPieChart(Graphics2D g2d, int x, int y, int width, int height, long time) {
		// TODO draw pie-chart of startTimes times
	}
}
