package de.bluecolored.bluemap.api.events;

import org.jetbrains.annotations.ApiStatus;

import java.util.UUID;

@SuppressWarnings("unused")
@ApiStatus.Experimental
public class PlayerVisibilityChangeEvent {

    private final UUID playerUuid;
    private final boolean visible;

    public PlayerVisibilityChangeEvent(UUID playerUuid, boolean visible) {
        this.playerUuid = playerUuid;
        this.visible = visible;
    }

    public UUID getPlayerUuid() {
        return playerUuid;
    }

    public boolean isVisible() {
        return visible;
    }

}
