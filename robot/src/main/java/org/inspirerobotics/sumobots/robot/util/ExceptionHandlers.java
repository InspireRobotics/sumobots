package org.inspirerobotics.sumobots.robot.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExceptionHandlers {

    private static final Logger logger = LogManager.getLogger(ExceptionHandlers.class);

    public static Thread.UncaughtExceptionHandler robotHandler() {
        return (thread, exception) -> {
            logException(thread, exception);

            thread.interrupt();
        };
    }

    public static Thread.UncaughtExceptionHandler mainThreadHandler() {
        return (thread, exception) -> {
            logException(thread, exception);

            logger.fatal("Terminating JVM due to exception!");
            System.exit(100);
        };
    }
    private static void logException(Thread thread, Throwable e){
        logger.fatal("Uncaught Exception on thread " + thread.getName() + ":", e);
    }
}
