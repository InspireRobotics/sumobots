package org.inspirerobotics.sumobots.robot.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LogEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RobotLogTests {

    @Test
    void robotLogInitTest() {
        TestAppender appender = new TestAppender();
        RobotLogAppender.init(appender);

        LogManager.getRootLogger().info("Foo");

        Assertions.assertEquals("Foo", appender.getLastMessage());
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
