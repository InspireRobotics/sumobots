package org.inspirerobotics.sumobots.driverstation.config;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.inspirerobotics.sumobots.SumobotsRuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConfigTests {

    @Test
    void configRobotIPDefaultNonNullTest() {
        Config config = new Config(new String[]{});

        Assertions.assertNotNull(config.getRobotIP());
    }

    @Test
    void configLogLevelDefaultNonNullTest() {
        Config config = new Config(new String[]{});

        Assertions.assertNotNull(config.getLogLevel());
    }

    @Test
    void setLogLevelBasicTest() {
        Config config = new Config(new String[]{});
        config.setLogLevel(Level.FATAL);

        Assertions.assertEquals(Level.FATAL, LogManager.getLogger("org.inspirerobotics.foo").getLevel());
    }

    @Test
    void argumentWithoutEqualsThrowsExceptionTest() {
        Assertions.assertThrows(SumobotsRuntimeException.class, () ->
                new Config(new String[]{"robotIP:localhost"}));
    }

    @Test
    void unknownArgumentThrowsExceptionTest() {
        Assertions.assertThrows(SumobotsRuntimeException.class, () ->
                new Config(new String[]{"foo=bar"}));
    }

    @Test
    void robotIPArgumentBasicTest() {
        Config config = new Config(new String[]{"robotIP=foobar"});

        Assertions.assertEquals("foobar", config.getRobotIP());
    }

    @Test
    void logLevelArgumentBasicTest() {
        Config config = new Config(new String[]{"logLevel=fatal"});

        Assertions.assertEquals(Level.FATAL, config.getLogLevel());
    }

    @Test
    void setRobotIPNullFailsTest() {
        Assertions.assertThrows(NullPointerException.class, () -> new Config(new String[0]).setRobotIP(null));
    }

    @Test
    void setLogLevelNullFailsTest() {
        Assertions.assertThrows(NullPointerException.class, () -> new Config(new String[0]).setLogLevel(null));
    }
}
