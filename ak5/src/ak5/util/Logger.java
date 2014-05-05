/**
 * 
 */
package ak5.util;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pwnedary
 */
public class Logger {
	private static final Map<String, Logger> loggers = new HashMap<>();
	
	/** The output stream for dumping the log out on */
	public PrintStream out = System.out;
	/** The output stream for dumping the errors out on */
	public PrintStream err = System.err;
	/** The acceptance of messages. */
	private Level level = Level.valueOf(System.getProperty("gamelib.default_log_level", "INFO"));
	private String name;

	public Logger() {
		this("");
	}

	public Logger(String name) {

	}

	public static Logger getLogger(String name) {
		return loggers.get(name);
	}
	
	public static Logger getLogger(Class<?> clazz) {
		return getLogger(clazz.getName());
	}

	public static enum Level {
		/** The <code>OFF</code> level is intended to turn off logging. */
		OFF("", Integer.MAX_VALUE),
		/** The <code>ERROR</code> level designates errors that might still allow the application to continue running. */
		ERROR("ERROR", 3),
		/** The <code>WARN</code> level designates potentially harmful situations. */
		WARN("WARNING", 2),
		/** The <code>INFO</code> level designates informational messages that highlight the progress of the application at coarse-grained level. */
		INFO("INFO", 1),
		/** The <code>DEBUG</code> level designates fine-grained informational events that are most useful to debug an application. */
		DEBUG("DEBUG", 0);

		/** Textual representation of this level. */
		private final String name;
		/** The integer value of the level. */
		private final int value;

		private Level(String name, int value) {
			this.name = name;
			this.value = value;
		}

		/** @return the textual representation of this level */
		public String getName() {
			return name;
		}

		/** @return the integer value of the level */
		public int getValue() {
			return value;
		}
	}

	/**
	 * Sets the log level, the acceptance of messages, as found in {@link Level}.
	 * 
	 * @param level the level as found in {@link Level}
	 */
	public Logger level(Level level) {
		this.level = level;
		return this;
	}

	/**
	 * Logs and/or prints <code>message</code> with {@link Level} <code>level</code>.
	 * 
	 * @param level the message's level
	 * @param the message to log
	 */
	public void log(Level level, String message) {
		if (this.level.getValue() >= level.getValue()) out.println(level.getName() + ": " + message);
	}

	public void log(Level level, String message, Throwable e) {
		if (this.level.getValue() >= level.getValue()) {
			PrintWriter builder = new PrintWriter(e == null ? out : err);
			builder.append(level.getName());
			builder.append(": ");
			builder.append(message);
			if (e != null) {
				builder.append(' ');
				e.printStackTrace(builder);
			}
			builder.flush();
		}
	}

	public void error(String message, Throwable e) {
		log(Level.ERROR, message, e);
	}

	public void info(String message) {
		log(Level.INFO, message);
	}

	public void debug(String message) {
		log(Level.DEBUG, message);
	}

	public static class Log {
		private static final Logger LOG = new Logger();

		/**
		 * Sets the log level, the acceptance of messages, as found in {@link Level}.
		 * 
		 * @param level the level as found in {@link Level}
		 */
		public void setLogLevel(Level level) {
			LOG.level(level);
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

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.METHOD })
	public @interface Try {
		String[] args() default {};

		String expect() default "";

		/**
		 * Default empty exception
		 */
		static class None extends Throwable {
			private static final long serialVersionUID = 1L;

			private None() {}
		}

		Class<? extends Throwable> expected() default None.class;
	}

	public static void main(String[] args) {
		if (args.length == 1) {
			try {
				String testClassName = args[0];
				Class<?> testClass = Class.forName(testClassName);

				for (Method method : testClass.getDeclaredMethods()) {
					Try attempt = method.getAnnotation(Try.class);
					if (attempt != null) {
						Object instance = Modifier.isStatic(method.getModifiers()) ? null : testClass.newInstance();
						method.invoke(instance, (Object[]) attempt.args());
					}
				}
			} catch (ReflectiveOperationException e) {
				e.printStackTrace();
			}

		}
	}

	/** OLD METHODS */

	/** @return a timestamp for this time */
	public static String timestamp() {
		return new Date().toString();
	}
}
