package org.inspirerobotics.sumobots.packet;

import org.inspirerobotics.sumobots.FmsComponent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PacketPathTests {

    @Test
    void nullSourceConstructorFails() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            new PacketPath(null, FmsComponent.FIELD_SERVER);
        });
    }

    @Test
    void nullDestinationConstructorFails() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            new PacketPath(FmsComponent.FIELD_SERVER, null);
        });
    }

    @Test
    void sameSourceDestinationConstructorFails() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
           new PacketPath(FmsComponent.FIELD_SERVER, FmsComponent.FIELD_SERVER);
        });
    }
}
