package org.inspirerobotics.sumobots;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FmsComponentTest {

    @Test
    void getSourceNameTest() {
        Assertions.assertEquals("driver_station", FmsComponent.DRIVER_STATION.getSourceName());
        Assertions.assertEquals("field_server", FmsComponent.FIELD_SERVER.getSourceName());
    }
}
