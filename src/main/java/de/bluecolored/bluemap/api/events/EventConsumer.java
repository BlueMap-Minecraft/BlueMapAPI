package de.bluecolored.bluemap.api.events;

/**
 * A consumer accepting events
 * @param <T> The type of events that this consumer accepts
 */
@FunctionalInterface
public interface EventConsumer<T> {

    /**
     * Performs an action on the provided event.
     * @param event The event
     * @throws Exception If an exception occurred while handling the event
     */
    void accept(T event) throws Exception;

}
