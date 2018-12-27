package org.inspirerobotics.sumobots.robot.examples;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.robot.RobotManager;

public class Launcher {

    private static final Logger logger = LogManager.getLogger(Launcher.class);

    public static void main(String[] args){
        logger.info("Launcher started.... Starting robot manager!");

        RobotManager.manage(new BasicRobot());
    }
}
