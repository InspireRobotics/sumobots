package org.inspirerobotics.sumobots.robot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.robot.api.RobotBase;

public class RobotManager {

    private static final Logger logger = LogManager.getLogger(RobotManager.class);

    public static void manage(RobotBase robot){
        logger.info("Managing robot: " + robot.getClass().getName());

        robot.init();
        robot.onDisable();
        robot.onEnable();
    }

}
