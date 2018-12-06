package org.inspirerobotics.sumobots.socket;

import org.inspirerobotics.sumobots.packet.Packet;

public interface SocketPipeListener {

    void onPacketReceived(Packet packet);

    default void onClose(){}

    default void update(){}

}
