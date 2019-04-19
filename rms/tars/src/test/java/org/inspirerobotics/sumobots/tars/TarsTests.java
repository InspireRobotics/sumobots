package org.inspirerobotics.sumobots.tars;

import org.inspirerobotics.sumobots.SumobotsRuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TarsTests {

    @Test
    void moreThanOneArgumentFailsTest() {
        Assertions.assertThrows(SumobotsRuntimeException.class, () -> {
           new Tars(new String[2]);
        });
    }

    @Test
    void zeroArgumentsFailsTest() {
        Assertions.assertThrows(SumobotsRuntimeException.class, () -> {
            new Tars(new String[0]);
        });
    }

    @Test
    void getPathTest() {
        Tars tars = new Tars(new String[]{"Zebra"});

        Assertions.assertEquals("Zebra", tars.getFilePath());
    }
}
