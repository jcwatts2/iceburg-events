package iceburg.events;


import java.util.List;


/**
 * Interface to be implemented by an class that will be used to listen for events
 */
public interface EventListener {

    /**
     * Returns the EventTypes in which this EventListener is interested
     *
     * @return List of EventType(s)
     */
    List<EventType> getWantedEvents();

    /**
     * Called to handle an event
     *
     * @param event the received Event
     */
    void handleEvent(Event event);
}
