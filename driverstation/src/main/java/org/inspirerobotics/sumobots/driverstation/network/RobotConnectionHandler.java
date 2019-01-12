package org.inspirerobotics.sumobots.driverstation.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.driverstation.Gui;
import org.inspirerobotics.sumobots.packet.LogData;
import org.inspirerobotics.sumobots.packet.Packet;
import org.inspirerobotics.sumobots.packet.PacketFactory;
import org.inspirerobotics.sumobots.socket.SocketPipeListener;

public class RobotConnectionHandler implements SocketPipeListener {

    private static final Logger logger = LogManager.getLogger(RobotConnectionHandler.class);

    private final Gui gui;

    public RobotConnectionHandler(Gui gui) {
        this.gui = gui;
    }

    public void onPacketReceived(Packet packet) {
        logger.info("Received packet: {}", packet);

        if(packet.getAction().equals(PacketFactory.LOG)){
            handleLogPacket(packet);
        }
    }

    private void handleLogPacket(Packet packet) {
        LogData data = (LogData) packet.getDataAs(LogData.class).get();

        gui.log(data.getLine());
    }
}
