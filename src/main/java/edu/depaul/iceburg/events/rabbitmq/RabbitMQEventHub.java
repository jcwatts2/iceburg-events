package edu.depaul.iceburg.events.rabbitmq;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import edu.depaul.iceburg.events.Event;
import edu.depaul.iceburg.events.EventHub;
import edu.depaul.iceburg.events.EventListener;
import edu.depaul.iceburg.events.EventType;
import edu.depaul.iceburg.events.HubInitializationException;
import edu.depaul.iceburg.events.ListenerAdditionFailureException;
import edu.depaul.iceburg.events.MultiTouchEvent;
import edu.depaul.iceburg.events.ProximityEvent;
import edu.depaul.iceburg.events.TouchEvent;

import org.slf4j.Logger;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static edu.depaul.iceburg.events.EventType.MULTI_BERG;
import static edu.depaul.iceburg.events.EventType.PROXIMITY;
import static edu.depaul.iceburg.events.EventType.TOUCH;


/**
 * An implementation of the EventHub which used RabbitMQ as the underlying messaging service
 */
public class RabbitMQEventHub implements EventHub {

    private String rabbitMQUrl;

    private String exchangeName;

    private String queueName;

    private Connection connection;

    private Channel channel;

    private String icebergName;

    private Map<EventType, List<EventListener>> listeners;

    private Map<EventType, EventTransformer> transformers;

    private Map<EventType, C> consumers;

    private Logger logger = org.slf4j.LoggerFactory.getLogger(RabbitMQEventHub.class);

    public void addListener(final EventListener listener) {

        for (EventType eventType : listener.getWantedEvents()) {
            try {
                this.setupConsumer(eventType);
                this.addListener(listener, eventType);
            } catch (IOException ex) {
                throw new ListenerAdditionFailureException("Error adding listener", ex);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void init(String icebergName) throws HubInitializationException {

        this.icebergName = icebergName;
        this.listeners = new HashMap<EventType, List<EventListener>>();

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri(this.getRabbitMQUrl());
            this.connection = factory.newConnection();

            this.channel = this.connection.createChannel();
            this.channel.exchangeDeclare(this.getExchangeName(), "topic");
            this.queueName = this.channel.queueDeclare().getQueue();

            this.transformers = new HashMap<EventType, EventTransformer>();
            this.transformers.put(TOUCH, new TouchEventTransformer());
            this.transformers.put(PROXIMITY, new ProximityEventTransformer());
            this.transformers.put(MULTI_BERG, new MultiBergTouchEventTransformer());

            this.consumers = new HashMap<EventType, C>();

        } catch (Exception ex) {
            throw new HubInitializationException("Error initializing event hub.", ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void shutdown() {
        try {
            this.channel.close();
        } catch (Exception ex) {
        }
    }

    public void postEvent(final Event event) {

        EventTransformer transformer = this.transformers.get(event.getType());

        if (transformer == null) { return; }

        try {
            channel.basicPublish(this.getExchangeName(), this.getPublishRoutingKey(event.getType(), event), null,
                    transformer.messageFromEvent(event).getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setRabbitMQUrl(String url) {
        this.rabbitMQUrl = url;
    }

    public String getRabbitMQUrl() {
        return this.rabbitMQUrl;
    }

    public String getExchangeName() {
        return this.exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    void notify(Event event) {

        List<EventListener> listeners = this.listeners.get(event.getType());

        if (listeners == null) { return; }

        for (EventListener listener : listeners) {
            listener.handleEvent(event);
        }
    }

    String getPublishRoutingKey(final EventType eventType, Event event) {
        switch (eventType) {
            case MULTI_BERG:
                final MultiTouchEvent multiTouchEvent = (MultiTouchEvent) event;
                return multiTouchEvent.getSensorNumber() + ".correspondence.event";

            case PROXIMITY:
                final ProximityEvent proximityEvent = (ProximityEvent) event;
                return proximityEvent.getIceberg() + ".proximity.event";

            case TOUCH:
                final TouchEvent touchEvent = (TouchEvent) event;
                return touchEvent.getIcebergNumber() + "." + touchEvent.getSensorNumber() + ".touch.event";

            default:
                throw new Error("Undefined event type");
        }
    }

    String getBindingKeyForEvent(EventType eventType) {

        switch (eventType) {
            case MULTI_BERG :
                return "*.correspondence.event";
            case PROXIMITY :
                return this.icebergName + ".proximity.event";
            case TOUCH :
                return this.icebergName + ".*.touch.event";
            case TOUCH_ALL :
                return "*.*.touch.event";
            default :
                throw new Error("Undefined event type");
        }
    }

    String getQueueName() {
        return "events";
    }

    void addListener(EventListener listener, EventType eventType) {

        List<EventListener> eventListeners = this.listeners.get(eventType);

        if (eventListeners == null) {
            eventListeners = new ArrayList<EventListener>();
            this.listeners.put(eventType, eventListeners);
        }
        eventListeners.add(listener);
    }

    void setupConsumer(EventType eventType) throws IOException {

        C consumer = this.consumers.get(eventType);

        if (consumer == null) {

            this.channel.queueBind(this.queueName, this.getExchangeName(), this.getBindingKeyForEvent(eventType));

            consumer = new C(this.channel, this.transformers.get(eventType));
            this.consumers.put(eventType, consumer);
            channel.basicConsume(this.queueName, true, consumer);
        }
    }

    private class C extends DefaultConsumer {

        private EventTransformer transformer;

        C(Channel channel, EventTransformer transformer) {
            super(channel);
            this.transformer = transformer;
        }

        @Override
        public void handleDelivery(
            String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

            String message = new String(body, "UTF-8");

            System.out.println("Message received: " + message);

            Event event = transformer.eventFromMessage(message);
            RabbitMQEventHub.this.notify(event);
        }
    }
}
