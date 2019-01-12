package org.inspirerobotics.sumobots.robot.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.inspirerobotics.sumobots.VisibleForTesting;

public class RobotLog {

    @VisibleForTesting
    static void init(Appender appender){
        final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        final Configuration config = ctx.getConfiguration();

        appender.start();
        config.addAppender(appender);
        ctx.getRootLogger().addAppender(appender);
    }

    public static void init() {
        init(new RobotLogAppender());
    }
}
