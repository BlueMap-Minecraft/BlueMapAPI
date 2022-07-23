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

import de.bluecolored.bluemap.api.debug.DebugDump;
import de.bluecolored.bluemap.api.marker.Marker;
import de.bluecolored.bluemap.api.marker.POIMarker;
import com.flowpowered.math.vector.Vector2i;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

public interface WebApp {

    /**
     * Getter for the configured web-root folder
     * @return The {@link Path} of the web-root folder
     */
    @DebugDump
    Path getWebRoot();

    /**
     * Shows or hides the given player from being shown on the web-app.
     * @param player the UUID of the player
     * @param visible true if the player-marker should be visible, false if it should be hidden
     */
    void setPlayerVisibility(UUID player, boolean visible);

    /**
     * Creates an image-file with the given {@link BufferedImage} somewhere in the web-root, so it can be used in the web-app (e.g. for {@link Marker}-icons).
     *
     * <p>The given <code>path</code> is used as file-name and (separated with '/') optional folders to organize the image-files.
     * Do NOT include the file-ending! (e.g. <code>"someFolder/somePOIIcon"</code> will result in a file "somePOIIcon.png" in a folder "someFolder").</p>
     * <p>If the image file with the given path already exists, it will be replaced.</p>
     *
     * @param image the image to create
     * @param path the path/name of this image, the separator-char is '/'
     * @return the relative address of the image in the web-app,
     * which can be used as it is e.g. in the {@link POIMarker#setIcon(String, Vector2i)} method
     * @throws IOException If an {@link IOException} is thrown while writing the image
     */
    String createImage(BufferedImage image, String path) throws IOException;

    /**
     * Lists all images that are available. This includes all images previously created with the {@link #createImage(BufferedImage, String)}
     * function, but might include more.
     * @return A map of available images where:
     * <ul>
     * <li>the <b>key</b> is the image path how it would be used in the "path" parameter of the {@link #createImage(BufferedImage, String)} method</li>
     * <li>and the <b>value</b> is the relative address of the image. The same ones that are returned from the {@link #createImage(BufferedImage, String)} method</li>
     * </ul>
     * @throws IOException If an {@link IOException} is thrown while reading the images
     */
    @DebugDump
    Map<String, String> availableImages() throws IOException;

}
