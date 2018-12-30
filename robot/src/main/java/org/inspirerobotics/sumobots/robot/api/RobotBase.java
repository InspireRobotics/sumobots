package org.inspirerobotics.sumobots.robot.api;

public interface RobotBase {

    void init();

    default void onEnable(){};

    default void onDisable(){};

    void disablePeriodic();

    void enablePeriodic();

    default void onShutdown(){}

}
