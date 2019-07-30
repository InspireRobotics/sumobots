package org.inspirerobotics.sumobots.robot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.Version;
import org.inspirerobotics.sumobots.robot.api.HardwareBackend;
import org.inspirerobotics.sumobots.robot.api.RobotBase;
import org.inspirerobotics.sumobots.robot.driverstation.Driverstation;
import org.inspirerobotics.sumobots.robot.driverstation.DriverstationServer;
import org.inspirerobotics.sumobots.robot.util.ExceptionHandlers;
import org.inspirerobotics.sumobots.robot.util.RobotLogAppender;

public class RobotManager {

    private static final Logger logger = LogManager.getLogger(RobotManager.class);

    private final Thread driverstationServer = DriverstationServer.createAndRun();
    private final RobotContainer container;
    private final Thread robotThread;

    private boolean running;

    public RobotManager(RobotBase robot, HardwareBackend backend) {
        this.container = new RobotContainer(robot, backend);
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
        running = true;

        while (running){
            checkRobotThread();

            Driverstation.getInstance().update();
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

    public static void manage(RobotBase robot, HardwareBackend backend){
        RobotLogAppender.init();
        Version.printInfo("Robot Manager");
        Thread.currentThread().setUncaughtExceptionHandler(ExceptionHandlers.nonRobotHandler());

        logger.info("Managing robot: " + robot.getClass().getName());
        logger.info("Hardware Platform: " + backend.getName());

        RobotManager manager = new RobotManager(robot, backend);

        manager.run();

        throw new IllegalStateException("Robot manager should self terminate!");
    }
}
