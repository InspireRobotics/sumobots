package org.inspirerobotics.sumobots.driverstation.util;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BackendEventQueue {

    private static Optional<BackendEventQueue> instance = Optional.empty();

    private final ConcurrentLinkedQueue<BackendEvent> queue;

    private BackendEventQueue() {
        queue = new ConcurrentLinkedQueue<>();
    }

    public static void add(BackendEvent e){
        Objects.requireNonNull(e, "Event cannot be null!");

        getInstance().queue.add(e);
    }

    public static Optional<BackendEvent> poll(){
        return Optional.ofNullable(getInstance().queue.poll());
    }

    public ConcurrentLinkedQueue<BackendEvent> getQueue() {
        return queue;
    }

    static BackendEventQueue getInstance(){
        if(instance.isPresent() == false){
            synchronized (BackendEventQueue.class) {
                if(instance.isPresent() == false){
                    instance = Optional.of(new BackendEventQueue());
                }
            }
        }

        return instance.get();
    }
}
