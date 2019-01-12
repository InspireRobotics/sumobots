package org.inspirerobotics.sumobots.driverstation.gui.style;

import javafx.scene.layout.AnchorPane;
import org.inspirerobotics.sumobots.driverstation.gui.GuiTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StyleTests extends GuiTest {

    @Test
    void loadDarkModeTest() {
        StyleManager.getInstance().styleChildren(StyleType.DARK);
    }

    @Test
    void loadLightModeTest() {
        StyleManager.getInstance().styleChildren(StyleType.LIGHT);
    }

    @Test
    void loadFakeFileTest() {
        Assertions.assertThrows(StylesheetLoadException.class,
                () -> StylesheetLoader.load("non-existent path", new AnchorPane()));
    }
}
