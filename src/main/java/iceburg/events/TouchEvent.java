package iceburg.events;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.ToString;


/**
 * An event posted when a sensor is touched on the iceberg
 */
@ToString
public class TouchEvent implements Event {

    private Integer sensorNumber;

    private String icebergId;

    private boolean touched;

    private long time;

    public TouchEvent() {
        super();
    }

    /**
     * Creates an instance of the TouchEvent
     *
     * @param icebergId the id of the iceberg
     * @param sensorNumber the id of the sensor which was touched
     * @param touched indicates if a user is touching the sensor or has disengaged
     * @param time the time the event is created/posted (milliseconds since the epoch)
     */
    public TouchEvent(String icebergId, Integer sensorNumber, boolean touched, long time) {
        this.icebergId = icebergId;
        this.sensorNumber = sensorNumber;
        this.touched = touched;
        this.time = time;
    }

    /**
     * Returns the id of the sensor (facet)
     *
     * @return Integer id of the sensor (facet)
     */
    public Integer getSensorNumber() {
        return sensorNumber;
    }

    /**
     * Returns the id of the iceberg
     *
     * @return String id of the iceberg
     */
    public String getIcebergId() {
        return this.icebergId;
    }

    /**
     * Returns whether or not the sensor is currently being touched
     *
     * @return indicated if the sensor is being touched (true) or not (false)
     */
    public boolean isTouched() {
        return this.touched;
    }

    /**
     * {@inheritDoc}
     */
    @JsonIgnore
    public EventType getType() {
        return EventType.TOUCH;
    }

    /**
     * {@inheritDoc}
     */
    public long getTime() {
        return this.time;
    }
}
