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

import java.awt.*;

public interface LineMarker extends ObjectMarker, DistanceRangedMarker {

    /**
     * Getter for {@link Line} of this {@link LineMarker}.
     * @return the {@link Line}
     */
    Line getLine();

    /**
     * Sets the {@link Line} of this {@link LineMarker}.
     * @param line the new {@link Line}
     */
    void setLine(Line line);

    /**
     * If the depth-test is disabled, you can see the marker fully through all objects on the map. If it is enabled, you'll only see the marker when it is not behind anything.
     * @return <code>true</code> if the depthTest is enabled
     */
    boolean isDepthTestEnabled();

    /**
     * If the depth-test is disabled, you can see the marker fully through all objects on the map. If it is enabled, you'll only see the marker when it is not behind anything.
     * @param enabled if the depth-test should be enabled for this {@link LineMarker}
     */
    void setDepthTestEnabled(boolean enabled);

    /**
     * Getter for the width of the lines of this {@link LineMarker}.
     * @return the width of the lines in pixels
     */
    int getLineWidth();

    /**
     * Sets the width of the lines for this {@link LineMarker}.
     * @param width the new width in pixels
     */
    void setLineWidth(int width);

    /**
     * Getter for the {@link Color} of the border-line of the shape.
     * @return the line-color
     */
    Color getLineColor();

    /**
     * Sets the {@link Color} of the border-line of the shape.
     * @param color the new line-color
     */
    void setLineColor(Color color);

}
