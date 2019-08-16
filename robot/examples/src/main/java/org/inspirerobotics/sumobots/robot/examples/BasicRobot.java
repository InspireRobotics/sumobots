package org.inspirerobotics.sumobots.robot.examples;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.packet.JoystickData;
import org.inspirerobotics.sumobots.robot.api.HardwareBackend;
import org.inspirerobotics.sumobots.robot.api.PWMMotorController;
import org.inspirerobotics.sumobots.robot.api.RobotBase;

public class BasicRobot implements RobotBase {

    private final Logger logger = LogManager.getLogger(BasicRobot.class);

    private PWMMotorController motor;

    @Override
    public void init(HardwareBackend backend) {
        logger.info("Init!");
        motor = backend.createMotorController(5);
    }

    @Override
    public void onEnable() {
        logger.info("Robot has been enabled!");
        motor.setPower(1);
    }

    @Override
    public void onDisable() {
        logger.info("Robot has been disabled!");
        motor.setPower(0);
    }

    @Override
    public void disablePeriodic() {

    }

    @Override
    public void enablePeriodic(JoystickData data) {
        logger.info("Data: " + data.getLeftX());
    }
}
