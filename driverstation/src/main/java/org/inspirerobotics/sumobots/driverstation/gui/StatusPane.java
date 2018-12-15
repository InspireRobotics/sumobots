package org.inspirerobotics.sumobots.driverstation.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.inspirerobotics.sumobots.driverstation.gui.file.FXMLFileLoader;
import org.inspirerobotics.sumobots.driverstation.gui.file.StylesheetLoader;

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
        styleStatusBox(robotStatusBox, StatusBoxState.BAD);
        styleStatusBox(joystickStatusBox, StatusBoxState.GOOD);
        styleStatusBox(fieldStatusBox, StatusBoxState.UNKNOWN);
    }

    private void styleStatusBox(TextField box, StatusBoxState state){
        GuiUtils.setStyleClass(box, state.getStyleClassName());
    }

    private void initEnableDisableButtons() {
        disableButton.setOnAction(event -> setEnabled(false));
        enableButton.setOnAction(event -> setEnabled(true));
    }

    public void setEnabled(boolean enabled){
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
    GOOD("statusBoxGood"), BAD("statusBoxBad"), UNKNOWN("statusBoxUnknown");

    String styleClassName;

    StatusBoxState(String className) {
        this.styleClassName = className;
    }

    public String getStyleClassName() {
        return styleClassName;
    }
}