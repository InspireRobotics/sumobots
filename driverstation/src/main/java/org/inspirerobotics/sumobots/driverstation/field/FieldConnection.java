package org.inspirerobotics.sumobots.driverstation.field;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.FmsComponent;
import org.inspirerobotics.sumobots.SumobotsRuntimeException;
import org.inspirerobotics.sumobots.packet.Packet;
import org.inspirerobotics.sumobots.packet.PacketPath;
import org.inspirerobotics.sumobots.socket.SocketPipe;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.nio.channels.SocketChannel;
import java.util.Objects;
import java.util.Optional;

public class FieldConnection {

    private static final Logger logger = LogManager.getLogger(FieldConnection.class);

    private final SocketPipe fieldConnection;

    FieldConnection(SocketChannel channel) {
        Objects.requireNonNull(channel, "Field connection cannot be null!");

        PacketPath path = new PacketPath(FmsComponent.DRIVER_STATION, FmsComponent.FIELD_SERVER);
        fieldConnection = new SocketPipe(channel, this::handlePacket, path);
        logger.info("Established connection with field: " + fieldConnection);
    }

    public void update() {
        if(fieldConnection.isClosed() == false)
            fieldConnection.update();
        else
            throw new SumobotsRuntimeException("Cannot update while closed!");

    }

    private void handlePacket(Packet packet) {
        logger.info("Received packet: {}", packet);
    }

    public static Optional<FieldConnection> connectToField() {
        try{
            SocketChannel socket = SocketChannel.open();
            socket.socket().connect(new InetSocketAddress(8080), 1);

            if(socket.socket().isConnected())
                return Optional.of(onConnectionMade(socket));
        }catch (SocketTimeoutException e){
            logger.trace("Socket timeout reached! ");
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

    SocketPipe getFieldConnection() {
        return fieldConnection;
    }

    public boolean isClosed(){
        return fieldConnection.isClosed();
    }
}
