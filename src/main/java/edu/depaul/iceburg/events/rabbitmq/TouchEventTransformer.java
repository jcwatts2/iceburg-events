package edu.depaul.iceburg.events.rabbitmq;


import com.fasterxml.jackson.databind.ObjectMapper;

import edu.depaul.iceburg.events.TouchEvent;

import java.io.IOException;


/**
 * Created by jwatts on 8/23/16.
 */
class TouchEventTransformer implements EventTransformer<TouchEvent> {

    private ObjectMapper mapper = new ObjectMapper();

    public TouchEvent eventFromMessage(String message) throws IOException {
        return mapper.readValue(message, TouchEvent.class);
    }

    public String messageFromEvent(TouchEvent event) throws IOException {
        return mapper.writeValueAsString(event);
    }

    public String routingKeyForEvent(TouchEvent event) {
        return event.getIcebergNumber() + "."  + event.getSensorNumber() + ".touch.event";
    }
}
