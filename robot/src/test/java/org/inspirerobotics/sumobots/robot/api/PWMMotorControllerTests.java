package org.inspirerobotics.sumobots.robot.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PWMMotorControllerTests {

    @Test
    void percentToValueNegativeOneTest() {
        percentToValueTest(100, -1);
    }

    @Test
    void percentToValuePositiveOneTest() {
        percentToValueTest(200, 1);
    }

    @Test
    void percentToValueZeroTest() {
        percentToValueTest(150, 0);
    }

    void percentToValueTest(int expected, double percent){
        Assertions.assertEquals(expected, PWMMotorController.percentToValue(percent));
    }
}
