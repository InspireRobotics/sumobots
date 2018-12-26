package org.inspirerobotics.sumobots.driverstation.gui;

import org.inspirerobotics.sumobots.SumobotsRuntimeException;
import org.inspirerobotics.sumobots.driverstation.JavaFXTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(JavaFXTest.class)
public class StagePositionManagerTests extends GuiTest{

    @BeforeEach
    void setUp() {
        StagePositionManager.setInstance(null);
    }

    @Test
    public void twoInstancesFailsTest(){
        new StagePositionManager(getStage());

        Assertions.assertThrows(SumobotsRuntimeException.class, () -> new StagePositionManager(getStage()));
    }

}
