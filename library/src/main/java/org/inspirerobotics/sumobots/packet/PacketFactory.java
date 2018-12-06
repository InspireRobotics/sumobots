package org.inspirerobotics.sumobots.packet;

import java.util.Optional;

public class PacketFactory {

    public static final String HEARTBEAT = "heartbeat";

    public static Packet createHeartbeat(PacketPath path){
        return Packet.create(HEARTBEAT, path, Optional.of(new HeartbeatData()));
    }
}
