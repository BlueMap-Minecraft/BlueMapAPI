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
package de.bluecolored.bluemap.api.markers;

/**
 * @see POIMarker
 * @see ShapeMarker
 * @see ExtrudeMarker
 * @see LineMarker
 */
public interface DetailMarker {

    /**
     * Getter for the detail of this marker. The label can include html-tags.
     * @return the detail of this {@link Marker}
     */
    String getDetail();

    /**
     * Sets the detail of this {@link Marker}. The detail can include html-tags.<br>
     * This is the text that will be displayed on the popup when you click on this marker.
     * <p>
     * 	<b>Important:</b><br>
     * 	Html-tags in the label will not be escaped, so you can use them to style the {@link Marker}-detail.<br>
     * 	Make sure you escape all html-tags from possible user inputs to prevent possible
     * 	<a href="https://en.wikipedia.org/wiki/Cross-site_scripting">XSS-Attacks</a> on the web-client!
     * </p>
     *
     * @param detail the new detail for this {@link ObjectMarker}
     */
    void setDetail(String detail);

    interface Builder<B> {

        /**
         * Sets the detail of the {@link Marker}. The detail can include html-tags.<br>
         * This is the text that will be displayed on the popup when you click on the marker.
         * <p>
         * 	<b>Important:</b><br>
         * 	Html-tags in the label will not be escaped, so you can use them to style the {@link Marker}-detail.<br>
         * 	Make sure you escape all html-tags from possible user inputs to prevent possible
         * 	<a href="https://en.wikipedia.org/wiki/Cross-site_scripting">XSS-Attacks</a> on the web-client!
         * </p>
         *
         * @param detail the new detail for the {@link Marker}
         * @return this builder for chaining
         */
        B detail(String detail);

    }

}
