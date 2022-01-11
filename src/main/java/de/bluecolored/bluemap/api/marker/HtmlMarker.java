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
package de.bluecolored.bluemap.api.marker;

import com.flowpowered.math.vector.Vector2i;
import de.bluecolored.bluemap.api.BlueMapAPI;

public interface HtmlMarker extends Marker, DistanceRangedMarker {

    /**
     * Getter for the position (in pixels) where the html-element is anchored to the map.
     * @return the anchor-position in pixels
     */
    Vector2i getAnchor();

    /**
     * Sets the position (in pixels) where the html-element is anchored to the map.
     * @param anchor the anchor-position in pixels
     */
    void setAnchor(Vector2i anchor);

    /**
     * Sets the position (in pixels) where the html-element is anchored to the map.
     * @param x the anchor-x-position in pixels
     * @param y the anchor-y-position in pixels
     */
    default void setAnchor(int x, int y) {
        setAnchor(new Vector2i(x, y));
    }

    /**
     * Getter for the html-code of this HTML marker
     * @return the html-code
     */
    String getHtml();

    /**
     * Sets the html for this {@link HtmlMarker}.
     *
     * <p>
     * 	<b>Important:</b><br>
     * 	Make sure you escape all html-tags from possible user inputs to prevent possible <a href="https://en.wikipedia.org/wiki/Cross-site_scripting">XSS-Attacks</a> on the web-client!
     * </p>
     *
     * @param html the html that will be inserted as the marker.
     */
    void setHtml(String html);

}
