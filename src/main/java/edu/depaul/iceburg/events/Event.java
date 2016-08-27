package edu.depaul.iceburg.events;

import jdk.nashorn.internal.ir.annotations.Ignore;

/**
 * An interface to be implemented by any class that will represent an Event
 */
public interface Event {

    /**
     * Returns the type of the event
     *
     * @return the type of the Event
     */
    EventType getType();

    /**
     * The time at which the event was created/posted
     *
     * @return long time (milliseconds since the epoch)
     */
    long getTime();
}