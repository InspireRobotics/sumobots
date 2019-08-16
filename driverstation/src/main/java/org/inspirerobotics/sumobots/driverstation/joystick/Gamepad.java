package org.inspirerobotics.sumobots.driverstation.joystick;

import net.java.games.input.Controller;
import net.java.games.input.Event;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inspirerobotics.sumobots.packet.JoystickData;

public class Gamepad {

    private static final String LEFT_X_AXIS_NAME = "x";
    private static final String LEFT_Y_AXIS_NAME = "y";
    private static final String RIGHT_X_AXIS_NANE = "rx";
    private static final String RIGHT_Y_AXIS_NAME = "ry";

    private static final Logger logger = LogManager.getLogger(Gamepad.class);

    private final Controller controller;

    private float leftX;
    private float leftY;
    private float rightX;
    private float rightY;

    public Gamepad(Controller controller) {
        this.controller = controller;
    }

    public boolean poll() {
        return controller.poll();
    }

    public void update() {
        Event event = new Event();

        while(controller.getEventQueue().getNextEvent(event)) {
            handleEvent(event);
        }
    }

    void handleEvent(Event event) {
        switch(event.getComponent().getIdentifier().getName()) {
            case LEFT_X_AXIS_NAME:
                synchronized(this) {
                    leftX = event.getValue();
                }
                break;
            case LEFT_Y_AXIS_NAME:
                synchronized(this) {
                    leftY = event.getValue();
                }
                break;
            case RIGHT_X_AXIS_NANE:
                synchronized(this) {
                    rightX = event.getValue();
                }
                break;
            case RIGHT_Y_AXIS_NAME:
                synchronized(this) {
                    rightY = event.getValue();
                }
                break;
            default:
                logger.trace("Unknown identifier: " + event.getComponent().getIdentifier().getName());
        }
    }

    public JoystickData getData(){
        return new JoystickData(leftX, leftY, rightX, rightY);
    }

    public synchronized double getLeftX() {
        return leftX;
    }

    public synchronized double getLeftY() {
        return leftY;
    }

    public synchronized double getRightX() {
        return rightX;
    }

    public synchronized double getRightY() {
        return rightY;
    }
}
