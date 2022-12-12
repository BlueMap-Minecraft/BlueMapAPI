package de.bluecolored.bluemap.api.plugin;

import de.bluecolored.bluemap.api.debug.DebugDump;

public interface Plugin {

    /**
     * Get the {@link SkinProvider} that bluemap is using to fetch player-skins
     * @return the {@link SkinProvider} instance bluemap is using
     */
    @DebugDump
    SkinProvider getSkinProvider();

    /**
     * Sets the {@link SkinProvider} that bluemap will use to fetch new player-skins.
     * @param skinProvider The new {@link SkinProvider} bluemap should use
     */
    void setSkinProvider(SkinProvider skinProvider);

    /**
     * Get the {@link PlayerIconFactory} that bluemap is using to convert a player-skin into the icon-image that is used
     * for the Player-Markers
     * @return The {@link PlayerIconFactory} bluemap uses to convert skins into player-marker icons
     */
    @DebugDump
    PlayerIconFactory getPlayerMarkerIconFactory();

    /**
     * Set the {@link PlayerIconFactory} that bluemap will use to convert a player-skin into the icon-image that is used
     * for the Player-Markers
     * @param playerMarkerIconFactory The {@link PlayerIconFactory} bluemap uses to convert skins into player-marker icons
     */
    void setPlayerMarkerIconFactory(PlayerIconFactory playerMarkerIconFactory);

}
