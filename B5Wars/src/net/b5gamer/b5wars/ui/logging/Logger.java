package net.b5gamer.b5wars.ui.logging;

import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import org.apache.log4j.PatternLayout;

/**
 * Wrapper for org.apache.log4j.Logger to simplify application-wide configuration
 */
public final class Logger {

	private Logger() {
	}
	
	public static final Layout DEFAULT_LAYOUT = new PatternLayout("%m%n");

	private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getRootLogger();
	
	public static void addAppender(Appender newAppender) {
		logger.addAppender(newAppender);
	}
	
	public static Appender getAppender(java.lang.String name) {
		return logger.getAppender(name);
	}
	
	public static void removeAppender(Appender appender) {
		logger.removeAppender(appender);
	}
	
	public static void removeAppender(java.lang.String name) {
		logger.removeAppender(name);
	}
	
	public static void setAppender(Appender newAppender) {
		logger.removeAllAppenders();
		logger.addAppender(newAppender);
	}
			
	public static boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}
	
	public static void debug(java.lang.Object message) {
		logger.debug(message);
	}
		
	public static void debug(java.lang.Object message, java.lang.Throwable t) {
		logger.debug(message, t);
	}
	
	public static void error(java.lang.Object message) {
		logger.error(message);
	}
	
	public static void error(java.lang.Object message, java.lang.Throwable t) {
		logger.error(message, t);
	}
	
	public static void fatal(java.lang.Object message) {
		logger.fatal(message);		
	}
	
	public static void fatal(java.lang.Object message, java.lang.Throwable t) {
		logger.fatal(message, t);
	}

	public static boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}
	
	public static void info(java.lang.Object message) {
		logger.info(message);		
	}
	
	public static void info(java.lang.Object message, java.lang.Throwable t) {
		logger.info(message, t);
	}
	
	public static boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	public static void trace(java.lang.Object message) {
		logger.trace(message);		
	}
	
	public static void trace(java.lang.Object message, java.lang.Throwable t) {
		logger.trace(message, t);		
	}
	
	public static void warn(java.lang.Object message) {
		logger.warn(message);		
	}
	
	public static void warn(java.lang.Object message, java.lang.Throwable t) {
		logger.warn(message, t);
	}

}
