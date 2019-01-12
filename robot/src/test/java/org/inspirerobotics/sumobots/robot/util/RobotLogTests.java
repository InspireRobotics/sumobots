package org.inspirerobotics.sumobots.robot.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RobotLogTests {

    @Test
    void robotLogInitTest() {
        TestAppender appender = new TestAppender();
        RobotLog.init(appender);

        LogManager.getRootLogger().info("Foo");

        Assertions.assertEquals("Foo", appender.getLastMessage());
    }

    @Test
    void filterInfoTest(){
        filterTest(Level.INFO, Filter.Result.ACCEPT);
    }

    @Test
    void filterFatalTest(){
        filterTest(Level.FATAL, Filter.Result.ACCEPT);
    }

    @Test
    void filterErrorTest(){
        filterTest(Level.ERROR, Filter.Result.ACCEPT);
    }

    @Test
    void filterWarningTest(){
        filterTest(Level.WARN, Filter.Result.ACCEPT);
    }

    @Test
    void filterDebugTest(){
        filterTest(Level.DEBUG, Filter.Result.DENY);
    }

    @Test
    void filterTraceTest(){
        filterTest(Level.TRACE, Filter.Result.DENY);
    }

    private void filterTest(Level level, Filter.Result expected){
        Assertions.assertEquals(expected, RobotLogFilter.filter(level));
    }

}
class TestAppender extends RobotLogAppender{

    private String lastMessage;

    @Override
    public void append(LogEvent event) {
        lastMessage = event.getMessage().getFormattedMessage();
    }

    public String getLastMessage() {
        return lastMessage;
    }
}
