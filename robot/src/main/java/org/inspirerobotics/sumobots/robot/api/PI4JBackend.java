package org.inspirerobotics.sumobots.robot.api;

import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.SoftPwm;

import java.util.ArrayList;

public class PI4JBackend implements HardwareBackend {

    private final ArrayList<Integer> pins = new ArrayList<>();

    @Override
    public void init() {
        Gpio.wiringPiSetup();
    }

    @Override
    public void createPWM(int pin, int value, int range) {
        pins.add(pin);

        SoftPwm.softPwmCreate(pin, value, range);
    }

    @Override
    public void writePWM(int pin, int value) {
        SoftPwm.softPwmWrite(pin, value);
    }

    @Override
    public void shutdown() {
        pins.forEach(SoftPwm::softPwmStop);
    }

    @Override
    public String getName() {
        return "PI4J";
    }
}
