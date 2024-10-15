package ac.ku.oloo.utils;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.utils)
 * Created by: oloo
 * On: 15/10/2024. 20:20
 * Description:
 **/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {

    // Enum for log levels
    public enum LogLevel {
        INFO, WARN, ERROR, DEBUG
    }

    // Method to log the message with level, class, and method details
    private static void log(LogLevel level, String message) {
        // Capture the current stack trace to identify the caller
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        // The 3rd element refers to the method that called this logger method (adjust index as needed)
        StackTraceElement caller = stackTrace[3];
        String className = caller.getClassName();
        String methodName = caller.getMethodName();

        // Create a logger for the calling class
        Logger logger = LoggerFactory.getLogger(className);

        // Format the current date and time
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // Log the message with the level, timestamp, class, method, and message
        String logMessage = String.format("[%s] [%s] [%s.%s] %s",
                level, timestamp, className, methodName, message);

        // Log the message based on the level using SLF4J
        switch (level) {
            case INFO:
                logger.info(logMessage);
                break;
            case WARN:
                logger.warn(logMessage);
                break;
            case ERROR:
                logger.error(logMessage);
                break;
            case DEBUG:
                logger.debug(logMessage);
                break;
        }
    }

    // Convenience methods for different log levels
    public static void info(String message) {
        log(LogLevel.INFO, message);
    }

    public static void warn(String message) {
        log(LogLevel.WARN, message);
    }

    public static void error(String message) {
        log(LogLevel.ERROR, message);
    }

    public static void debug(String message) {
        log(LogLevel.DEBUG, message);
    }
}