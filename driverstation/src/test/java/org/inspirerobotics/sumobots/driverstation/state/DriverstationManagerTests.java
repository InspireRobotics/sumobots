package org.inspirerobotics.sumobots.driverstation.state;

import org.inspirerobotics.sumobots.ComponentState;
import org.inspirerobotics.sumobots.driverstation.BackendWorker;
import org.inspirerobotics.sumobots.driverstation.Gui;
import org.inspirerobotics.sumobots.driverstation.JavaFXTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(JavaFXTest.class)
public class DriverstationManagerTests extends JavaFXTest {

    private final Gui gui = new Gui();
    private final TestStateManager manager = new TestStateManager(gui);

    @Test
    void nullGuiFailsTest() {
        Assertions.assertThrows(NullPointerException.class,
                () -> new DriverstationStateManager(null, new BackendWorker(null)));
    }

    @Test
    void nullWorkerFailsTest() {
        Assertions.assertThrows(NullPointerException.class,
                () -> new DriverstationStateManager(new Gui(), null));
    }

    @Test
    void setCurrentStateNullFailsTest() {
        Assertions.assertThrows(NullPointerException.class,
                () -> manager.setCurrentState(null));
    }

    @Test
    void setCurrentStateSyncsWithGuiTest() {
        DriverstationState backendState = new DriverstationState(DriverstationMode.FIELD_MODE, ComponentState.DISABLED);

        manager.setCurrentState(backendState);

        Assertions.assertEquals(backendState, manager.getGuiState());
    }

    @Test
    void attemptToDisableBasicTest() {
        manager.setCurrentState(new DriverstationState(DriverstationMode.REGULAR, ComponentState.ENABLED));
        Assertions.assertEquals(manager.getCurrentState().getCurrentState(), ComponentState.ENABLED);

        manager.attemptToDisable();
        Assertions.assertEquals(manager.getCurrentState().getCurrentState(), ComponentState.DISABLED);
    }

    @Test
    void attemptToEnableBasicTest() {
        Assertions.assertEquals(manager.getCurrentState().getCurrentState(), ComponentState.DISABLED);

        manager.attemptToEnable();
        Assertions.assertEquals(manager.getCurrentState().getCurrentState(), ComponentState.ENABLED);
    }
}
class TestStateManager extends DriverstationStateManager{

    private DriverstationState guiState;

    public TestStateManager(Gui gui) {
        super(gui, new BackendWorker(gui));
    }

    @Override
    protected void syncStateWithGui() {
        this.guiState = this.getCurrentState().clone();
    }

    public DriverstationState getGuiState() {
        return guiState;
    }

    @Override
    public void attemptToChangeComponentState(ComponentState componentState) {
        DriverstationState newState = new DriverstationState(this.getCurrentState().getMode(), componentState);

        setCurrentState(newState);
    }
}