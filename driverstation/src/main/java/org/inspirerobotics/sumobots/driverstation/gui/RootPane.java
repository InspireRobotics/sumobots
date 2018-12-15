package org.inspirerobotics.sumobots.driverstation.gui;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class RootPane extends AnchorPane {

    private BorderPane borderPane;

    public RootPane() {
        this.borderPane = new BorderPane();
        borderPane.setBottom(new StatusPane());

        GuiUtils.anchorInAnchorPane(borderPane);

        this.getChildren().add(borderPane);
    }

    public Scene createScene() {
       return new Scene(this);
    }
}
