package org.inspirerobotics.sumobots.driverstation.gui;

import javafx.scene.Node;
import org.inspirerobotics.sumobots.ComponentState;
import org.inspirerobotics.sumobots.driverstation.state.DriverstationMode;
import org.inspirerobotics.sumobots.driverstation.state.DriverstationState;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class StatusPaneTests extends GuiTest {

    private StatusPane pane = getPane().getStatusPane();

    @Test
    void statusBoxesDefaultTest() {
        String expected = StatusBoxState.DISCONNECTED.styleClassName;

        for(Node node : Arrays.asList(pane.fieldStatusBox, pane.joystickStatusBox, pane.robotStatusBox)){
            assertContainsClass(expected, node);
        }
    }

    @Test
    void statusBoxesAllConnectedTest() {
        String expected = StatusBoxState.CONNECTED.styleClassName;
        DriverstationState state = new DriverstationState(DriverstationMode.REGULAR, ComponentState.ENABLED);
        state.setRobotConnected(true);
        state.setFieldConnected(true);
        state.setJoysticksConnected(true);


        updateState(state);

        for(Node node : Arrays.asList(pane.fieldStatusBox, pane.joystickStatusBox, pane.robotStatusBox)){
            assertContainsClass(expected, node);
        }
    }

    @Test
    void statusLabelDisabledTest() {
        updateState(new DriverstationState(DriverstationMode.REGULAR, ComponentState.DISABLED));

        assertStatusLabelState(false);
    }

    @Test
    void statusLabelEnabledTest() {
        updateState(new DriverstationState(DriverstationMode.REGULAR, ComponentState.ENABLED));

        assertStatusLabelState(true);
    }

    @Test
    void enableButtonPressedTest() {
        updateState(new DriverstationState(DriverstationMode.REGULAR, ComponentState.ENABLED));

        assertStatusLabelState(true);
    }

    private void assertStatusLabelState(boolean enabled){
        if(enabled){
            assertContainsClass("toggleButtonNotSelected", pane.disableButton);
            assertContainsClass("toggleButtonSelected", pane.enableButton);
            assertContainsClass("statusLabelEnabled", pane.statusLabel);
        }else{
            assertContainsClass("toggleButtonSelected", pane.disableButton);
            assertContainsClass("toggleButtonNotSelected", pane.enableButton);
            assertContainsClass("statusLabelDisabled", pane.statusLabel);
        }
    }
}
