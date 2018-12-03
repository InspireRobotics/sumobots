package org.inspirerobotics.sumobots.field;

import org.inspirerobotics.sumobots.Version;
import org.inspirerobotics.sumobots.field.server.DriverstationServer;
import org.inspirerobotics.sumobots.field.web.WebServer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Field {

    private WebServer webServer;
    private DriverstationServer dsServer;

    public Field() {
        start();
    }

    private void start() {
        try{
            this.webServer = new WebServer();
            this.webServer.start();
            this.dsServer = DriverstationServer.create();
        }catch (IOException e){
            System.out.println("Failed to start the field");
            e.printStackTrace();
        }

        System.out.println("Field has been started!");
    }

    private void run() {
        System.out.println("Running the field!");

        while (webServer.isAlive()){
            dsServer.acceptNext().ifPresent(this::newDriverstationConnection);
        }

        stop();
    }

    private void newDriverstationConnection(SocketChannel socketChannel) {
        System.out.println("Handling new DS Connection!");
        ByteBuffer buffer = ByteBuffer.wrap(("FMS: " + Version.VERSION + "\n").getBytes(StandardCharsets.UTF_8));

        try {
            socketChannel.configureBlocking(true);
            socketChannel.write(buffer);
            socketChannel.socket().close();
        }catch (IOException e){
            e.printStackTrace();
        }
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
