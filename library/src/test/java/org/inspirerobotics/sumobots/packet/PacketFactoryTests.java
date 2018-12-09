package org.inspirerobotics.sumobots.packet;

import org.inspirerobotics.sumobots.FmsComponent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PacketFactoryTests {

    @Test
    void heartbeatPathTest() {
        PacketPath path = new PacketPath(FmsComponent.FIELD_SERVER, FmsComponent.DRIVER_STATION);
        Packet packet = PacketFactory.createHeartbeat(path);

        Assertions.assertEquals(path, packet.getPath());
    }

    @Test
    void heartbeatActionTest() {
        PacketPath path = new PacketPath(FmsComponent.FIELD_SERVER, FmsComponent.DRIVER_STATION);
        Packet packet = PacketFactory.createHeartbeat(path);

        Assertions.assertEquals("heartbeat", packet.getAction());
    }

    @Test
    void heartbeatDataTest() {
        PacketPath path = new PacketPath(FmsComponent.FIELD_SERVER, FmsComponent.DRIVER_STATION);
        Packet packet = PacketFactory.createHeartbeat(path);
        HeartbeatData data = (HeartbeatData) packet.getDataAs(HeartbeatData.class).get();

        Assertions.assertTrue(System.currentTimeMillis() >= data.getSentTime(),
                "Time went backwards!");
    }
}
