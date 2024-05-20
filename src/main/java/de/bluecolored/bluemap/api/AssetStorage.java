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

import de.bluecolored.bluemap.api.markers.POIMarker;
import de.bluecolored.bluemap.api.markers.HtmlMarker;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

/**
 * A storage that is able to hold any "asset"-data for a map. For example images, icons, scripts or json-files.
 */
@SuppressWarnings("unused")
public interface AssetStorage {

    /**
     * Writes a new asset into this storage, overwriting any existent assets with the same name.<br>
     * Use the returned {@link OutputStream} to write the asset-data. The asset will be added to the storage as soon as that stream
     * gets closed!<br>
     * <br>
     * Example:
     * <pre>
     * try (OutputStream out = assetStorage.writeAsset("image.png")) {
     *     ImageIO.write(image, "png", out);
     * }
     * </pre>
     * @param name The (unique) name for this asset
     * @return An {@link OutputStream} that should be used to write the asset and closed once!
     * @throws IOException when the underlying storage rises an IOException
     */
    OutputStream writeAsset(String name) throws IOException;

    /**
     * Reads an asset from this storage.<br>
     * Use the returned {@link InputStream} to read the asset-data.<br>
     * <br>
     * Example:
     * <pre>
     * Optional&lt;InputStream&gt; optIn = assetStorage.readAsset("image.png");
     * if (optIn.isPresent()) {
     *     try (InputStream in = optIn.get()) {
     *         BufferedImage image = ImageIO.read(in);
     *     }
     * }
     * </pre>
     * @param name The name of the asset that should be read from the storage.
     * @return An {@link Optional} with an {@link InputStream} when the asset is found, from which the asset can be read.
     * Or an empty optional if there is no asset with this name.
     * @throws IOException when the underlying storage rises an IOException
     */
    Optional<InputStream> readAsset(String name) throws IOException;

    /**
     * Checks if an asset exists in this storage without reading it.<br>
     * This is useful if the asset has a lot of data and using {@link #readAsset(String)}
     * just to check if the asset is present would be wasteful.
     * @param name The name of the asset to check for
     * @return <code>true</code> if the asset is found, <code>false</code> if not
     * @throws IOException when the underlying storage rises an IOException
     */
    boolean assetExists(String name) throws IOException;

    /**
     * Returns the relative URL that can be used by the webapp to request this asset.<br>
     * This is the url that you can e.g. use in {@link POIMarker}s or {@link HtmlMarker}s to add an icon.<br>
     * If there is no asset with this name, then this method returns the URL that an asset with such a name <i>would</i>
     * have if it would be added later.
     * @param name The name of the asset
     * @return The relative URL for an asset with the given name
     */
    String getAssetUrl(String name);

    /**
     * Deletes the asset with the given name from this storage, if it exists.<br>
     * If there is no asset with this name, this method does nothing.
     * @param name The name of the asset that should be deleted
     * @throws IOException when the underlying storage rises an IOException
     */
    void deleteAsset(String name) throws IOException;

}
