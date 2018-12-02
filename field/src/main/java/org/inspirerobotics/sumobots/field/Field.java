package org.inspirerobotics.sumobots.field;

import org.inspirerobotics.sumobots.field.web.WebServer;

import java.io.IOException;

public class Field {

    private final WebServer server;

    public Field() {
        this.server = new WebServer();
    }

    private void start() {
        try{
            server.start();
        }catch (IOException e){
            System.out.println("Failed to start the web web");
            e.printStackTrace();
        }

        System.out.println("Field has been started!");
    }

    private void run() {
        System.out.println("Running the field!");
    }

    public static void main(String[] args) {
        Field application = new Field();
        application.start();
        application.run();
    }

}
