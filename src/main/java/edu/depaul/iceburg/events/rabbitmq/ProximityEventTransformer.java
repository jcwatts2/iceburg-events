package edu.depaul.iceburg.events.rabbitmq;


import com.fasterxml.jackson.databind.ObjectMapper;

import edu.depaul.iceburg.events.ProximityEvent;

import java.io.IOException;


class ProximityEventTransformer implements EventTransformer<ProximityEvent> {

    private ObjectMapper mapper = new ObjectMapper();

    public ProximityEvent eventFromMessage(String message) throws IOException {
        return this.mapper.readValue(message, ProximityEvent.class);
    }

    public String messageFromEvent(ProximityEvent event) throws IOException {
        return this.mapper.writeValueAsString(event);
    }

    public String routingKeyForEvent(ProximityEvent event) {
        return event.getIceberg() + ".proximity.event";
    }
}
