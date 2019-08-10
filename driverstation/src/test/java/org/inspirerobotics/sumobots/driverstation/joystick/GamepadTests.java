package org.inspirerobotics.sumobots.driverstation.joystick;

import net.java.games.input.AbstractComponent;
import net.java.games.input.Component;
import net.java.games.input.Event;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GamepadTests {

    private final Gamepad gamepad = new Gamepad(null);

    @Test
    void leftYAxisTest() {
        Event event = createEvent("y", .124f);

        gamepad.handleEvent(event);

        Assertions.assertEquals(.124f, gamepad.getLeftY());
    }

    @Test
    void leftXAxisTest() {
        Event event = createEvent("x", .521f);

        gamepad.handleEvent(event);

        Assertions.assertEquals(.521f, gamepad.getLeftX());
    }

    @Test
    void rightYAxisTest() {
        Event event = createEvent("ry", -.411f);

        gamepad.handleEvent(event);

        Assertions.assertEquals(-.411f, gamepad.getRightY());
    }

    @Test
    void rightXAxisTest() {
        Event event = createEvent("rx", .888f);

        gamepad.handleEvent(event);

        Assertions.assertEquals(.888f, gamepad.getRightX());
    }

    public static Event createEvent(String identifier, float value){
        Component component = new TestComponent(identifier);
        Event event = new Event();
        event.set(component, value, 0L);

        return event;
    }
}
class TestComponent extends AbstractComponent{

    public TestComponent(String identifier) {
        super("", createIdentifier(identifier));
    }

    private static Identifier createIdentifier(String identifier) {
        return new Identifier(identifier){};
    }

    @Override
    protected float poll() {
        return 0;
    }

    @Override
    public boolean isRelative() {
        return false;
    }
}