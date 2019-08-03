package org.inspirerobotics.sumobots.robot.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PWMMotorControllerTests {

    @Test
    void percentToValueNegativeOneTest() {
        percentToValueTest(10, -1);
    }

    @Test
    void percentToValuePositiveOneTest() {
        percentToValueTest(20, 1);
    }

    @Test
    void percentToValueZeroTest() {
        percentToValueTest(15, 0);
    }

    void percentToValueTest(int expected, double percent){
        Assertions.assertEquals(expected, PWMMotorController.percentToValue(percent));
    }
}
