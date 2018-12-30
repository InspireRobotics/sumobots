package org.inspirerobotics.sumobots.driverstation.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.packet.Packet;
import org.inspirerobotics.sumobots.socket.SocketPipeListener;

public class FieldConnectionHandler implements SocketPipeListener {

    private static final Logger logger = LogManager.getLogger(FieldConnectionHandler.class);

    public void onPacketReceived(Packet packet) {
        logger.info("Received packet: {}", packet);
    }

}
