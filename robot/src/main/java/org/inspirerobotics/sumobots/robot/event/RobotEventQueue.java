package org.inspirerobotics.sumobots.robot.event;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RobotEventQueue {

    private static Optional<RobotEventQueue> instance = Optional.empty();

    private final ConcurrentLinkedQueue<RobotEvent> queue;

    private RobotEventQueue() {
        queue = new ConcurrentLinkedQueue<>();
    }

    public static void add(RobotEvent e){
        Objects.requireNonNull(e, "Event cannot be null!");

        getInstance().queue.add(e);
    }

    public static Optional<RobotEvent> poll(){
        return Optional.ofNullable(getInstance().queue.poll());
    }

    public ConcurrentLinkedQueue<RobotEvent> getQueue() {
        return queue;
    }

    static RobotEventQueue getInstance(){
        if(instance.isPresent() == false){
            synchronized (RobotEventQueue.class) {
                if(instance.isPresent() == false){
                    instance = Optional.of(new RobotEventQueue());
                }
            }
        }

        return instance.get();
    }
}
