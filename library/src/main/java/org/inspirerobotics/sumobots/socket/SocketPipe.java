package org.inspirerobotics.sumobots.socket;

import org.inspirerobotics.sumobots.packet.HeartbeatData;
import org.inspirerobotics.sumobots.packet.Packet;
import org.inspirerobotics.sumobots.packet.PacketFactory;
import org.inspirerobotics.sumobots.packet.PacketPath;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

public class SocketPipe implements Closeable {

    private static final int HEADER_SIZE = 4;

    private final SocketChannel socket;
    private final SocketPipeListener listener;
    private final PacketPath path;

    private long lastHeartbeatSentTime;
    private long lastHeartbeatReceivedTime;
    private int ping;

    private boolean closed = false;

    public SocketPipe(SocketChannel socket, SocketPipeListener listener, PacketPath path) {
        Objects.requireNonNull(socket, "Socket cannot be null");
        Objects.requireNonNull(listener, "Listener cannot be null");
        Objects.requireNonNull(path, "Path cannot be null");


        this.socket = socket;
        this.listener = listener;
        this.path = path;

        try{
            socket.configureBlocking(false);
        }catch (IOException e){
            System.err.println("Failed to disable blocking for socket pipe: " + e);
            System.err.println("Closing socket pipe due to error!");
            close();
        }

    }

    public void update(){
        if(closed)
            throw new IllegalStateException("Cannot update while closed!");

        listener.update();
        updateHeartbeat();

        try{
            readFromSocket().ifPresent(this::handlePacketReceived);
        }catch (IOException e){
            System.err.println("Failed while reading from socket pipe: " + e);
            System.err.println("Closing socket pipe due to error!");
            close();
        }

    }

    private void handlePacketReceived(Packet packet) {
        if(packet.getAction().equals(PacketFactory.HEARTBEAT)){
            handleHeartbeatPacket(packet);
        }else {
            listener.onPacketReceived(packet);
        }
    }

    private void updateHeartbeat() {
        if(lastHeartbeatSentTime + 1000 < System.currentTimeMillis()){
            sendHeartbeat();
        }

        if(lastHeartbeatReceivedTime == 0){
            lastHeartbeatReceivedTime = System.currentTimeMillis();
        }

        if(lastHeartbeatReceivedTime + 5000 < System.currentTimeMillis()){
            System.out.println("Lost signal with connection!");
            close();
            return;
        }
    }

    private void sendHeartbeat() {
        lastHeartbeatSentTime = System.currentTimeMillis();
        sendPacket(PacketFactory.createHeartbeat(path));
    }

    private void handleHeartbeatPacket(Packet packet) {
        lastHeartbeatReceivedTime = System.currentTimeMillis();
        HeartbeatData data = (HeartbeatData) packet.getDataAs(HeartbeatData.class).get();
        ping = (int) (lastHeartbeatReceivedTime - data.getSentTime());

        System.out.println("ping: " + ping + "ms");
        checkPing();
    }

    private void checkPing() {
        if(ping > 25)
            System.out.println("High ping: " + ping + "ms");
    }

    private Optional<Packet> readFromSocket() throws IOException {
        ByteBuffer headerBuffer = ByteBuffer.allocate(HEADER_SIZE);
        int bytesRead = socket.read(headerBuffer);

        if(bytesRead > 0){
            String packetCharacters = readPacketAsUTF8(headerBuffer);

            return Optional.of(Packet.fromJSON(packetCharacters));
        }else if(bytesRead < 0){
            close();
        }

        return Optional.empty();
    }

    private String readPacketAsUTF8(ByteBuffer headerBuffer) throws IOException{
        headerBuffer.flip();

        if(headerBuffer.remaining() < 4)
            throw new IllegalStateException("Expected 4 bytes, found " + headerBuffer.remaining());

        int messageLength = headerBuffer.getInt();
        byte[] packetBytes = readPacketRawBytes(messageLength);

        return new String(packetBytes, StandardCharsets.UTF_8);
    }

    private byte[] readPacketRawBytes(int messageLength) throws IOException{
        ByteBuffer buffer = ByteBuffer.allocate(messageLength);
        socket.read(buffer);

        return buffer.array();
    }

    public void sendPacket(Packet packet){
        if(closed)
            throw new IllegalStateException("Cannot send packet while socket is closed!");

        String characters  = packet.toJSON();
        ByteBuffer buffer = createBufferFromBytes(toUTF8Bytes(characters));
        buffer.flip();

        System.out.println("Sending packet: " + characters);
        try{
            socket.write(buffer);
        }catch (IOException e){
            System.err.println("Failed while writing to socket pipe: " + e);
            System.err.println("Closing socket pipe due to error!");
            close();
        }
    }

    private ByteBuffer createBufferFromBytes(byte[] messageBytes){
        int messageLength = messageBytes.length;
        ByteBuffer buffer = ByteBuffer.allocate(HEADER_SIZE + messageLength);
        buffer.putInt(messageLength);
        buffer.put(messageBytes);

        return buffer;
    }

    private byte[] toUTF8Bytes(String input) {
        return input.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public void close() {
        try{
            listener.onClose();
            socket.close();
            closed = true;
        }catch (IOException e){
            throw new RuntimeException("Failed to close socket!", e);
        }
    }

    public PacketPath getPath() {
        return path;
    }

    public boolean isClosed() {
        return closed;
    }
}
