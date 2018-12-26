package org.inspirerobotics.sumobots.driverstation.gui;

import javafx.scene.Node;
import javafx.stage.Stage;
import org.inspirerobotics.sumobots.driverstation.JavaFXTest;
import org.inspirerobotics.sumobots.driverstation.state.DriverstationState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationTest;

@ExtendWith(JavaFXTest.class)
public class GuiTest extends ApplicationTest {

    private final RootPane pane = new RootPane();
    private Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setScene(pane.createScene());
    }

    public void updateState(DriverstationState state){
        pane.onStateUpdated(state);
    }

    protected static void assertContainsClass(String className, Node node){
        Assertions.assertTrue(node.getStyleClass().contains(className));
    }

    public Stage getStage() {
        return stage;
    }

    public RootPane getPane() {
        return pane;
    }
}
