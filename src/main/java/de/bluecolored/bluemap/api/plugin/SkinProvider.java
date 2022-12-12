package de.bluecolored.bluemap.api.plugin;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

/**
 * A skin-provider capable of loading minecraft player-skins for a given UUID
 */
@FunctionalInterface
public interface SkinProvider {

    /**
     * Attempts to load a minecraft-skin from this skin-provider.
     * @return an {@link Optional} containing a {@link BufferedImage} with the skin-image or an empty Optional if there is no
     * skin for this UUID
     * @throws IOException if something went wrong trying to load the skin
     */
    Optional<BufferedImage> load(UUID playerUUID) throws IOException;

}
