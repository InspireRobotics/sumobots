package org.inspirerobotics.sumobots.robot;

import org.inspirerobotics.sumobots.ComponentState;
import org.inspirerobotics.sumobots.robot.api.HardwareBackend;
import org.inspirerobotics.sumobots.robot.api.MockHardware;
import org.inspirerobotics.sumobots.robot.api.RobotBase;
import org.inspirerobotics.sumobots.robot.driverstation.DriverstationTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RobotContainerTests {

    private MockHardware mockHardware;
    private RobotContainer container;
    private TestRobot robot;

    @BeforeEach
    void setUp() {
        robot = new TestRobot();
        mockHardware = new MockHardware();
        container = new RobotContainer(robot, mockHardware);
    }

    @Test
    void shutdownBasicTest(){
        container.onShutdown();

        Assertions.assertTrue(robot.shutdown);
    }

    @Test
    void initBasicTest(){
        container.init();

        Assertions.assertTrue(robot.init);
    }

    @Test
    public void updateRobotEnabledTest(){
        DriverstationTests.setSingletonState(ComponentState.ENABLED);

        for(int i = 0; i < 5; i++){
            container.updateRobot();
        }

        Assertions.assertEquals(5, robot.enableCount);
    }

    @Test
    public void updateRobotDisabledTest(){
        DriverstationTests.setSingletonState(ComponentState.DISABLED);

        for(int i = 0; i < 5; i++){
            container.updateRobot();
        }

        Assertions.assertEquals(5, robot.disableCount);
    }

    @Test
    void shutdownHardwareBackendOnRobotShutdown() {
        container.onShutdown();

        Assertions.assertTrue(mockHardware.isShutdown());
    }

    @Test
    void hardwareInitializedOnRobotInitialisation() {
        Assumptions.assumeFalse(mockHardware.isInitialized());

        container.init();

        Assertions.assertTrue(mockHardware.isInitialized());
    }
}
class TestRobot implements RobotBase{

    boolean shutdown;
    boolean init;

    int enableCount = 0;
    int disableCount = 0;

    @Override
    public void init(HardwareBackend backend) {
        init = true;
    }

    @Override
    public void enablePeriodic() {
        enableCount++;
    }

    @Override
    public void disablePeriodic() {
        disableCount++;
    }

    @Override
    public void onShutdown() {
        shutdown = true;
    }
}