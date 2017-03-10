package iceburg.events.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import iceburg.events.Event;
import iceburg.events.MultiTouchEvent;
import iceburg.events.ProximityEvent;
import iceburg.events.TouchEvent;

import java.io.IOException;

/**
 * Created by jwatts on 8/28/16.
 */
public class Test {

    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            String value = mapper.writeValueAsString(new TouchEvent("A", 10, true, System.currentTimeMillis()));
            System.out.println(value);
            Event event = mapper.readValue(value.getBytes(), Event.class);
            System.out.println(((TouchEvent)event).getSensorNumber());

            value = mapper.writeValueAsString(new ProximityEvent("A", 1, false, System.currentTimeMillis()));
            System.out.println(value);
            event = mapper.readValue(value.getBytes(), Event.class);
            System.out.println(((ProximityEvent)event).isPersonPresent());

            value = mapper.writeValueAsString(new MultiTouchEvent("B", 8, true, System.currentTimeMillis()));
            System.out.println(value);
            event = mapper.readValue(value.getBytes(), Event.class);
            System.out.println(((MultiTouchEvent)event).getSensorNumber());


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
