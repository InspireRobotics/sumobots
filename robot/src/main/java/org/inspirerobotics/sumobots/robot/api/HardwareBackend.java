package org.inspirerobotics.sumobots.robot.api;

public interface HardwareBackend {

    void init();

    void createPWM(int pin, int value);

    void writePWM(int pin, int value);

    void shutdown();

    String getName();
}
