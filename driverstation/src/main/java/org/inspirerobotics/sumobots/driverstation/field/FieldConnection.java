package org.inspirerobotics.sumobots.driverstation.field;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.FmsComponent;
import org.inspirerobotics.sumobots.packet.Packet;
import org.inspirerobotics.sumobots.packet.PacketPath;
import org.inspirerobotics.sumobots.socket.SocketPipe;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Objects;
import java.util.Optional;

public class FieldConnection {

    private static final Logger logger = LogManager.getLogger(FieldConnection.class);

    private final SocketPipe fieldConnection;

    private FieldConnection(SocketChannel channel) {
        Objects.requireNonNull(channel, "Field connection cannot be null!");

        PacketPath path = new PacketPath(FmsComponent.DRIVER_STATION, FmsComponent.FIELD_SERVER);
        fieldConnection = new SocketPipe(channel, this::handlePacket, path);
        logger.info("Established connection with field: " + fieldConnection);
    }

    // Noah - you need to handle what happens when the connection is closed

    public void update() {
        if(fieldConnection.isClosed() == false)
            fieldConnection.update();
    }

    private void handlePacket(Packet packet) {
        logger.info("Received packet: {}", packet);
    }

    public static Optional<FieldConnection> connectToField() {
        try{
            SocketChannel socket = SocketChannel.open(new InetSocketAddress(8080));
            return Optional.of(onConnectionMade(socket));
        }catch (IOException e){
            logger.error("Error occurred while trying to connect to field: " + e);
        }

        return Optional.empty();
    }

    private static FieldConnection onConnectionMade(SocketChannel socket) {
        return new FieldConnection(socket);
    }

    public void close() {
        logger.info("Closing field connection!");
        fieldConnection.close();
    }
}
