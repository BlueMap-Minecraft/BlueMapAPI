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

import java.awt.Color;

public interface ShapeMarker extends ObjectMarker, DistanceRangedMarker {

    /**
     * Getter for {@link Shape} of this {@link ShapeMarker}.
     * <p>The shape is placed on the xz-plane of the map, so the y-coordinates of the {@link Shape}'s points are the z-coordinates in the map.</p>
     * @return the {@link Shape}
     */
    Shape getShape();

    /**
     * Getter for the height (y-coordinate) of where the shape is displayed on the map.
     * @return the height of the shape on the map
     * @deprecated Use {@link #getShapeY()} instead
     */
    default float getHeight() {
        return getShapeY();
    }

    /**
     * Getter for the height (y-coordinate) of where the shape is displayed on the map.
     * @return the height of the shape on the map
     */
    float getShapeY();

    /**
     * Sets the {@link Shape} of this {@link ShapeMarker}.
     * <p>The shape is placed on the xz-plane of the map, so the y-coordinates of the {@link Shape}'s points will be the z-coordinates in the map.</p>
     * @param shape the new {@link Shape}
     * @param y the new height (y-coordinate) of the shape on the map
     */
    void setShape(Shape shape, float y);

    /**
     * If the depth-test is disabled, you can see the marker fully through all objects on the map. If it is enabled, you'll only see the marker when it is not behind anything.
     * @return <code>true</code> if the depthTest is enabled
     */
    boolean isDepthTestEnabled();

    /**
     * If the depth-test is disabled, you can see the marker fully through all objects on the map. If it is enabled, you'll only see the marker when it is not behind anything.
     * @param enabled if the depth-test should be enabled for this {@link ShapeMarker}
     */
    void setDepthTestEnabled(boolean enabled);

    /**
     * Getter for the width of the border-line of this {@link ShapeMarker}.
     * @return the width of the line in pixels
     */
    int getLineWidth();

    /**
     * Sets the width of the border-line for this {@link ShapeMarker}.
     * @param width the new width in pixels
     */
    void setLineWidth(int width);

    /**
     * Getter for the {@link Color} of the border of the shape.
     * @return the border-color
     * @deprecated Use {@link #getLineColor()} instead
     */
    default Color getBorderColor() {
        return getLineColor();
    }

    /**
     * Sets the {@link Color} of the border of the shape.
     * @param color the new border-color
     * @deprecated Use {@link #setLineColor(Color)} instead
     */
    default void setBorderColor(Color color){
        setLineColor(color);
    }

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

    /**
     * Getter for the fill-{@link Color} of the shape.
     * @return the fill-color
     */
    Color getFillColor();

    /**
     * Sets the fill-{@link Color} of the shape.
     * @param color the new fill-color
     */
    void setFillColor(Color color);

    /**
     * Sets the border- and fill- color.
     * @param lineColor the new border-color
     * @param fillColor the new fill-color
     * @see #setLineColor(Color)
     * @see #setFillColor(Color)
     */
    default void setColors(Color lineColor, Color fillColor) {
        setLineColor(lineColor);
        setFillColor(fillColor);
    }

}
