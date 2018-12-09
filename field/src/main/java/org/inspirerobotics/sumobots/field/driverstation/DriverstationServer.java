package org.inspirerobotics.sumobots.field.driverstation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.SumobotsRuntimeException;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Optional;

public class DriverstationServer implements Closeable {

    private static final Logger logger = LogManager.getLogger(DriverstationServer.class);
    private final ServerSocketChannel socket;

    public DriverstationServer(ServerSocketChannel socket) {
        this.socket = socket;
    }

    public static DriverstationServer create() throws IOException{
        ServerSocketChannel socket = ServerSocketChannel.open();
        socket.configureBlocking(false);
        socket.socket().bind(new InetSocketAddress(8080));

        return new DriverstationServer(socket);
    }

    public Optional<SocketChannel> acceptNext(){
        Optional<SocketChannel> output = Optional.empty();

        if(socket.isOpen() == false){
            throw new SumobotsRuntimeException("Server cannot accept while closed!");
        }

        try {
            output = Optional.ofNullable(socket.accept());
        }catch (IOException e){
            logger.error("Error while accepting in DS server. Closing server!" + e);
        }

        return output;
    }

    public void close(){
        try {
            socket.close();
        }catch (IOException e){
            throw new RuntimeException("Failed to close DS server!", e);
        }
    }
}
