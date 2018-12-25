package org.inspirerobotics.sumobots.driverstation.gui.fxml;

import org.inspirerobotics.sumobots.driverstation.JavaFXTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(JavaFXTest.class)
public class FXMLFileLoaderTests{

    @Test
    public void unexistantFileFailsTest(){
        Assertions.assertThrows(FXMLFileLoadException.class, () -> {
           FXMLFileLoader.load("file", null);
        });
    }

}
