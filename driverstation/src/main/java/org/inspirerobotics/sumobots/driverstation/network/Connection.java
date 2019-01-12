package org.inspirerobotics.sumobots.driverstation.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.FmsComponent;
import org.inspirerobotics.sumobots.Ports;
import org.inspirerobotics.sumobots.SumobotsRuntimeException;
import org.inspirerobotics.sumobots.VisibleForTesting;
import org.inspirerobotics.sumobots.driverstation.Gui;
import org.inspirerobotics.sumobots.packet.PacketPath;
import org.inspirerobotics.sumobots.socket.SocketPipe;
import org.inspirerobotics.sumobots.socket.SocketPipeListener;
import org.inspirerobotics.sumobots.socket.Sockets;

import java.nio.channels.SocketChannel;
import java.util.Optional;

public final class Connection {

    private final Logger logger = LogManager.getLogger(Connection.class);
    private final SocketPipe pipe;

    Connection(SocketChannel socket, PacketPath path, SocketPipeListener listener) {
        this.pipe = new SocketPipe(socket, listener, path);
    }

    public void update(){
        if(pipe.isClosed()){
            throw new SumobotsRuntimeException("Cannot update while closed");
        }

        pipe.update();
    }

    public void close(String reason) {
        pipe.close(reason);
    }

    public SocketPipe getPipe() {
        return pipe;
    }

    public boolean isClosed(){
        return pipe.isClosed();
    }

    public static Optional<Connection> createForField(){
        PacketPath path = new PacketPath(FmsComponent.DRIVER_STATION, FmsComponent.FIELD_SERVER);
        return Sockets.create(Ports.FIELD_DS, 1)
                .map(channel -> create(channel, path, new FieldConnectionHandler()));
    }

    public static Optional<Connection> createForRobot(Gui gui){
        PacketPath path = new PacketPath(FmsComponent.DRIVER_STATION, FmsComponent.ROBOT);
        return Sockets.create(Ports.ROBOT, 1)
                .map(channel -> create(channel, path, new RobotConnectionHandler(gui)));
    }

    @VisibleForTesting
    static Connection create(SocketChannel channel, PacketPath path, SocketPipeListener listener){
        return new Connection(channel, path, listener);
    }
}
