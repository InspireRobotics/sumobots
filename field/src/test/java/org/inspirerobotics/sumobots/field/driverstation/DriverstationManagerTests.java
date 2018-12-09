package org.inspirerobotics.sumobots.field.driverstation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.Collections;

public class DriverstationManagerTests {

    private DriverstationManager manager = new DriverstationManager();

    @Test
    void manageNewConnectionNullSocketFailsTest() {
        Assertions.assertThrows(NullPointerException.class, () ->{
            manager.manageNewConnection(null);
        });
    }

    @Test
    void managerRemovesClosedPipesOnUpdateTest() throws IOException {
        manager.manageNewConnection(SocketChannel.open());
        manager.getConnections().get(0).getPipe().get().close();

        manager.update();

        Assertions.assertTrue(manager.getConnections().isEmpty());
    }

    @Test
    void removeConnectionsTest() throws IOException {
        SocketChannel channel = SocketChannel.open();
        manager.manageNewConnection(channel);
        Assertions.assertEquals(1, manager.getConnections().size());

        manager.removeConnections(Collections.singletonList(manager.getConnections().get(0)));
        Assertions.assertEquals(0, manager.getConnections().size());

    }

    @Test
    void closeConnectionsTest() throws IOException {
        SocketChannel channel = SocketChannel.open();
        manager.manageNewConnection(channel);

        manager.closeConnections();

        Assertions.assertTrue(manager.getConnections().get(0).isClosed());
    }
}
