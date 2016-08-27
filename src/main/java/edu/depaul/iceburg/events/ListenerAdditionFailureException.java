package edu.depaul.iceburg.events;

import java.io.IOException;

/**
 * Exception thrown to indicate an issue setting up a listener for a specific event(s)
 */
public class ListenerAdditionFailureException extends RuntimeException {

    public ListenerAdditionFailureException(final String message, final Throwable ex) {
        super(message, ex);
    }
}
