package edu.depaul.iceburg.events.rabbitmq;


import edu.depaul.iceburg.events.Event;
import edu.depaul.iceburg.events.EventListener;
import edu.depaul.iceburg.events.EventType;
import edu.depaul.iceburg.events.TouchEvent;

import java.util.Arrays;
import java.util.List;


/**
 * Created by jwatts on 8/26/16.
 */
class RabbitMQReceiverTest {

    public static void main(String[] args) {

        RabbitMQEventHub hub = new RabbitMQEventHub();
        hub.setRabbitMQUrl("amqp://localhost");
        hub.setExchangeName("events");
        hub.init("A");

        hub.addListener(new EventListener() {

            public List<EventType> getWantedEvents() {
                return Arrays.asList(new EventType[]{EventType.TOUCH});
            }

            public void handleEvent(final Event e) {
                final TouchEvent event = (TouchEvent)e;
                System.out.println("Event: " + event.getType() + ", iceberg=" + event.getIcebergNumber() +
                        ", sensorNumber=" + event.getSensorNumber() + ", touched=" + event.isTouched());
            }
        });
    }
}
