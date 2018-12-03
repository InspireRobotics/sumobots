package org.inspirerobotics.sumobots.field.server;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Optional;

public class DriverstationServer implements Closeable {

    private final ServerSocketChannel socket;
    private boolean closed = false;

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

        try {
            output = Optional.ofNullable(socket.accept());
        }catch (IOException e){
            System.err.println("Error while accepting in DS server! Closing server: " + e);
        }

        return output;
    }

    public void close(){
        try {
            socket.close();
            closed = true;
        }catch (IOException e){
            throw new RuntimeException("Failed to close DS server!", e);
        }
    }
}
