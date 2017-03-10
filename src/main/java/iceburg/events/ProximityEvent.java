package iceburg.events;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.ToString;


/**
 * An event posted when a person is detected near the iceberg
 */
@ToString
public class ProximityEvent implements Event {

    private String icebergId;

    private boolean personPresent;

    private long time;

    private int sensorNumber;

    public ProximityEvent() {
        super();
    }

    /**
     * Creates an instance of the ProximityEvent
     *
     * @param icebergId the id of the iceberg
     * @param sensorNumber the number of the proximity sensor
     * @param personPresent indicates if a user is detected (true) or not (false)
     * @param time the event was created (milliseconds since epoch)
     */
    public ProximityEvent(String icebergId, int sensorNumber, boolean personPresent, long time) {
        this.icebergId = icebergId;
        this.sensorNumber = sensorNumber;
        this.personPresent = personPresent;
        this.time = time;
    }

    /**
     * Returns the sensor number
     *
     * @return sensor number
     */
    public Integer getSensorNumber() { return this.sensorNumber; }

    /**
     * Returns if a user is detected (true) or not (false)
     *
     * @return boolean person detected (true) or not (false)
     */
    public boolean isPersonPresent() {
        return this.personPresent;
    }

    /**
     * Returns the id of the iceberg
     *
     * @return the id of the iceberg
     */
    public String getIcebergId() {
        return this.icebergId;
    }

    /**
     * {@inheritDoc}
     */
    @JsonIgnore
    public EventType getType() {
        return EventType.PROXIMITY;
    }

    /**
     * {@inheritDoc}
     */
    public long getTime() {
        return this.time;
    }
}
