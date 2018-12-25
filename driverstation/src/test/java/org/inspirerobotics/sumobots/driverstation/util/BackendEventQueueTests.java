package org.inspirerobotics.sumobots.driverstation.util;

import org.inspirerobotics.sumobots.driverstation.BackendWorker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Queue;

public class BackendEventQueueTests {

    @BeforeEach
    public void setUp() {
        getQueue().clear();
    }

    @Test
    public void basicEventQueueTest(){
        TestBackendEvent event = new TestBackendEvent();
        BackendEventQueue.add(event);

        Assertions.assertEquals(event, BackendEventQueue.poll().get());
    }

    @Test
    public void addEventNullFailsTest(){
        Assertions.assertThrows(NullPointerException.class, () -> BackendEventQueue.add(null));
    }

    private Queue<BackendEvent> getQueue(){
        return BackendEventQueue.getInstance().getQueue();
    }

}
class TestBackendEvent implements BackendEvent{

    @Override
    public void run(BackendWorker backendThread) {

    }
}
