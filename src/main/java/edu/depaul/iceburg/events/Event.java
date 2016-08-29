package edu.depaul.iceburg.events;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * An interface to be implemented by any class that will represent an Event
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        defaultImpl = Event.class,
        visible = true
)
@JsonSubTypes({
        @Type(value = MultiTouchEvent.class, name = "MULTI_BERG"),
        @Type(value = ProximityEvent.class, name = "PROXIMITY"),
        @Type(value = TouchEvent.class, name = "TOUCH")
    })
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