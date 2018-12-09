package org.inspirerobotics.sumobots.field;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.field.driverstation.DriverstationManager;
import org.inspirerobotics.sumobots.field.driverstation.DriverstationThread;
import org.inspirerobotics.sumobots.field.driverstation.DriverstationServer;
import org.inspirerobotics.sumobots.field.web.WebServer;

import java.io.IOException;

public class Field {

    public static final int FAILED_EXIT_CODE = 1;
    private static final Logger logger = LogManager.getLogger(Field.class);

    private WebServer webServer;
    private DriverstationServer dsServer;
    private DriverstationManager dsManager;
    private DriverstationThread driverstationThread;

    private volatile boolean running = true;

    public Field() {
        start();
    }

    private void start() {
        try {
            this.webServer = new WebServer(this);
            this.webServer.start();
            this.dsServer = DriverstationServer.create();
            this.dsManager = new DriverstationManager();
        } catch (IOException e) {
            logger.fatal("Failed to start the field", e);
            System.exit(FAILED_EXIT_CODE);
        }

        startDriverstationServerThread();
        logger.info("Field has been started!");
    }

    private void startDriverstationServerThread() {
        driverstationThread = new DriverstationThread(this);
        driverstationThread.setName("Driverstation Manager");
        driverstationThread.setDaemon(false);
        driverstationThread.start();
    }

    public synchronized void stop() {
        running = false;
        logger.info("Stopping FMS");
        webServer.stop();
        waitForStop();
        logger.info("FMS Stopped.");
        System.exit(0);
    }

    private void waitForStop() {
        try{
            driverstationThread.join(5000);
            logger.debug("Driverstation thread joined successfully!");
        }catch (InterruptedException e){
            logger.warn("Driverstation thread interrupted while closing!");
        }
    }

    public static void main(String[] args) {
        Thread.currentThread().setName("Main Thread");
        new Field();
    }

    public DriverstationManager getDsManager() {
        return dsManager;
    }

    public DriverstationServer getDsServer() {
        return dsServer;
    }

    public boolean isRunning() {
        return running;
    }

    public WebServer getWebServer() {
        return webServer;
    }
}
