package edu.depaul.iceburg.events;


/**
 * An interface to be implemented by any class that will be used to listen for and send events
 */
public interface EventHub {

    /**
     * Performs any initialization of the event hub
     *
     * @param icebergName the name of the iceberg for the event hub
     */
    void init(String icebergName);

    /**
     * Performs any actions necessary to shut down the event hub
     */
    void shutdown();

    /**
     * Adds the specified EventListener to the hub
     *
     * @param listener the EventListener to be added
     */
    void addListener(final EventListener listener);

    /**
     * Post and Event to the hub
     *
     * @param event the event to be posted
     */
    void postEvent(Event event);
}
