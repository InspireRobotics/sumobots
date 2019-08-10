package org.inspirerobotics.sumobots.driverstation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.ComponentState;
import org.inspirerobotics.sumobots.driverstation.network.Connection;
import org.inspirerobotics.sumobots.driverstation.state.DriverstationStateManager;
import org.inspirerobotics.sumobots.driverstation.util.BackendEvent;
import org.inspirerobotics.sumobots.driverstation.util.BackendEventQueue;

import java.util.Optional;

public class BackendWorker implements Runnable{

    private static final Logger logger = LogManager.getLogger(BackendWorker.class);
    private static final int CONNECTION_RESET_TIME = 5000;

    private final DriverstationStateManager stateManager;
    private final Gui gui;

    private Optional<Connection> fieldConnection = Optional.empty();
    private Optional<Connection> robotConnection = Optional.empty();
    private int robotConnectionTimeout = 0;
    private int fieldConnectionTimeout = 0;

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
            updateRobotConnection();
        }
    }

    private void runEventsFromEventQueue() {
        Optional<BackendEvent> e;

        while((e = BackendEventQueue.poll()).isPresent()){
            e.get().run(this);
        }
    }

    private void updateFieldConnection(){
        if(fieldConnection.isPresent()){
            if(fieldConnection.get().isClosed()){
                fieldConnectionTimeout = CONNECTION_RESET_TIME;
                setFieldConnection(Optional.empty());
            }else{
                fieldConnection.get().update();
            }
        }else if(fieldConnectionTimeout >= 0){
            fieldConnectionTimeout--;
        }else{
            setFieldConnection(Connection.createForField());
        }
    }

    private void updateRobotConnection(){
        if(robotConnection.isPresent()){
            if(robotConnection.get().isClosed()){
                robotConnectionTimeout = CONNECTION_RESET_TIME;
                setRobotConnection(Optional.empty());
            }else{
                robotConnection.get().update();
            }
        }else if(robotConnectionTimeout >= 0){
            robotConnectionTimeout--;
        }else{
            setRobotConnection(Connection.createForRobot(gui));
        }
    }

    private void shutdown() {
        logger.info("Shutting down backend thread!");
        fieldConnection.ifPresent(conn -> conn.close("Driverstation closed"));
        robotConnection.ifPresent(conn -> conn.close("Driverstation closed"));
        logger.info("Backend thread shutdown!");
    }

    public void stopRunning(){
        running = false;
    }

    public void setJoystickStatus(boolean connected){
        this.stateManager.setJoystickStatus(connected);
    }

    void setFieldConnection(Optional<Connection> fieldConnection) {
        if(fieldConnection != this.fieldConnection){
            this.fieldConnection = fieldConnection;
            stateManager.attemptToChangeComponentState(ComponentState.DISABLED);
            return;
        }

        this.fieldConnection = fieldConnection;
    }

    void setRobotConnection(Optional<Connection> robotConnection) {
        if(robotConnection != this.robotConnection){
            this.robotConnection = robotConnection;
            stateManager.attemptToChangeComponentState(ComponentState.DISABLED);
            updateLogBasedOnConnection(robotConnection);

            return;
        }

        this.robotConnection = robotConnection;
    }

    private void updateLogBasedOnConnection(Optional<Connection> robotConnection) {
        if(robotConnection.isPresent()){
            gui.clearLog();
        }else{
            gui.log("-------------- \n\n ROBOT CONNECTION LOST!");
        }
    }

    public Optional<Connection> getRobotConnection() {
        return robotConnection;
    }

    public Optional<Connection> getFieldConnection() {
        return fieldConnection;
    }

    public DriverstationStateManager getStateManager() {
        return stateManager;
    }
}
