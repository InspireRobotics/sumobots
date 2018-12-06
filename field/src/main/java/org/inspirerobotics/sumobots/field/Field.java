package org.inspirerobotics.sumobots.field;

import org.inspirerobotics.sumobots.field.driverstation.DriverstationManager;
import org.inspirerobotics.sumobots.field.server.DriverstationServer;
import org.inspirerobotics.sumobots.field.web.WebServer;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class Field {

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
            System.out.println("Failed to start the field");
            e.printStackTrace();
        }

        System.out.println("Field has been started!");
    }

    private void run() {
        System.out.println("Running the field!");

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
        System.out.println("Stopping FMS");
        webServer.stop();
        dsServer.close();
        dsManager.closeConnections();
        System.out.println("FMS Stopped.");
        System.exit(0);
    }

    public static void main(String[] args) {
        Field application = new Field();
        application.run();
    }

}
