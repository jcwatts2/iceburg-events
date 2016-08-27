package edu.depaul.iceburg.events.rabbitmq;


import com.fasterxml.jackson.databind.ObjectMapper;

import edu.depaul.iceburg.events.MultiTouchEvent;

import java.io.IOException;


/**
 * Created by jwatts on 8/23/16.
 */
class MultiBergTouchEventTransformer implements EventTransformer<MultiTouchEvent> {

    private ObjectMapper mapper = new ObjectMapper();

    public MultiTouchEvent eventFromMessage(String message) throws IOException {
        return this.mapper.readValue(message, MultiTouchEvent.class);
    }

    public String messageFromEvent(MultiTouchEvent event) throws IOException {
        return this.mapper.writeValueAsString(event);
    }

    public String routingKeyForEvent(MultiTouchEvent event) {
        return event.getSensorNumber() + ".correspondence.event";
    }
}
