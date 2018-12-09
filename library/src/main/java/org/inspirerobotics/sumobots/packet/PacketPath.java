package org.inspirerobotics.sumobots.packet;

import org.inspirerobotics.sumobots.FmsComponent;

import java.util.Objects;

public class PacketPath {

    private final FmsComponent source;
    private final FmsComponent destination;

    public PacketPath(FmsComponent source, FmsComponent destination) {
        Objects.requireNonNull(source, "Source cannot be null");
        Objects.requireNonNull(destination, "Destination cannot be null");

        if(destination.equals(source))
            throw new IllegalArgumentException("Destination cannot equal source");

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
