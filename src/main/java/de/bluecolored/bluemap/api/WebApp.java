/*
 * This file is part of BlueMap, licensed under the MIT License (MIT).
 *
 * Copyright (c) Blue (Lukas Rieger) <https://bluecolored.de>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package de.bluecolored.bluemap.api;

import org.jetbrains.annotations.ApiStatus;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public interface WebApp {

    /**
     * Getter for the configured web-root folder
     * @return The {@link Path} of the web-root folder
     */
    Path getWebRoot();

    /**
     * Shows or hides the given player from being shown on the web-app.
     * @param player the UUID of the player
     * @param visible true if the player-marker should be visible, false if it should be hidden
     */
    @ApiStatus.Experimental
    void setPlayerVisibility(UUID player, boolean visible);

    /**
     * Returns <code>true</code> if the given player is currently visible on the web-app.
     * @see #setPlayerVisibility(UUID, boolean)
     * @param player the UUID of the player
     */
    @ApiStatus.Experimental
    boolean getPlayerVisibility(UUID player);

    /**
     * Registers a css-style so the webapp loads it.<br>
     * This method should only be used inside the {@link Consumer} that got registered <i>(before bluemap loaded,
     * pre server-start!)</i> to {@link BlueMapAPI#onEnable(Consumer)}.<br>
     * Invoking this method at any other time is not supported.<br>
     * Style-registrations are <b>not persistent</b>, register your style each time bluemap enables!<br>
     * <br>
     * Example:
     * <pre>
     * BlueMapAPI.onEnable(api -&gt; {
     *    api.getWebApp().registerStyle("js/my-custom-style.css");
     * });
     * </pre>
     * @param url The (relative) URL that links to the style.css file. The {@link #getWebRoot()}-method can be used to
     *            create the custom file in the correct location and make it available to the web-app.
     */
    void registerStyle(String url);

    /**
     * Registers a js-script so the webapp loads it.<br>
     * This method should only be used inside the {@link Consumer} that got registered <i>(before bluemap loaded,
     * pre server-start!)</i> to {@link BlueMapAPI#onEnable(Consumer)}.<br>
     * Invoking this method at any other time is not supported.<br>
     * Script-registrations are <b>not persistent</b>, register your script each time bluemap enables!<br>
     * <br>
     * Example:
     * <pre>
     * BlueMapAPI.onEnable(api -&gt; {
     *    api.getWebApp().registerScript("js/my-custom-script.js");
     * });
     * </pre>
     * @param url The (relative) URL that links to the script.js file. The {@link #getWebRoot()}-method can be used to
     *            create the custom file in the correct location and make it available to the web-app.
     */
    void registerScript(String url);

    // ------

    /**
     * @deprecated You should use the {@link #getWebRoot()} method to create the image-files you need, or store map/marker
     * specific images in the map's storage (See: {@link BlueMapMap#getAssetStorage()})!
     */
    @Deprecated(forRemoval = true)
    String createImage(BufferedImage image, String path) throws IOException;

    /**
     * @deprecated You should use the {@link #getWebRoot()} method to find the image-files you need, or read map/marker
     * specific images from the map's storage (See: {@link BlueMapMap#getAssetStorage()})!
     */
    @Deprecated(forRemoval = true)
    Map<String, String> availableImages() throws IOException;

}
