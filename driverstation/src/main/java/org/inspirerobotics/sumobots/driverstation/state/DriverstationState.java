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
        Objects.requireNonNull(currentState, "ComponentState cannot be null!");

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DriverstationState)) return false;
        DriverstationState that = (DriverstationState) o;
        return startTime == that.startTime &&
                fieldConnected == that.fieldConnected &&
                robotConnected == that.robotConnected &&
                joysticksConnected == that.joysticksConnected &&
                mode == that.mode &&
                currentState == that.currentState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mode, currentState, startTime, fieldConnected, robotConnected, joysticksConnected);
    }

    @Override
    public String toString() {
        return "DriverstationState{" +
                "mode=" + mode +
                ", currentState=" + currentState +
                '}';
    }
}
