package de.bluecolored.bluemap.api.events;

import de.bluecolored.bluemap.api.BlueMapMap;

public abstract class MapEvent {

    private final BlueMapMap map;

    public MapEvent(final BlueMapMap map) {
        this.map = map;
    }

    public BlueMapMap getMap() {
        return map;
    }

    public static class Freeze extends MapEvent {

        public Freeze(final BlueMapMap map) {
            super(map);
        }

    }

    public static class Unfreeze extends MapEvent {

        public Unfreeze(final BlueMapMap map) {
            super(map);
        }

    }

}
