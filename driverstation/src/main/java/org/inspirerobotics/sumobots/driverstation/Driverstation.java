package org.inspirerobotics.sumobots.driverstation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.driverstation.field.FieldConnection;

import java.util.Optional;

public class Driverstation {

    private static final Logger logger = LogManager.getLogger(Driverstation.class);

    private Optional<FieldConnection> fieldConnection = Optional.empty();

    private volatile boolean running;

    public void run(){
        start();
        runMainLoop();
        shutdown();
    }

    private void start() {
        running = true;

        logger.info("Driverstation started!");
    }

    private void runMainLoop() {
        while (running){
            if(fieldConnection.isPresent()){
                fieldConnection.get().update();
            }else{
                fieldConnection = FieldConnection.connectToField();
            }
        }
    }

    private void shutdown() {
        logger.info("Shutting down driverstation!");
        fieldConnection.ifPresent(FieldConnection::close);
        logger.info("Driverstation shutdown! Exiting JVM...");
        System.exit(0);
    }

    public static void main(String[] args){
        new Driverstation().run();
    }

}
