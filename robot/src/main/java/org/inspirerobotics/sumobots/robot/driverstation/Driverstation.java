package org.inspirerobotics.sumobots.robot.driverstation;

import org.inspirerobotics.sumobots.ComponentState;
import org.inspirerobotics.sumobots.VisibleForTesting;

import java.util.Optional;

public class Driverstation {

    private static final Driverstation instance = new Driverstation();

    private Optional<DriverstationConnection> connection = Optional.empty();
    private ComponentState state = ComponentState.DISABLED;

    @VisibleForTesting
    Driverstation() {
    }

    public void update(){
        connection.ifPresent(this::updateConnection);
    }

    private synchronized void updateConnection(DriverstationConnection connection) {
        if(connection.isClosed()){
            setConnection(Optional.empty());
            return;
        }

        connection.updateConnection();
    }

    void setConnection(Optional<DriverstationConnection> connection) {
        synchronized (this.connection) {
            this.connection = connection;
        }
    }

    public boolean isConnected(){
        synchronized (connection){
            return connection.isPresent();
        }
    }

    void setState(ComponentState state) {
        synchronized (this.state){
            this.state = state;
        }
    }

    public ComponentState getState() {
        synchronized (state){
            return state;
        }
    }

    @VisibleForTesting
    Optional<DriverstationConnection> getConnection() {
        return connection;
    }

    public static Driverstation getInstance() {
        if(instance == null){
            synchronized (Driverstation.instance){
                return instance;
            }
        }

        return instance;
    }
}
