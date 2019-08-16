package org.inspirerobotics.sumobots.packet;

import java.util.Objects;

public class JoystickData {

    private final float leftX;
    private final float leftY;
    private final float rightX;
    private final float rightY;

    public JoystickData(float leftX, float leftY, float rightX, float rightY) {
        this.leftX = leftX;
        this.leftY = leftY;
        this.rightX = rightX;
        this.rightY = rightY;
    }

    public float getLeftX() {
        return leftX;
    }

    public float getLeftY() {
        return leftY;
    }

    public float getRightX() {
        return rightX;
    }

    public float getRightY() {
        return rightY;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof JoystickData)) return false;
        JoystickData that = (JoystickData) o;
        return Float.compare(that.getLeftX(), getLeftX()) == 0 &&
                Float.compare(that.getLeftY(), getLeftY()) == 0 &&
                Float.compare(that.getRightX(), getRightX()) == 0 &&
                Float.compare(that.getRightY(), getRightY()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLeftX(), getLeftY(), getRightX(), getRightY());
    }
}
