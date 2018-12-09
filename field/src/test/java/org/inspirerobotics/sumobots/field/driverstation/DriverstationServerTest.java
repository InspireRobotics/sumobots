package org.inspirerobotics.sumobots.field.driverstation;

import org.inspirerobotics.sumobots.SumobotsRuntimeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;

public class DriverstationServerTest {

    private DriverstationServer server;

    @BeforeEach
    public void createServer() throws IOException {
        server = new DriverstationServer(ServerSocketChannel.open());
    }

    @Test
    void onUpdateWhileClosedFailsTest() {
        Assertions.assertThrows(SumobotsRuntimeException.class, () ->{
            server.close();
            server.acceptNext();
        });
    }

    @AfterEach
    public void destroyServer(){
        server.close();
    }
}
