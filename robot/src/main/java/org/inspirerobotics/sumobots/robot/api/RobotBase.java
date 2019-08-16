package org.inspirerobotics.sumobots.robot.api;

import org.inspirerobotics.sumobots.packet.JoystickData;

public interface RobotBase {

    void init(HardwareBackend backend);

    default void onEnable(){};

    default void onDisable(){};

    void disablePeriodic();

    void enablePeriodic(JoystickData data);

    default void onShutdown(){}

}
