package org.inspirerobotics.sumobots.robot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.ComponentState;
import org.inspirerobotics.sumobots.VisibleForTesting;
import org.inspirerobotics.sumobots.robot.api.HardwareBackend;
import org.inspirerobotics.sumobots.robot.api.RobotBase;
import org.inspirerobotics.sumobots.robot.driverstation.Driverstation;
import org.inspirerobotics.sumobots.robot.event.RobotEvent;
import org.inspirerobotics.sumobots.robot.event.RobotEventQueue;

import java.util.Optional;

public class RobotContainer implements Runnable {

    private static final Logger logger = LogManager.getLogger(RobotContainer.class);
    private final RobotBase robot;
    private final HardwareBackend hardwareBackend;

    private volatile boolean running;

    public RobotContainer(RobotBase base, HardwareBackend backend) {
        this.robot = base;
        hardwareBackend = backend;
    }

    @Override
    public void run() {
        init();
        logger.info("Robot Container started: running robot code!");
        running = true;

        runMainLoop();
        onShutdown();
    }

    private void runMainLoop() {
        while (running) {
            runRobotEvents();
            updateRobot();
        }
    }

    @VisibleForTesting
    void init() {
        logger.debug("Initializing robot container!");
        robot.init();
        hardwareBackend.init();
    }

    private void runRobotEvents() {
        Optional<RobotEvent> e;

        while ((e = RobotEventQueue.poll()).isPresent() && running) {
            e.get().run(this);
        }
    }

    public void onStateChange(ComponentState state) {
        switch(state){
            case ENABLED:
                robot.onEnable();
                break;
            case DISABLED:
                robot.onDisable();
                break;
            default:
        }
    }

    @VisibleForTesting
    void updateRobot() {
        switch (Driverstation.getInstance().getState()) {
            case DISABLED:
                robot.disablePeriodic();
                break;
            case ENABLED:
                robot.enablePeriodic();
                break;
            default:
                throw new IllegalStateException("Unknown variant: " + Driverstation.getInstance().getState());
        }
    }

    @VisibleForTesting
    void onShutdown() {
        logger.info("Shutting down robot!");

        robot.onShutdown();
        hardwareBackend.shutdown();
    }

    void stop() {
        logger.info("Stopping robot container!");
        running = false;
    }

    protected HardwareBackend getHardwareBackend() {
        return hardwareBackend;
    }
}
