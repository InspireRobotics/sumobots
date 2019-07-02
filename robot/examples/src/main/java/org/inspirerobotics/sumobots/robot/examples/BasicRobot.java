package org.inspirerobotics.sumobots.robot.examples;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.robot.api.RobotBase;

public class BasicRobot implements RobotBase {

    private final Logger logger = LogManager.getLogger(BasicRobot.class);

    @Override
    public void init() {
        logger.info("Init!");
    }

    @Override
    public void onEnable() {
        logger.info("Robot has been enabled!");
    }

    @Override
    public void onDisable() {
        logger.info("Robot has been disabled!");
    }

    @Override
    public void disablePeriodic() {

    }

    @Override
    public void enablePeriodic() {

    }
}
