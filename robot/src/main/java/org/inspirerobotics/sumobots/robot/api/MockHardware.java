package org.inspirerobotics.sumobots.robot.api;

public class MockHardware implements HardwareBackend {

    private boolean initalized;
    private boolean shutdown;

    @Override
    public void init() {
        initalized = true;
    }

    @Override
    public void createPWM(int pin, int value) {

    }

    @Override
    public void writePWM(int pin, int value) {

    }

    @Override
    public void shutdown() {
        shutdown = true;
    }

    public boolean isShutdown() {
        return shutdown;
    }

    public boolean isInitalized() {
        return initalized;
    }

    @Override
    public String getName() {
        return "Mock Hardware";
    }
}
