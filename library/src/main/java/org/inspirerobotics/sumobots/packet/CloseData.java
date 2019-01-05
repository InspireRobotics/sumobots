package org.inspirerobotics.sumobots.packet;

public class CloseData {

    private String reason;

    public CloseData(String reason) {
        this.reason = reason == null ? "No reason listed!" : reason;
    }

    public String getReason() {
        return reason;
    }
}
