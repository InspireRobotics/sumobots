package org.inspirerobotics.sumobots.field;

import org.inspirerobotics.sumobots.ControlSystemComponent;
import org.inspirerobotics.sumobots.field.server.DriverstationServer;
import org.inspirerobotics.sumobots.field.web.WebServer;
import org.inspirerobotics.sumobots.packet.Packet;
import org.inspirerobotics.sumobots.socket.SocketPipe;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class Field {

    private WebServer webServer;
    private DriverstationServer dsServer;

    public Field() {
        start();
    }

    private void start() {
        try {
            this.webServer = new WebServer();
            this.webServer.start();
            this.dsServer = DriverstationServer.create();
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
        }

        stop();
    }

    private void newDriverstationConnection(SocketChannel socketChannel) {
        SocketPipe pipe = new SocketPipe(socketChannel);

        for(int i = 0; i < 500; i++){
            Packet packet = Packet.create(
                    "hello " + i,
                    ControlSystemComponent.FIELD_SERVER,
                    ControlSystemComponent.DRIVER_STATION
            );

            pipe.sendPacket(packet);
        }
        pipe.close();
    }

    private void stop() {
        System.out.println("Stopping FMS");
        webServer.stop();
        dsServer.close();
        System.out.println("FMS Stopped..");
        System.exit(0);
    }

    public static void main(String[] args) {
        Field application = new Field();
        application.run();
    }

}
