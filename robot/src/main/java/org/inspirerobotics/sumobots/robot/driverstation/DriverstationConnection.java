package org.inspirerobotics.sumobots.robot.driverstation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.FmsComponent;
import org.inspirerobotics.sumobots.packet.Packet;
import org.inspirerobotics.sumobots.packet.PacketPath;
import org.inspirerobotics.sumobots.socket.SocketPipe;
import org.inspirerobotics.sumobots.socket.SocketPipeListener;

import java.nio.channels.SocketChannel;


public class DriverstationConnection implements SocketPipeListener {

    private static final Logger logger = LogManager.getLogger(DriverstationConnection.class);
    private final SocketPipe pipe;

    public DriverstationConnection(SocketChannel connection) {
        this.pipe = new SocketPipe(connection, this,
                new PacketPath(FmsComponent.ROBOT, FmsComponent.DRIVER_STATION));
    }

    public void updateConnection() {
        pipe.update();
    }

    @Override
    public void onPacketReceived(Packet packet) {
        logger.debug("Received packet: " + packet);
    }

    public boolean isClosed(){
        return pipe.isClosed();
    }
}
