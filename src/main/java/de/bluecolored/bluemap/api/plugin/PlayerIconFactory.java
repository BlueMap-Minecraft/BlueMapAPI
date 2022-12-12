package de.bluecolored.bluemap.api.plugin;

import java.awt.image.BufferedImage;
import java.util.UUID;
import java.util.function.BiFunction;

@FunctionalInterface
public interface PlayerIconFactory extends BiFunction<UUID, BufferedImage, BufferedImage> {

    /**
     * Takes a players UUID and skin-image and creates an icon
     * @param playerUuid the players UUID
     * @param playerSkin the input image
     * @return a <b>new</b> {@link BufferedImage} generated based on the input image
     */
    BufferedImage apply(UUID playerUuid, BufferedImage playerSkin);

}
