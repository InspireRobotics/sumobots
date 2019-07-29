package org.inspirerobotics.sumobots.robot.api;

public interface HardwareBackend {

    default PWMMotorController createMotorController(int pin){
        return new PWMMotorController(this, pin);
    }

    void init();

    void createPWM(int pin, int initialValue, int range);

    void writePWM(int pin, int value);

    void shutdown();

    String getName();
}
