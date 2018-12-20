package org.inspirerobotics.sumobots.driverstation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.ComponentState;
import org.inspirerobotics.sumobots.driverstation.field.FieldConnection;
import org.inspirerobotics.sumobots.driverstation.state.DriverstationMode;
import org.inspirerobotics.sumobots.driverstation.state.DriverstationState;
import org.inspirerobotics.sumobots.driverstation.state.DriverstationStateManager;
import org.inspirerobotics.sumobots.driverstation.util.BackendEvent;
import org.inspirerobotics.sumobots.driverstation.util.BackendEventQueue;

import java.util.Optional;

public class BackendWorker implements Runnable{

    private static final Logger logger = LogManager.getLogger(BackendWorker.class);

    private final DriverstationStateManager stateManager;
    private final Gui gui;

    private Optional<FieldConnection> fieldConnection = Optional.empty();
    private volatile boolean running;

    public BackendWorker(Gui gui) {
        this.gui = gui;
        this.stateManager = new DriverstationStateManager(gui, this);
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

            fieldConnection.filter(FieldConnection::isClosed).ifPresent(this::closeFieldConnection);
        }else{
            FieldConnection.connectToField().ifPresent(this::onFieldConnected);
        }
    }

    private void closeFieldConnection(FieldConnection connection) {
        connection.close();
        this.fieldConnection = Optional.empty();

        stateManager.attemptToDisable();
    }

    private void onFieldConnected(FieldConnection connection) {
        this.fieldConnection = Optional.of(connection);
        DriverstationState state = new DriverstationState(DriverstationMode.REGULAR, ComponentState.DISABLED);
        state.setFieldConnected(true);

        stateManager.setCurrentState(state);
    }

    private void shutdown() {
        logger.info("Shutting down backend thread!");
        fieldConnection.ifPresent(FieldConnection::close);
        logger.info("Backend thread shutdown!");
    }

    public void stopRunning(){
        running = false;
    }

    public Optional<FieldConnection> getFieldConnection() {
        return fieldConnection;
    }

    public DriverstationStateManager getStateManager() {
        return stateManager;
    }
}
