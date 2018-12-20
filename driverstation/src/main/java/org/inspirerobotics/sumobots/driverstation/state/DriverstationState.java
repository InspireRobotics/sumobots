package org.inspirerobotics.sumobots.driverstation.state;

import org.inspirerobotics.sumobots.SumobotsRuntimeException;
import org.inspirerobotics.sumobots.ComponentState;

import java.util.Objects;

public class DriverstationState implements Cloneable{

    private final DriverstationMode mode;
    private final ComponentState currentState;
    private final long startTime;

    private boolean fieldConnected = false;
    private boolean robotConnected = false;
    private boolean joysticksConnected = false;

    public DriverstationState(DriverstationMode mode, ComponentState currentState) {
        Objects.requireNonNull(mode, "DriverstationMode cannot be null!");
        Objects.requireNonNull(mode, "TimePeriod cannot be null!");

        this.mode = mode;
        this.currentState = currentState;
        this.startTime = System.currentTimeMillis();
    }

    public boolean isFieldConnected() {
        return fieldConnected;
    }

    public void setFieldConnected(boolean fieldConnected) {
        this.fieldConnected = fieldConnected;
    }

    public boolean isRobotConnected() {
        return robotConnected;
    }

    public void setRobotConnected(boolean robotConnected) {
        this.robotConnected = robotConnected;
    }

    public boolean isJoysticksConnected() {
        return joysticksConnected;
    }

    public void setJoysticksConnected(boolean joysticksConnected) {
        this.joysticksConnected = joysticksConnected;
    }

    public DriverstationMode getMode() {
        return mode;
    }

    public long getStartTime() {
        return startTime;
    }

    public ComponentState getCurrentState() {
        return currentState;
    }

    @Override
    public DriverstationState clone()  {
        try{
            return (DriverstationState) super.clone();
        }catch (CloneNotSupportedException e){
            throw new SumobotsRuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "DriverstationState{" +
                "mode=" + mode +
                ", currentState=" + currentState +
                '}';
    }
}
