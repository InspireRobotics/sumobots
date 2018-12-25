package org.inspirerobotics.sumobots.driverstation.state;

import org.inspirerobotics.sumobots.ComponentState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DriverstationStateTests {

    @Test
    public void basicTest(){
        DriverstationState state = new DriverstationState(DriverstationMode.REGULAR, ComponentState.DISABLED);

        Assertions.assertEquals(state.getCurrentState(), ComponentState.DISABLED);
        Assertions.assertEquals(state.getMode(), DriverstationMode.REGULAR);
    }

    @Test
    public void startTimeTest(){
        DriverstationState state = new DriverstationState(DriverstationMode.REGULAR, ComponentState.DISABLED);

        Assertions.assertTrue(System.currentTimeMillis() >= state.getStartTime());
    }

    @Test
    public void nullModeFailsTest(){
        Assertions.assertThrows(NullPointerException.class,
                () -> new DriverstationState(null, ComponentState.DISABLED));
    }

    @Test
    public void nullComponentStateFails(){
        Assertions.assertThrows(NullPointerException.class,
                () -> new DriverstationState(DriverstationMode.REGULAR, null));
    }

    @Test
    public void cloneTest(){
        DriverstationState originalState = new DriverstationState(DriverstationMode.FIELD_MODE, ComponentState.ENABLED);
        originalState.setFieldConnected(true);
        originalState.setRobotConnected(true);

        DriverstationState newState = originalState.clone();

        Assertions.assertTrue(originalState.equals(newState));
    }

}
