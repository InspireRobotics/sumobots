package org.inspirerobotics.sumobots.robot.event;

import org.inspirerobotics.sumobots.robot.RobotContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Queue;

public class RobotEventQueueTests {

    @BeforeEach
    public void setUp() {
        getQueue().clear();
    }

    @Test
    public void basicEventQueueTest(){
        TestRobotEvent event = new TestRobotEvent();
        RobotEventQueue.add(event);

        Assertions.assertEquals(event, RobotEventQueue.poll().get());
    }

    @Test
    public void addEventNullFailsTest(){
        Assertions.assertThrows(NullPointerException.class, () -> RobotEventQueue.add(null));
    }

    private Queue<RobotEvent> getQueue(){
        return RobotEventQueue.getInstance().getQueue();
    }

}

class TestRobotEvent implements RobotEvent{

    @Override
    public void run(RobotContainer robot) { }
}
