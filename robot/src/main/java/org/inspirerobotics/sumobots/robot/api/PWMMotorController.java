package org.inspirerobotics.sumobots.robot.api;

public class PWMMotorController {

    private final HardwareBackend hardware;
    private final int pin;

    PWMMotorController(HardwareBackend hardware, int pin) {
        this.hardware = hardware;
        this.pin = pin;

        hardware.createPWM(pin, 150, 200);
    }


    public void setPower(double percentPower){
        hardware.writePWM(pin, percentToValue(percentPower));
    }

    /**
     *
     * @param percentPower the power between -1 (full reverse) and 1 (full forwards)
     * @return the PWM value (between 100 and 200)
     */
    static int percentToValue(double percentPower) {
        percentPower /= 2;
        percentPower += 1.5;
        percentPower *= 10;

        return (int) percentPower;
    }
}
