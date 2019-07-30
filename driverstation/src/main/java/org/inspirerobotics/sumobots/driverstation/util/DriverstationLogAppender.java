package org.inspirerobotics.sumobots.driverstation.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.inspirerobotics.sumobots.HighPriorityLogFilter;
import org.inspirerobotics.sumobots.VisibleForTesting;
import org.inspirerobotics.sumobots.driverstation.Gui;

public class DriverstationLogAppender extends AbstractAppender {

    private final Gui gui;

    public DriverstationLogAppender(Gui gui) {
        super(LogManager.ROOT_LOGGER_NAME, new HighPriorityLogFilter(), null);

        this.gui = gui;
    }

    @Override
    public void append(LogEvent event) {
        String message = event.getMessage().getFormattedMessage();
        Level level = event.getLevel();

        if(level.equals(Level.WARN)) {
            gui.log("[DS][WARN] " + message);
        } else if(level.equals(Level.ERROR) || level.equals(Level.FATAL)) {
            gui.log("[DS][ERROR] " + message);
        }
    }

    @VisibleForTesting
    public static void init(Gui gui) {
        init(new DriverstationLogAppender(gui));
    }

    public static void init(Appender appender){
        final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        final Configuration config = ctx.getConfiguration();

        appender.start();
        config.addAppender(appender);
        ctx.getRootLogger().addAppender(appender);
    }
}
