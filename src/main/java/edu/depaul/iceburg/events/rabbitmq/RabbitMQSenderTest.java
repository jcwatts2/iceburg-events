package edu.depaul.iceburg.events.rabbitmq;


import edu.depaul.iceburg.events.TouchEvent;

/**
 * Created by jwatts on 8/27/16.
 */
public class RabbitMQSenderTest {

    public static void main(String[] args) {

        RabbitMQEventHub hub = new RabbitMQEventHub();
        hub.setRabbitMQUrl("amqp://localhost");
        hub.setExchangeName("events");
        hub.init("A");

        hub.postEvent(new TouchEvent("A", 1, true, System.currentTimeMillis()));

        try {
            Thread.sleep(5000);
            hub.postEvent(new TouchEvent("A", 1, false, System.currentTimeMillis()));

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
