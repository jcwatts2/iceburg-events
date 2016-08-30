package iceburg.events;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.ToString;


/**
 * An event posted when a person is detected near the iceberg
 */
@ToString
public class ProximityEvent implements Event {

    private String iceberg;

    private boolean personPresent;

    private long time;

    public ProximityEvent() {
        super();
    }

    /**
     * Creates an instance of the ProximityEvent
     *
     * @param iceberg the id of the iceberg
     * @param personPresent indicates if a user is detected (true) or not (false)
     * @param time the event was created (milliseconds since epoch)
     */
    public ProximityEvent(String iceberg, boolean personPresent, long time) {
        this.iceberg = iceberg;
        this.personPresent = personPresent;
        this.time = time;
    }

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
    public String getIceberg() {
        return this.iceberg;
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
