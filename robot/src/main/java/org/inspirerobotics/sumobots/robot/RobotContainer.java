package org.inspirerobotics.sumobots.robot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.robot.api.RobotBase;
import org.inspirerobotics.sumobots.robot.event.RobotEvent;
import org.inspirerobotics.sumobots.robot.event.RobotEventQueue;

import java.util.Optional;

public class RobotContainer implements Runnable{

    private static final Logger logger = LogManager.getLogger(RobotContainer.class);
    private final RobotBase robot;

    private volatile boolean running;

    public RobotContainer(RobotBase base) {
        this.robot = base;
    }

    @Override
    public void run() {
        logger.info("Robot Container started: running robot code!");
        running = true;

        while (true){
            runRobotEvents();
        }

//        onShutdown();
    }

    private void runRobotEvents() {
        Optional<RobotEvent> e;

        while((e  = RobotEventQueue.poll()).isPresent() && running){
            e.get().run(this);
        }
    }

    private void onShutdown() {
        logger.info("Shutting down robot!");

        robot.onShutdown();
    }

    void stop() {
        logger.info("Stopping robot container!");
        running = false;
    }
}
