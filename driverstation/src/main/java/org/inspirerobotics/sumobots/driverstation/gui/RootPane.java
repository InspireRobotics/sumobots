package org.inspirerobotics.sumobots.driverstation.gui;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.inspirerobotics.sumobots.driverstation.state.DriverstationState;

public class RootPane extends AnchorPane {

    private final BorderPane borderPane;
    private final StatusPane statusPane;

    public RootPane() {
        this.borderPane = new BorderPane();
        this.statusPane = new StatusPane();

        borderPane.setBottom(statusPane);

        GuiUtils.anchorInAnchorPane(borderPane);

        this.getChildren().add(borderPane);
    }

    public Scene createScene() {
       return new Scene(this);
    }

    public void onStateUpdated(DriverstationState currentState) {
        statusPane.onStateUpdated(currentState);
    }
}
