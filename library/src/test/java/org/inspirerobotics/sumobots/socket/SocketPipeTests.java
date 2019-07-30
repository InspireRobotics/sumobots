package org.inspirerobotics.sumobots.socket;

import org.inspirerobotics.sumobots.FmsComponent;
import org.inspirerobotics.sumobots.packet.Packet;
import org.inspirerobotics.sumobots.packet.PacketPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class SocketPipeTests {

    @Test
    void closedSocketClosesPipeTest() throws IOException{
        PacketPath path = new PacketPath(FmsComponent.FIELD_SERVER, FmsComponent.DRIVER_STATION);
        SocketChannel channel = SocketChannel.open();
        SocketPipe pipe = new SocketPipe(channel, createPipeListener(), path);
        channel.close();

        Assertions.assertTrue(pipe.isClosed());
    }

    @Test
    void checkVersionsMatchTest() throws IOException{
        String packet = "{\"path\":{\"source\":\"FIELD_SERVER\",\"destination\":\"DRIVER_STATION\"}," +
                "\"data\":{\"version\":\"0.1.1\"},\"action\":\"version\"}";
        PacketPath path = new PacketPath(FmsComponent.FIELD_SERVER, FmsComponent.DRIVER_STATION);
        SocketChannel channel = SocketChannel.open();
        SocketPipe pipe = new SocketPipe(channel, createPipeListener(), path);
        pipe.handleVersionPacket(Packet.fromJSON(packet));

        Assertions.assertFalse(pipe.isClosed());
    }

    @Test
    void checkVersionsDoesNotMatchTest() throws IOException{
        String packet = "{\"path\":{\"source\":\"FIELD_SERVER\",\"destination\":\"DRIVER_STATION\"}," +
                "\"data\":{\"version\":\"1.1.0\"},\"action\":\"version\"}";
        PacketPath path = new PacketPath(FmsComponent.FIELD_SERVER, FmsComponent.DRIVER_STATION);
        SocketChannel channel = SocketChannel.open();
        SocketPipe pipe = new SocketPipe(channel, createPipeListener(), path);
        pipe.handleVersionPacket(Packet.fromJSON(packet));

        Assertions.assertTrue(pipe.isClosed());
    }

    @Test
    void nullSocketConstructorFails() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            PacketPath path = new PacketPath(FmsComponent.FIELD_SERVER, FmsComponent.DRIVER_STATION);

            new SocketPipe(null, createPipeListener(), path);
        });
    }

    @Test
    void nullPipeListenerConstructorFails(){
        Assertions.assertThrows(NullPointerException.class, () -> {
            PacketPath path = new PacketPath(FmsComponent.FIELD_SERVER, FmsComponent.DRIVER_STATION);

            new SocketPipe(createSocketChannel(), null, path);
        });
    }

    @Test
    void nullPacketPathConstructorFails(){
        Assertions.assertThrows(NullPointerException.class, () -> {
            new SocketPipe(createSocketChannel(), createPipeListener(), null);
        });
    }

    private SocketChannel createSocketChannel() throws IOException {
        return SocketChannel.open();
    }

    private SocketPipeListener createPipeListener(){
        return packet -> {};
    }
}
