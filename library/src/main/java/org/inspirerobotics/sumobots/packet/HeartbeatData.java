package org.inspirerobotics.sumobots.packet;

public class HeartbeatData {

    private final long sentTime;

    public HeartbeatData() {
        this.sentTime = System.currentTimeMillis();
    }

    public long getSentTime() {
        return sentTime;
    }
}
