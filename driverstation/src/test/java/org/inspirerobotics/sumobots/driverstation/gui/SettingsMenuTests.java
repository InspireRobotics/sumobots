package org.inspirerobotics.sumobots.driverstation.gui;

import org.inspirerobotics.sumobots.driverstation.JavaFXTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(JavaFXTest.class)
public class SettingsMenuTests extends GuiTest {

    private SettingsMenu menu = getPane().getSettingsMenu();

    @BeforeEach
    void setUp() {
        StagePositionManager.setInstance(null);
    }

    @Test
    void dockWindowPressed() {
        new StagePositionManager(getStage()).setCurrentMode(StagePositionManager.WindowMode.FLOAT);
        menu.dockWindow.getOnAction().handle(null);

        Assertions.assertEquals(StagePositionManager.WindowMode.DOCKED,
                StagePositionManager.getInstance().getCurrentMode());
    }

    @Test
    void floatWindowPressed() {
        new StagePositionManager(getStage()).setCurrentMode(StagePositionManager.WindowMode.DOCKED);
        menu.floatWindow.getOnAction().handle(null);

        Assertions.assertEquals(StagePositionManager.WindowMode.FLOAT,
                StagePositionManager.getInstance().getCurrentMode());
    }
}
