package org.inspirerobotics.sumobots.field.driverstation;

import org.inspirerobotics.sumobots.FmsComponent;
import org.inspirerobotics.sumobots.packet.PacketPath;
import org.inspirerobotics.sumobots.socket.SocketPipe;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class DriverstationManager{

    private ArrayList<DriverstationConnection> connections = new ArrayList<>();

    public void manageNewConnection(SocketChannel socket){
        DriverstationConnection conn = createConnectionPipeFromSocket(socket);

        synchronized (connections){
            connections.add(conn);
        }
    }

    private DriverstationConnection createConnectionPipeFromSocket(SocketChannel socket) {
        DriverstationConnection connectionHandler = new DriverstationConnection();
        PacketPath path = new PacketPath(FmsComponent.FIELD_SERVER, FmsComponent.DRIVER_STATION);
        SocketPipe pipe = new SocketPipe(socket, connectionHandler, path);
        connectionHandler.setPipe(pipe);

        return connectionHandler;
    }

    public void update(){
        ArrayList<DriverstationConnection> closedConnections = new ArrayList<>();

        for(DriverstationConnection connection : connections){
            if(connection.getPipe().isPresent()){
                connection.getPipe().get().update();

                if(connection.getPipe().get().isClosed())
                    closedConnections.add(connection);
            }
        }

        removeConnections(closedConnections);
    }

    private void removeConnections(List<DriverstationConnection> connectionsToRemove) {
        for(DriverstationConnection conn : connectionsToRemove){
            connections.remove(conn);
        }
    }

    public void closeConnections() {
        for(DriverstationConnection connection : connections){
            connection.getPipe().ifPresent(SocketPipe::close);
        }
    }
}
