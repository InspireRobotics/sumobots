package org.inspirerobotics.sumobots.packet;

import java.util.Optional;

public class PacketFactory {

    public static final String HEARTBEAT = "heartbeat";
    public static final String VERSION = "version";
    public static final String CLOSE = "close";

    public static Packet createHeartbeat(PacketPath path){
        return Packet.create(HEARTBEAT, path, Optional.of(new HeartbeatData()));
    }

    public static Packet createClose(PacketPath path, String reason){
        return Packet.create(CLOSE, path, Optional.of(new CloseData(reason)));
    }

    public static Packet createVersion(PacketPath path){
        return  Packet.create(VERSION, path, Optional.of(new VersionData()));
    }
}
