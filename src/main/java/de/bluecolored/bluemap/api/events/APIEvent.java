package de.bluecolored.bluemap.api.events;

import de.bluecolored.bluemap.api.BlueMapAPI;

/**
 * All events involving any change with {@link BlueMapAPI}
 */
public abstract class APIEvent {

    private final BlueMapAPI api;

    public APIEvent(BlueMapAPI api) {
        this.api = api;
    }

    /**
     * Returns the {@link BlueMapAPI} instance involved with this event.
     * @return The {@link BlueMapAPI} instance
     */
    public BlueMapAPI getAPI() {
        return api;
    }

    /**
     * Called when {@link BlueMapAPI} got enabled and is now available.
     */
    public static class Enable extends APIEvent {



        public Enable(BlueMapAPI api) {
            super(api);
        }

    }

    /**
     * Called when {@link BlueMapAPI} gets disabled and will soon be no longer available.
     */
    public static class Disable extends APIEvent {

        public Disable(BlueMapAPI api) {
            super(api);
        }

    }

}
