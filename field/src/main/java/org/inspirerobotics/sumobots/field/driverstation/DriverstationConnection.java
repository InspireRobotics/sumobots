package org.inspirerobotics.sumobots.field.driverstation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.packet.Packet;
import org.inspirerobotics.sumobots.socket.SocketPipe;
import org.inspirerobotics.sumobots.socket.SocketPipeListener;

import java.util.Optional;

public class DriverstationConnection implements SocketPipeListener {

    private static final Logger logger = LogManager.getLogger(DriverstationConnection.class);
    private Optional<SocketPipe> pipe;

    @Override
    public void onPacketReceived(Packet packet) {
        logger.debug("Packet received: {}", packet);
    }

    public Optional<SocketPipe> getPipe() {
        return pipe;
    }

    public void setPipe(SocketPipe pipe) {
        this.pipe = Optional.ofNullable(pipe);
    }

    @Override
    public String toString() {
        return "DriverstationConnection{" +
                "pipe=" + pipe +
                '}';
    }
}
