package de.bluecolored.bluemap.api.events;

/**
 * Thrown if a listener registration was unsuccessful for some reason.
 */
public class ListenerRegistrationException extends RuntimeException {

    public ListenerRegistrationException(final String message) {
        super(message);
    }

}
