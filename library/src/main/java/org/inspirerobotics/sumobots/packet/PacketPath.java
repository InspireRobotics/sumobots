package org.inspirerobotics.sumobots.packet;

import org.inspirerobotics.sumobots.FmsComponent;

public class PacketPath {

    private final FmsComponent source;
    private final FmsComponent destination;

    public PacketPath(FmsComponent source, FmsComponent destination) {
        if(source == null)
            throw new IllegalArgumentException("Source cannot be null!");

        if(destination == null)
            throw new IllegalArgumentException("Destination cannot be null!");

        this.source = source;
        this.destination = destination;
    }

    public FmsComponent getDestination() {
        return destination;
    }

    public FmsComponent getSource() {
        return source;
    }
}
