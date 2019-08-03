package org.inspirerobotics.sumobots.driverstation.state;

import javafx.application.Platform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.ComponentState;
import org.inspirerobotics.sumobots.FmsComponent;
import org.inspirerobotics.sumobots.driverstation.BackendWorker;
import org.inspirerobotics.sumobots.driverstation.Gui;
import org.inspirerobotics.sumobots.packet.Packet;
import org.inspirerobotics.sumobots.packet.PacketFactory;
import org.inspirerobotics.sumobots.packet.PacketPath;

import java.util.Objects;

public class DriverstationStateManager {

    private static final Logger logger = LogManager.getLogger(DriverstationState.class);
    private final Gui gui;
    private final BackendWorker backend;
    private DriverstationState currentState;

    public DriverstationStateManager(Gui gui, BackendWorker backend) {
        Objects.requireNonNull(gui, "Gui cannot be null!");
        Objects.requireNonNull(backend, "Backend worker cannot be null");

        this.gui = gui;
        this.backend = backend;

        setCurrentState(new DriverstationState(DriverstationMode.REGULAR, ComponentState.DISABLED));
    }

    public void setCurrentState(DriverstationState currentState) {
        Objects.requireNonNull(gui, "New state cannot be null!");
        logger.trace("New state: " + currentState);

        this.currentState = currentState;

        sendStateToRobot();
        syncStateWithGui();
    }

    private void sendStateToRobot() {
        backend.getRobotConnection().ifPresent(conn -> {
            PacketPath path = new PacketPath(FmsComponent.DRIVER_STATION, FmsComponent.ROBOT);
            Packet packet = PacketFactory.createUpdate(path, currentState.getCurrentState());

            conn.getPipe().sendPacket(packet);
        });
    }

    protected void syncStateWithGui() {
        logger.trace("Syncing state with GUI!");

        DriverstationState clonedState = this.currentState.clone();

        Platform.runLater(() -> gui.updateDriverstationState(clonedState));
    }

    public void attemptToDisable() {
        attemptToChangeComponentState(ComponentState.DISABLED);
    }

    public void attemptToEnable() {
        if(!getCurrentState().isRobotConnected()){
            logger.warn("Robot must be connected for it to be enabled!");
            return;
        }

        attemptToChangeComponentState(ComponentState.ENABLED);
    }

    public void attemptToChangeComponentState(ComponentState componentState){
        DriverstationState newState = new DriverstationState(this.currentState.getMode(), componentState);
        newState.setFieldConnected(backend.getFieldConnection().isPresent());
        newState.setRobotConnected(backend.getRobotConnection().isPresent());

        setCurrentState(newState);
    }

    public DriverstationState getCurrentState() {
        return currentState;
    }
}
