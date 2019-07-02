package org.inspirerobotics.sumobots.robot.event;

import org.inspirerobotics.sumobots.ComponentState;
import org.inspirerobotics.sumobots.robot.RobotContainer;

public class StateChangeEvent implements RobotEvent {

    private final ComponentState state;

    public StateChangeEvent(ComponentState state) {
        this.state = state;
    }

    @Override
    public void run(RobotContainer container) {
        container.onStateChange(state);
    }
}
