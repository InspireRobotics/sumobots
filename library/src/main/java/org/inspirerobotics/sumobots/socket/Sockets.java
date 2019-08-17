package org.inspirerobotics.sumobots.socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.nio.channels.SocketChannel;
import java.util.Optional;

public class Sockets {

    private static final Logger logger = LogManager.getLogger(Sockets.class);

    public static Optional<SocketChannel> create(String ip, int port, int timeout){
        try{
            SocketChannel socket = SocketChannel.open();
            socket.socket().connect(new InetSocketAddress(ip, port), timeout);

            if(socket.socket().isConnected())
                return Optional.of(socket);
        }catch (SocketTimeoutException e){
            //Do not handle timeout
        }catch (IOException e){
            logger.error("Error occurred while trying to connect to network: " + e);
        }

        return Optional.empty();
    }

    public static Optional<SocketChannel> create(int port, int timeout){
       return create("localhost", port, timeout);
    }
}
