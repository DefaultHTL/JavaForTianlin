package org.tianlin.java.exercise6.game.utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Log {
	private static final String TAG = "Logger";
	private static final DateFormat LOG_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private static final DateFormat FILE_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd-hhmmss");
	private static final boolean DEBUG = true;
	private static final String LEVEL_DEBUG = "D";
	private static final String LEVEL_INFO = "I";
	private static final String LEVEL_WARNING = "W";
	private static final String LEVEL_ERROR = "E";

	private static final String LOG_DIR = "log";
	private static FileLogger fileLogger = null;

	private static LogType logType = LogType.Server;
	private static final int LOG_RETRY_TIMES = 3;

	/*
	 * Record debug message. It's printed to screen only if DEBUG is true.
	 */
	public static void d(String TAG, String format, Object... args) {
		if (DEBUG) {
			log2Screen(buildLogString(LEVEL_DEBUG, TAG, format, args));
			log2file(buildLogString(LEVEL_DEBUG, TAG, format, args));
		}
	}

	/*
	 * Record game info. Always use this to record game routines.
	 */
	public static void i(String TAG, String format, Object... args) {
		log2Screen(buildLogString(LEVEL_INFO, TAG, format, args));
		log2file(buildLogString(LEVEL_INFO, TAG, format, args));
	}

	/*
	 * Record running warnings.
	 */
	public static void w(String TAG, String format, Object... args) {
		log2Screen(buildLogString(LEVEL_WARNING, TAG, format, args));
		log2file(buildLogString(LEVEL_WARNING, TAG, format, args));
	}

	/*
	 * Record errors.
	 */
	public static void e(String TAG, String format, Object... args) {
		log2Screen(buildLogString(LEVEL_ERROR, TAG, format, args));
		log2file(buildLogString(LEVEL_ERROR, TAG, format, args));
	}

	/*
	 * Server or client side should initial once in one game.
	 */
	public static void init(LogType type) {
		logType = type;
		if (fileLogger != null) {
			w(TAG, "File logger already initialized!");
			return;
		}
		String fileName = String.format("%s-%s.txt", type.toString(), FILE_DATE_FORMAT.format(new Date()));
		i(TAG, "Initializing file logger: %s", fileName);
		fileLogger = new FileLogger(fileName);
		new Thread(fileLogger).start();
	}

	/*
	 * Server or client side must shut logging thread down.
	 */
	public static void uninit() {
		try {
			/*
			 * [WORKAROUND] wait a bit time for logging thread to finish
			 * work......
			 */
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e(TAG, "Main thread is interrupted!");
		}
		if (fileLogger != null) {
			i(TAG, "Uninitializing file logger: %s", fileLogger.filename);
			fileLogger.shutdown = true;
			fileLogger = null;
		}
	}

	public static void runStressTest() {
		/*
		 * make sure threads will not fight with each other
		 */
		for (int i = 0; i < 5; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						for (int i = 1; i <= 50; i++) {
							Log.i(TAG, "[StressTest] I'm %s, count %d", Thread.currentThread().getName(), i);
							Thread.sleep(10);
						}
						Thread.sleep(100);
						for (int i = 1; i <= 50; i++) {
							Log.w(TAG, "[StressTest] I'm %s again, count %d", Thread.currentThread().getName(), i);
							Thread.sleep(10);
						}
					} catch (InterruptedException e) {
						e(TAG, "[StressTest] %s is interrupted!", Thread.currentThread().getName());
					}
					Log.d(TAG, "[StressTest] I'm %s and I'm going to die.", Thread.currentThread().getName());
				}
			}).start();
		}

		try {
			/*
			 * sleep 500 ms here to abort file log
			 */
			Thread.sleep(500);
			uninit();
			/*
			 * re-open for normal logging
			 */
			Thread.sleep(2000);
			init(logType);
			/*
			 * sleep 500 ms here to prevend closing
			 */
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e(TAG, "[StressTest] Main thread is interrupted!");
		}
	}

	private static class FileLogger implements Runnable {
		private static final String TAG = "FileLogger";
		private String filename = null;
		private BlockingQueue<String> messageQueue = new LinkedBlockingQueue<String>();
		private boolean shutdown = false;

		public FileLogger(String filename) {
			this.filename = filename;
		}

		@Override
		public void run() {
			File log = createAndOpenFile();
			if (log == null) {
				w(TAG, "Open log file %s failed, log to file disabled!", filename);
				fileLogger = null;
				messageQueue.clear();
				return;
			}

			FileWriter writer = null;
			try {
				writer = new FileWriter(log);
				while (!shutdown) {
					writer.write(messageQueue.take() + "\n");
					writer.flush(); // flush log immediately
				}
			} catch (InterruptedException | IOException e) {
				e(TAG, "Error logging to log file, exception: %s!!", e.getMessage());
				fileLogger = null;
				messageQueue.clear();
				return;
			} finally {
				try {
					if (null != writer)
						writer.close();
				} catch (IOException e) {
					e(TAG, "Error closing log file!!");
					fileLogger = null;
					messageQueue.clear();
				}
			}
		}

		/*
		 * Open file for logging. File name is like Server-20150811-061322.txt.
		 */
		private File createAndOpenFile() {
			File dir = new File(LOG_DIR);
			if (!dir.exists()) {
				if (!dir.mkdir()) {
					e(TAG, "Cannot create log directory at %s, please check access rights!", dir.getAbsolutePath());
					return null;
				}
				w(TAG, "Create log directory at %s", dir.getAbsolutePath());
			}

			File logFile = new File(dir, filename);
			if (logFile.exists()) {
				// should never happens
				if (logFile.delete()) {
					w(TAG, "Delete duplicated log file at %s, how could this happen?!", logFile.getAbsolutePath());
				} else {
					e(TAG, "Cannot delete duplicated log file, please check access rights!");
					return null;
				}
			}

			try {
				logFile.createNewFile();
				i(TAG, "Create log file %s", logFile.getAbsolutePath());
			} catch (IOException e) {
				e(TAG, "Cannot create log file at %s, please check access rights!", logFile.getAbsolutePath());
				return null;
			}

			return logFile;
		}

		/*
		 * Run on other thread.
		 */
		public void log(String message) {
			int retry = 0;
			while (retry++ < LOG_RETRY_TIMES) {
				if (messageQueue.offer(message))
					return;
				log2Screen(buildLogString(LEVEL_DEBUG, TAG, "Failed adding message to queue, retry: %d", retry));
			}
			log2Screen(buildLogString(LEVEL_WARNING, TAG, "Failed adding message to queue, it will be ignored."));
		}
	}

	/*
	 * Print the log message on screen.
	 */
	private static void log2Screen(String msg) {
		if (logType != LogType.NoConsole)
			System.out.println(msg);
	}

	/*
	 * Print the log message to log file if it has been initialized.
	 */
	private static void log2file(String msg) {
		if (fileLogger != null) {
			fileLogger.log(msg);
		}
	}

	/*
	 * Build log message according to level, tag and user string.
	 */
	private static String buildLogString(String level, String TAG, String format, Object... args) {
		StringBuilder builder = new StringBuilder();
		builder.append('[').append(LOG_DATE_FORMAT.format(new Date())).append("]/").append(level).append('/')
				.append(Thread.currentThread().getName()).append("  -  [").append(TAG).append(']')
				.append(String.format(format, args));
		return builder.toString();
	}
}
