package org.inspirerobotics.sumobots.packet;

import com.google.gson.Gson;
import org.inspirerobotics.sumobots.ControlSystemComponent;

public class Packet {

    private final ControlSystemComponent source;
    private final ControlSystemComponent destination;

    private final String action;

    Packet(String action, ControlSystemComponent source, ControlSystemComponent destination) {
        if(action == null)
            throw new IllegalArgumentException("Action cannot be null!");

        if(source == null)
            throw new IllegalArgumentException("Source cannot be null!");

        if(destination == null)
            throw new IllegalArgumentException("Destination cannot be null!");

        this.action = action;
        this.destination = destination;
        this.source = source;
    }

    public static Packet create(String action, ControlSystemComponent source, ControlSystemComponent destination){
        return new Packet(action, source, destination);
    }

    public static Packet fromJSON(String input){
        Gson gson = new Gson();
        return gson.fromJson(input, Packet.class);
    }

    public String toJSON(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public ControlSystemComponent getSource() {
        return source;
    }

    public String getAction() {
        return action;
    }

    public ControlSystemComponent getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return String.format("Packet[%s -> %s: %s]", source, destination, action);
    }
}
