package org.inspirerobotics.sumobots.robot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.robot.api.RobotBase;
import org.inspirerobotics.sumobots.robot.event.RobotEventQueue;
import org.inspirerobotics.sumobots.robot.util.ExceptionHandlers;

public class RobotManager {

    private static final Logger logger = LogManager.getLogger(RobotManager.class);

    private final RobotContainer container;
    private final Thread robotThread;

    private boolean running;

    public RobotManager(RobotBase robot) {
        this.container = new RobotContainer(robot);
        this.robotThread = createThread(container);

        this.robotThread.start();
    }

    private static Thread createThread(RobotContainer container) {
        Thread thread = new Thread(container);
        thread.setName("Robot Thread");
        thread.setDaemon(false);
        thread.setUncaughtExceptionHandler(ExceptionHandlers.robotHandler());

        return thread;
    }

    private void run() {
        RobotEventQueue.add(robot -> {
            logger.info("Running on robot thread!");
        });

        long endTime = System.currentTimeMillis() + 5000;
        running = true;

        while (running && endTime > System.currentTimeMillis()){
            checkRobotThread();
        }

        stop();
    }

    private void checkRobotThread() {
        if(robotThread.isInterrupted()){
            logger.error("Robot thread interrupted? Terminating JVM...");

            stopJVM(false);
        }
    }

    public void stop() {
        logger.info("Stopping robot program!");
        container.stop();

        boolean robotStopped = joinRobotThread();
        stopJVM(robotStopped);
    }

    private boolean joinRobotThread() {
        try{
            robotThread.join(1000);
        }catch (InterruptedException e){
            logger.error("Robot thread interrupted while shutting down: ", e);
        }

        return !robotThread.isAlive();
    }

    private void stopJVM(boolean successful) {
        if(successful){
            logger.info("Robot program ended successfully!");
            System.exit(0);
        }else{
            logger.fatal("Robot program ended unsuccessfully!");
            logger.fatal("Terminating JVM!");

            System.exit(100);
        }
    }

    public static void manage(RobotBase robot){
        logger.info("Managing robot: " + robot.getClass().getName());
        Thread.currentThread().setUncaughtExceptionHandler(ExceptionHandlers.mainThreadHandler());

        RobotManager manager = new RobotManager(robot);
        manager.run();
    }
}
