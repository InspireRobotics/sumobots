package org.inspirerobotics.sumobots.field;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.field.driverstation.DriverstationManager;
import org.inspirerobotics.sumobots.field.server.DriverstationServer;
import org.inspirerobotics.sumobots.field.web.WebServer;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class Field {

    public static final int FAILED_EXIT_CODE = 1;
    private static final Logger logger = LogManager.getLogger(Field.class);

    private WebServer webServer;
    private DriverstationServer dsServer;
    private DriverstationManager dsManager;

    public Field() {
        start();
    }

    private void start() {
        try {
            this.webServer = new WebServer();
            this.webServer.start();
            this.dsServer = DriverstationServer.create();
            this.dsManager = new DriverstationManager();
        } catch (IOException e) {
            logger.fatal("Failed to start the field", e);
            System.exit(FAILED_EXIT_CODE);
        }

        logger.info("Field has been started!");
    }

    private void run() {
        logger.info("Running the field!");

        while (webServer.isAlive()) {
            dsServer.acceptNext().ifPresent(this::newDriverstationConnection);
            dsManager.update();
        }

        stop();
    }

    private void newDriverstationConnection(SocketChannel socketChannel) {
        dsManager.manageNewConnection(socketChannel);
    }

    private void stop() {
        logger.info("Stopping FMS");
        webServer.stop();
        dsServer.close();
        dsManager.closeConnections();
        logger.info("FMS Stopped.");
        System.exit(0);
    }

    public static void main(String[] args) {
        Field application = new Field();
        application.run();
    }

}
