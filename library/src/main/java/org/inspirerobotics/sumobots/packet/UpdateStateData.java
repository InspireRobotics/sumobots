package org.inspirerobotics.sumobots.packet;

import org.inspirerobotics.sumobots.ComponentState;

public class UpdateStateData {

    private ComponentState newState;

    public UpdateStateData(ComponentState newState) {
        this.newState = newState;
    }

    public ComponentState getNewState() {
        return newState;
    }
}
