package org.inspirerobotics.sumobots.packet;

public class LogData {

    private final String line;

    public LogData(String lines) {
        this.line = lines;
    }

    public String getLine() {
        return line;
    }
}
