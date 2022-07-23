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
import com.flowpowered.math.vector.Vector3d;
import de.bluecolored.bluemap.api.debug.DebugDump;

import java.util.Objects;

/**
 * A marker that is a html-element placed somewhere on the map.
 */
@DebugDump
public class HtmlMarker extends DistanceRangedMarker {

    private Vector2i anchor;
    private String html;

    /**
     * Empty constructor for deserialization.
     */
    @SuppressWarnings("unused")
    private HtmlMarker() {
        this("", Vector3d.ZERO, "");
    }

    /**
     * Creates a new {@link HtmlMarker}.
     *
     * @param label the label of the marker
     * @param position the coordinates of the marker
     * @param html the html-content of the marker
     *
     * @see #setLabel(String)
     * @see #setPosition(Vector3d)
     * @see #setHtml(String)
     */
    public HtmlMarker(String label, Vector3d position, String html) {
        this(label, position, html, new Vector2i(0, 0));
    }

    /**
     * Creates a new {@link HtmlMarker}.
     *
     * @param label the label of the marker
     * @param position the coordinates of the marker
     * @param html the html-content of the marker
     * @param anchor the anchor-point of the html-content
     *
     * @see #setLabel(String)
     * @see #setPosition(Vector3d)
     * @see #setHtml(String)
     * @see #setAnchor(Vector2i)
     */
    public HtmlMarker(String label, Vector3d position, String html, Vector2i anchor) {
        super("html", label, position);
        this.html = Objects.requireNonNull(html, "html must not be null");
        this.anchor = Objects.requireNonNull(anchor, "anchor must not be null");
    }

    /**
     * Getter for the position (in pixels) where the html-element is anchored to the map.
     * @return the anchor-position in pixels
     */
    public Vector2i getAnchor() {
        return anchor;
    }

    /**
     * Sets the position (in pixels) where the html-element is anchored to the map.
     * @param anchor the anchor-position in pixels
     */
    public void setAnchor(Vector2i anchor) {
        this.anchor = Objects.requireNonNull(anchor, "anchor must not be null");
    }

    /**
     * Sets the position (in pixels) where the html-element is anchored to the map.
     * @param x the anchor-x-position in pixels
     * @param y the anchor-y-position in pixels
     */
    public void setAnchor(int x, int y) {
        setAnchor(new Vector2i(x, y));
    }

    /**
     * Getter for the html-code of this HTML marker
     * @return the html-code
     */
    public String getHtml() {
        return html;
    }

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
    public void setHtml(String html) {
        this.html = Objects.requireNonNull(html, "html must not be null");
    }

}
