package edu.depaul.iceburg.events.rabbitmq;


import edu.depaul.iceburg.events.Event;

import java.io.IOException;


/**
 * Created by jwatts on 8/25/16.
 */
public interface EventTransformer<T extends Event> {

    T eventFromMessage(String message) throws IOException;

    String messageFromEvent(T event) throws IOException;

    String routingKeyForEvent(T event);
}
