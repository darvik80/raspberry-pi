package xyz.crearts.rover.event;

import org.springframework.context.ApplicationEvent;

public class EventDistanceChanged  extends ApplicationEvent {
    private long distance;

    public EventDistanceChanged(Object source, long distance) {
        super(source);
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }
}
