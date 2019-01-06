package org.inspirerobotics.sumobots.driverstation.network;

import org.inspirerobotics.sumobots.FmsComponent;
import org.inspirerobotics.sumobots.SumobotsRuntimeException;
import org.inspirerobotics.sumobots.packet.PacketPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class FieldConnectionTests {

    private Connection connection;

    @BeforeEach
    public void setupFieldConnection() throws IOException {
        PacketPath path = new PacketPath(FmsComponent.DRIVER_STATION, FmsComponent.FIELD_SERVER);
        connection = Connection.create(SocketChannel.open(), path, packet -> {});
    }

    @Test
    void basicCloseTest() {
        connection.close("");

        Assertions.assertTrue(connection.isClosed());
    }

    @Test
    void updateAfterCloseFailsTest() {
        connection.close("");

        Assertions.assertThrows(SumobotsRuntimeException.class,
                () -> connection.update());
    }
}