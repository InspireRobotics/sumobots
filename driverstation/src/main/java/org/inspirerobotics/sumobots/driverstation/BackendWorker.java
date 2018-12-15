package org.inspirerobotics.sumobots.driverstation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.driverstation.field.FieldConnection;
import org.inspirerobotics.sumobots.driverstation.util.BackendEvent;
import org.inspirerobotics.sumobots.driverstation.util.BackendEventQueue;

import java.util.Optional;

public class BackendWorker implements Runnable{

    private static final Logger logger = LogManager.getLogger(BackendWorker.class);

    private final Gui gui;

    private Optional<FieldConnection> fieldConnection = Optional.empty();
    private volatile boolean running;

    public BackendWorker(Gui gui) {
        this.gui = gui;
    }

    public void run(){
        beforeRun();
        runMainLoop();
        shutdown();
    }

    private void beforeRun() {
        running = true;

        logger.info("Backend thread started!");
    }

    private void runMainLoop() {
        while (running){
            runEventsFromEventQueue();
            updateFieldConnection();
        }
    }

    private void runEventsFromEventQueue() {
        Optional<BackendEvent> e;

        while((e = BackendEventQueue.poll()).isPresent()){
            e.get().run(this);
        }
    }

    private void updateFieldConnection() {
        if(fieldConnection.isPresent()){
            fieldConnection.get().update();
        }else{
            fieldConnection = FieldConnection.connectToField();
        }
    }

    private void shutdown() {
        logger.info("Shutting down backend thread!");
        fieldConnection.ifPresent(FieldConnection::close);
        logger.info("Backend thread shutdown!");
    }

    public void stopRunning(){
        running = false;
    }

}
