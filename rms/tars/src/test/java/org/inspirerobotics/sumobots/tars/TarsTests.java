package org.inspirerobotics.sumobots.tars;

import org.inspirerobotics.sumobots.SumobotsRuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TarsTests {

    @Test
    void moreThanTwoArgumentFailsTest() {
        Assertions.assertThrows(SumobotsRuntimeException.class, () -> {
           new Tars(new String[3]);
        });
    }

    @Test
    void oneArgumentsFailsTest() {
        Assertions.assertThrows(SumobotsRuntimeException.class, () -> {
            new Tars(new String[]{""});
        });
    }

    @Test
    void getPathTest() {
        Tars tars = new Tars(new String[]{"Zebra", ""});

        Assertions.assertEquals("Zebra", tars.getFilePath());
    }
}
