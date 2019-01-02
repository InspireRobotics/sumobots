package org.inspirerobotics.sumobots.robot.driverstation;

import org.inspirerobotics.sumobots.ComponentState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.Optional;

public class DriverstationTests {

    private Driverstation driverstation = new Driverstation();

    @Test
    public void updateNullConnectionTest(){
        driverstation.setConnection(Optional.empty());
        driverstation.update();
    }

    @Test
    public void updateClosedConnectionTest() throws IOException {
        SocketChannel channel = SocketChannel.open();
        DriverstationConnection connection = new DriverstationConnection(channel);

        driverstation.setConnection(Optional.of(connection));
        channel.close();
        driverstation.update();

        Assertions.assertFalse(driverstation.isConnected());
    }

    @Test
    public void basicSetStateTest() {
        ComponentState state = ComponentState.ENABLED;
        driverstation.setState(state);

        Assertions.assertEquals(state, driverstation.getState());
    }

    public static void setSingletonState(ComponentState state){
        Driverstation.getInstance().setState(state);
    }

}
