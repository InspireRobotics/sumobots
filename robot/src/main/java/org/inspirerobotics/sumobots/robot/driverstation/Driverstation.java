package org.inspirerobotics.sumobots.robot.driverstation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.ComponentState;
import org.inspirerobotics.sumobots.VisibleForTesting;

import java.util.Optional;

public class Driverstation {

    private static final Driverstation instance = new Driverstation();
    private static final Logger logger = LogManager.getLogger(Driverstation.class);

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
            Driverstation.reportInfo("New State: " + state);
            this.state = state;
        }
    }

    public ComponentState getState() {
        synchronized (state){
            return state;
        }
    }

    public static void reportError(String message){
        sendLog(report("ERROR", message));
    }
    public static void reportWarning(String message){
        sendLog(report("WARN", message));
    }
    public static void reportInfo(String message){
        sendLog(report("INFO", message));
    }

    private static void sendLog(String message){
        getInstance().getConnection().ifPresent(conn -> {
            if(conn.getPipe().getSocket().isConnected()){
                conn.sendLog(message);
            }
        });
    }

    static String report(String type, String desc){
        return "[" + type + "] " + desc;
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
