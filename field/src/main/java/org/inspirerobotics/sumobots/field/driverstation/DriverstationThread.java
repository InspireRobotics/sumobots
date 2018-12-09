package org.inspirerobotics.sumobots.field.driverstation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.SumobotsRuntimeException;
import org.inspirerobotics.sumobots.field.Field;

public class DriverstationThread extends Thread {

    private final Logger logger = LogManager.getLogger(DriverstationThread.class);
    private final Field field;

    public DriverstationThread(Field field) {
        this.field = field;
    }

    @Override
    public void run() {
        DriverstationServer dsServer = field.getDsServer();
        DriverstationManager dsManager = field.getDsManager();

        while(field.isRunning()){
            dsServer.acceptNext().ifPresent(dsManager::manageNewConnection);
            dsManager.update();
        }

        shutdown();
    }

    private void shutdown() {
        logger.info("Shutting down driver station thread!");
        try{
            field.getDsServer().close();
            field.getDsManager().closeConnections();
        }catch (SumobotsRuntimeException e){
            logger.error("Exception thrown while terminating driverstation thread: " + e);
        }

        logger.info("Driverstation thread shutdown!");
    }
}
