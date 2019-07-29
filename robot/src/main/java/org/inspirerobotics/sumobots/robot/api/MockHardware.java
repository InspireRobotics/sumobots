package org.inspirerobotics.sumobots.robot.api;

public class MockHardware implements HardwareBackend {

    private boolean initialized;
    private boolean shutdown;

    @Override
    public void init() {
        initialized = true;
    }

    @Override
    public void createPWM(int pin, int value, int range) {

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

    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public String getName() {
        return "Mock Hardware";
    }
}
