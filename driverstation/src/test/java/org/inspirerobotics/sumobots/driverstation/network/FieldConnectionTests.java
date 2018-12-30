package org.inspirerobotics.sumobots.driverstation.network;

import org.inspirerobotics.sumobots.SumobotsRuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class FieldConnectionTests {

    private FieldConnectionHandler connection;

    @BeforeEach
    public void setupFieldConnection() throws IOException {
        connection = new FieldConnectionHandler(SocketChannel.open());
    }

    @Test
    void basicCloseTest() {
        connection.close();

        Assertions.assertTrue(connection.isClosed());
    }

    @Test
    void updateAfterCloseFailsTest() {
        connection.getFieldConnection().close();

        Assertions.assertThrows(SumobotsRuntimeException.class,
                () -> connection.update());
    }
}