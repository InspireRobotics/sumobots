package org.inspirerobotics.sumobots.packet;

import org.inspirerobotics.sumobots.ComponentState;

import java.util.Optional;

public class PacketFactory {

    public static final String HEARTBEAT = "heartbeat";
    public static final String VERSION = "version";
    public static final String CLOSE = "close";
    public static final String UPDATE = "update";
    public static final String LOG = "log";
    public static final String JOYSTICK = "joystick";

    public static Packet createHeartbeat(PacketPath path){
        return Packet.create(HEARTBEAT, path, Optional.of(new HeartbeatData()));
    }

    public static Packet createClose(PacketPath path, String reason){
        return Packet.create(CLOSE, path, Optional.of(new CloseData(reason)));
    }

    public static Packet createUpdate(PacketPath path, ComponentState newState){
        return Packet.create(UPDATE, path, Optional.of(new UpdateStateData(newState)));
    }

    public static Packet createVersion(PacketPath path){
        return  Packet.create(VERSION, path, Optional.of(new VersionData()));
    }

    public static Packet createLog(PacketPath path, String message) {
        return Packet.create(LOG, path, Optional.of(new LogData(message)));
    }

    public static Packet createJoystick(PacketPath path, JoystickData data){
        return Packet.create(JOYSTICK, path, Optional.of(data));
    }
}
