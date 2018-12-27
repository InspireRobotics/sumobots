package org.inspirerobotics.sumobots.robot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.robot.api.RobotBase;
import org.inspirerobotics.sumobots.robot.event.RobotEventQueue;

public class RobotManager {

    private static final Logger logger = LogManager.getLogger(RobotManager.class);

    private final RobotContainer container;
    private final Thread robotThread;

    public RobotManager(RobotBase robot) {
        this.container = new RobotContainer(robot);
        this.robotThread = createThread(container);

        this.robotThread.start();
    }

    private static Thread createThread(RobotContainer container) {
        Thread thread = new Thread(container);
        thread.setName("Robot Thread");
        thread.setDaemon(false);

        return thread;
    }

    private void run() {
        RobotEventQueue.add(robot -> {
            logger.info("Running on robot thread!");
        });

        waitFiveSeconds();

        stop();
    }

    private void stop() {
        logger.info("Stopping robot program!");
        container.stop();

        if(joinRobotThread()){
            logger.info("Robot program successfully shutdown!");
            System.exit(0);
        }else{
            logger.fatal("Robot thread failed to stop!");
            logger.fatal("Terminating JVM!");

            System.exit(100);
        }
    }

    private boolean joinRobotThread() {
        try{
            robotThread.join(2500);
        }catch (InterruptedException e){
            logger.error("Robot thread interrupted while shutting down: ", e);
        }

        return !robotThread.isAlive();
    }

    private void waitFiveSeconds() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void manage(RobotBase robot){
        logger.info("Managing robot: " + robot.getClass().getName());

        RobotManager manager = new RobotManager(robot);
        manager.run();
    }
}
