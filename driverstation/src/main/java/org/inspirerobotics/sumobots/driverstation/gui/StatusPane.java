package org.inspirerobotics.sumobots.driverstation.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.inspirerobotics.sumobots.SumobotsRuntimeException;
import org.inspirerobotics.sumobots.driverstation.state.DriverstationState;
import org.inspirerobotics.sumobots.driverstation.gui.file.FXMLFileLoader;
import org.inspirerobotics.sumobots.driverstation.gui.file.StylesheetLoader;
import org.inspirerobotics.sumobots.driverstation.util.BackendEventQueue;

public class StatusPane extends AnchorPane {

    @FXML
    private TextField fieldStatusBox;
    @FXML
    private TextField joystickStatusBox;
    @FXML
    private TextField robotStatusBox;

    @FXML
    private Button enableButton;
    @FXML
    private Button disableButton;
    @FXML
    private Label statusLabel;

    public StatusPane() {
        FXMLFileLoader.load("status_pane.fxml", this);
        StylesheetLoader.load("status_pane", this);

        initStatusBoxes();
        initEnableDisableButtons();
        setEnabled(false);
    }

    private void initStatusBoxes() {
        styleStatusBox(robotStatusBox, StatusBoxState.DISCONNECTED);
        styleStatusBox(joystickStatusBox, StatusBoxState.DISCONNECTED);
        styleStatusBox(fieldStatusBox, StatusBoxState.DISCONNECTED);
    }

    private void styleStatusBox(TextField box, StatusBoxState state){
        GuiUtils.setStyleClass(box, state.getStyleClassName());
    }

    private void initEnableDisableButtons() {
        disableButton.setOnAction(event -> onDisableButtonPressed());
        enableButton.setOnAction(event -> onEnableButtonPressed());
    }

    private void onEnableButtonPressed() {
        BackendEventQueue.add(backendThread -> {
            backendThread.getStateManager().attemptToEnable();
        });
    }

    private void onDisableButtonPressed() {
        BackendEventQueue.add(backendThread -> {
            backendThread.getStateManager().attemptToDisable();
        });
    }

    public void onStateUpdated(DriverstationState currentState) {
        updateEnabled(currentState);
        updateStatusBoxes(currentState);
    }

    private void updateStatusBoxes(DriverstationState currentState) {
        styleStatusBox(robotStatusBox, StatusBoxState.fromBool(currentState.isRobotConnected()));
        styleStatusBox(joystickStatusBox, StatusBoxState.fromBool(currentState.isJoysticksConnected()));
        styleStatusBox(fieldStatusBox, StatusBoxState.fromBool(currentState.isFieldConnected()));
    }

    private void updateEnabled(DriverstationState currentState) {
        switch (currentState.getCurrentState()){
            case ENABLED:
                setEnabled(true);
                break;
            case DISABLED:
                setEnabled(false);
                return;
            default:
                throw new SumobotsRuntimeException("Unknown time period: " + currentState.getCurrentState());
        }
    }

    private void setEnabled(boolean enabled){
        if(enabled){
            GuiUtils.setStyleClass(enableButton, "toggleButtonSelected");
            GuiUtils.setStyleClass(disableButton, "toggleButtonNotSelected");
            GuiUtils.setStyleClass(statusLabel, "statusLabelEnabled");
            statusLabel.setText("Enabled");
        }else{
            GuiUtils.setStyleClass(enableButton, "toggleButtonNotSelected");
            GuiUtils.setStyleClass(disableButton, "toggleButtonSelected");
            GuiUtils.setStyleClass(statusLabel, "statusLabelDisabled");
            statusLabel.setText("Disabled");
        }
    }
}
enum StatusBoxState{
    CONNECTED("statusBoxConnected"), DISCONNECTED("statusBoxDisconnected");

    String styleClassName;

    StatusBoxState(String className) {
        this.styleClassName = className;
    }

    public static StatusBoxState fromBool(boolean value) {
        return value ? CONNECTED : DISCONNECTED;
    }

    public String getStyleClassName() {
        return styleClassName;
    }
}