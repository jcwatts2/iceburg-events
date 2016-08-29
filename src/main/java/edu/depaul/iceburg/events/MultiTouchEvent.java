package edu.depaul.iceburg.events;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * An event posted when corresponding touch sensors on two icebergs are being touched (or just released)
 */
public class MultiTouchEvent extends TouchEvent implements Event {

    /**
     * Default constuctor
     */
    public MultiTouchEvent() {
        super();
    }

    /**
     * Creates an instance of the MultiTouchEvent
     *
     * @param icebergId the id of the iceberg publishing the event
     * @param sensorNumber the id of the sensor being touched
     * @param touched indicates if users are touching the sensor or have disengaged
     * @param time the time the event is created/posted (milliseconds since the epoch)
     */
    public MultiTouchEvent(String icebergId, Integer sensorNumber, boolean touched, long time) {
        super(icebergId, sensorNumber, touched, time);
    }

    /**
     * {@inheritDoc}
     */
    @JsonIgnore
    public EventType getType() {
        return EventType.MULTI_BERG;
    }
}
