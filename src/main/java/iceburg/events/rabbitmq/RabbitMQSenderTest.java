package iceburg.events.rabbitmq;


import iceburg.events.TouchEvent;

/**
 * Created by jwatts on 8/27/16.
 */
public class RabbitMQSenderTest {

    public static void main(String[] args) {

        RabbitMQEventHub hub = new RabbitMQEventHub();
        hub.setRabbitMQUrl("amqp://localhost");
        hub.setExchangeName("events");
        hub.init("A");


        try {
            hub.postEvent(new TouchEvent("A", 1, true, System.currentTimeMillis()));
            Thread.sleep(5000);
            hub.postEvent(new TouchEvent("B", 1, true, System.currentTimeMillis()));

            Thread.sleep(10000);
            hub.postEvent(new TouchEvent("A", 1, false, System.currentTimeMillis()));

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
