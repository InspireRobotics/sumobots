package org.inspirerobotics.sumobots.socket;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketPipe implements Closeable {

    private final SocketChannel socket;

    private boolean closed = false;

    public SocketPipe(SocketChannel socket) {
        this.socket = socket;
    }

    public void update() throws IOException{
        if(closed)
            throw new IllegalStateException("Cannot update while closed!");

        readFromSocket();
    }

    private void readFromSocket() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(256);
        int bytesRead = socket.read(buffer);

        if(bytesRead > 0){
            String output = new String(buffer.array(), "utf-8");
            System.out.println(output);
        }

        if(bytesRead < 0)
            close();
    }

    @Override
    public void close() {
        try{
            socket.close();
            closed = true;
        }catch (IOException e){
            throw new RuntimeException("Failed to close socket!", e);
        }
    }

    public boolean isClosed() {
        return closed;
    }
}
