package org.inspirerobotics.sumobots.driverstation.gui;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LogPaneTests extends GuiTest {

    private LogPane logPane = getPane().getLogPane();

    @Test
    public void resetTest(){
        logPane.appendLog("Hello World");
        logPane.reset();

        Assertions.assertEquals("", logPane.getTextArea().getText());
    }

    @Test
    public void appendLogTest(){
        logPane.appendLog("This is a string");

        Assertions.assertEquals("This is a string\n", logPane.getTextArea().getText());
    }
}
