package org.inspirerobotics.sumobots.field.driverstation;

import org.inspirerobotics.sumobots.packet.Packet;
import org.inspirerobotics.sumobots.socket.SocketPipe;
import org.inspirerobotics.sumobots.socket.SocketPipeListener;

import java.util.Optional;

public class DriverstationConnection implements SocketPipeListener {

    private Optional<SocketPipe> pipe;

    @Override
    public void onPacketReceived(Packet packet) {

    }

    public Optional<SocketPipe> getPipe() {
        return pipe;
    }

    public void setPipe(SocketPipe pipe) {
        this.pipe = Optional.ofNullable(pipe);
    }
}
