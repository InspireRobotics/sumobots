package org.inspirerobotics.sumobots.packet;

import org.inspirerobotics.sumobots.FmsComponent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RawPacketTests {

    @Test
    public void pathSrcTest(){
        String input = "{\"path\":{\"source\":\"ROBOT\",\"destination\":\"FIELD_SERVER\"}," +
                "\"data\":{\"reason\":\"Driverstation closed\"},\"action\":\"close\"}";

        Packet packet = Packet.fromJSON(input);

        Assertions.assertEquals(packet.getPath().getSource(), FmsComponent.ROBOT);
    }

    @Test
    public void pathDestinationTest(){
        String input = "{\"path\":{\"source\":\"ROBOT\",\"destination\":\"FIELD_SERVER\"}," +
                "\"data\":{\"reason\":\"Driverstation closed\"},\"action\":\"close\"}";

        Packet packet = Packet.fromJSON(input);

        Assertions.assertEquals(packet.getPath().getDestination(), FmsComponent.FIELD_SERVER);
    }
}
