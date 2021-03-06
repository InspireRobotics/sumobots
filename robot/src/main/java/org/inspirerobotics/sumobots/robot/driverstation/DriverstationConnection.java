package org.inspirerobotics.sumobots.robot.driverstation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.FmsComponent;
import org.inspirerobotics.sumobots.VisibleForTesting;
import org.inspirerobotics.sumobots.packet.Packet;
import org.inspirerobotics.sumobots.packet.PacketFactory;
import org.inspirerobotics.sumobots.packet.PacketPath;
import org.inspirerobotics.sumobots.packet.UpdateStateData;
import org.inspirerobotics.sumobots.socket.SocketPipe;
import org.inspirerobotics.sumobots.socket.SocketPipeListener;

import java.nio.channels.SocketChannel;


public class DriverstationConnection implements SocketPipeListener {

    private static final Logger logger = LogManager.getLogger(DriverstationConnection.class);
    private static final PacketPath path = new PacketPath(FmsComponent.ROBOT, FmsComponent.DRIVER_STATION);
    private final SocketPipe pipe;

    public DriverstationConnection(SocketChannel connection) {
        this.pipe = new SocketPipe(connection, this, path);
    }

    public void updateConnection() {
        pipe.update();
    }

    @Override
    public void onPacketReceived(Packet packet) {
        logger.debug("Received packet: " + packet);

        if(packet.getAction().equals(PacketFactory.UPDATE)){
            handleUpdateMessage(packet);
        }
    }

    private void handleUpdateMessage(Packet packet) {
        UpdateStateData data = (UpdateStateData) packet.getDataAs(UpdateStateData.class).get();

        Driverstation.getInstance().setState(data.getNewState());
    }


    public void sendLog(String message) {
        if(pipe.isClosed())
            return;
        
        pipe.sendPacket(PacketFactory.createLog(path, message));
    }

    @VisibleForTesting
    SocketPipe getPipe() {
        return pipe;
    }

    public boolean isClosed(){
        return pipe.isClosed();
    }
}
