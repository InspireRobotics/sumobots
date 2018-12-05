package org.inspirerobotics.sumobots.packet;

import java.util.Objects;

public class PingData {

    private long startTime;

    public PingData() {
        this.startTime = System.currentTimeMillis();
    }

    public long getStartTime() {
        return startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PingData)) return false;
        PingData pingData = (PingData) o;
        return startTime == pingData.startTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime);
    }

    @Override
    public String toString() {
        return "PingData{" +
                "startTime=" + startTime +
                '}';
    }
}
