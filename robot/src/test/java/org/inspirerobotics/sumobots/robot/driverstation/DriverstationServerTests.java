package org.inspirerobotics.sumobots.robot.driverstation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.Optional;

public class DriverstationServerTests {

    private DriverstationServer server = new DriverstationServer();

    @BeforeEach
    void setUp() {
        Driverstation.getInstance().setConnection(Optional.empty());
    }

    @Test
    void acceptWhileNotConnectedTest() throws IOException {
        SocketChannel channel = SocketChannel.open();

        server.handle(channel);

        Assertions.assertSame(channel, Driverstation.getInstance().getConnection().get().getPipe().getSocket());
    }

    @Test
    void acceptWhileConnectedTest() throws IOException{
        Driverstation.getInstance().setConnection(Optional.of(new DriverstationConnection(SocketChannel.open())));
        SocketChannel channel = SocketChannel.open();

        server.handle(channel);

        Assertions.assertTrue(channel.socket().isClosed());
    }
}
