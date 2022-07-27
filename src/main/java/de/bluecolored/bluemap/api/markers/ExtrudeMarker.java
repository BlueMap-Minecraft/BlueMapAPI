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

import com.flowpowered.math.vector.Vector2d;
import com.flowpowered.math.vector.Vector3d;
import de.bluecolored.bluemap.api.debug.DebugDump;
import de.bluecolored.bluemap.api.math.Color;
import de.bluecolored.bluemap.api.math.Shape;

import java.util.Objects;

@DebugDump
public class ExtrudeMarker extends ObjectMarker {
    private static final Shape DEFAULT_SHAPE = Shape.createRect(0, 0, 1, 1);

    private Shape shape;
    private float shapeMinY, shapeMaxY;
    private boolean depthTest = true;
    private int lineWidth = 2;
    private Color lineColor = new Color(255, 0, 0, 1f);
    private Color fillColor = new Color(200, 0, 0, 0.3f);

    /**
     * Empty constructor for deserialization.
     */
    @SuppressWarnings("unused")
    private ExtrudeMarker() {
        this("", DEFAULT_SHAPE, 0, 0);
    }

    /**
     * Creates a new {@link ExtrudeMarker}.
     * <p><i>(The position of the marker will be the center of the shape (it's bounding box))</i></p>
     *
     * @param label the label of the marker
     * @param shape the {@link Shape} of the marker
     * @param shapeMinY the minimum y-position of the extruded shape
     * @param shapeMaxY the maximum y-position of the extruded shape
     *
     * @see #setLabel(String)
     * @see #setShape(Shape, float, float)
     */
    public ExtrudeMarker(String label, Shape shape, float shapeMinY, float shapeMaxY) {
        this(label, calculateShapeCenter(Objects.requireNonNull(shape, "shape must not be null"), shapeMinY, shapeMaxY), shape, shapeMinY, shapeMaxY);
    }

    /**
     * Creates a new {@link ExtrudeMarker}.
     * <p><i>(Since the shape has its own positions, the position is only used to determine e.g. the distance to the camera)</i></p>
     *
     * @param label the label of the marker
     * @param position the coordinates of the marker
     * @param shape the shape of the marker
     * @param shapeMinY the minimum y-position of the extruded shape
     * @param shapeMaxY the maximum y-position of the extruded shape
     *
     * @see #setLabel(String)
     * @see #setPosition(Vector3d)
     * @see #setShape(Shape, float, float)
     */
    public ExtrudeMarker(String label, Vector3d position, Shape shape, float shapeMinY, float shapeMaxY) {
        super("extrude", label, position);
        this.shape = Objects.requireNonNull(shape, "shape must not be null");
        this.shapeMinY = shapeMinY;
        this.shapeMaxY = shapeMaxY;
    }

    /**
     * Getter for {@link Shape} of this {@link ExtrudeMarker}.
     * <p>The shape is placed on the xz-plane of the map, so the y-coordinates of the {@link Shape}'s points are the z-coordinates in the map.</p>
     * @return the {@link Shape}
     */
    public Shape getShape() {
        return shape;
    }

    /**
     * Getter for the minimum height (y-coordinate) of where the shape is displayed on the map.<br>
     * <i>(The shape will be extruded from this value to {@link #getShapeMaxY()} on the map)</i>
     * @return the min-height of the shape on the map
     */
    public float getShapeMinY() {
        return shapeMinY;
    }

    /**
     * Getter for the maximum height (y-coordinate) of where the shape is displayed on the map.
     * <i>(The shape will be extruded from {@link #getShapeMinY()} to this value on the map)</i>
     * @return the max-height of the shape on the map
     */
    public float getShapeMaxY() {
        return shapeMaxY;
    }

    /**
     * Sets the {@link Shape} of this {@link ExtrudeMarker}.
     * <p>The shape is placed on the xz-plane of the map, so the y-coordinates of the {@link Shape}'s points will be the z-coordinates in the map.</p>
     * <i>(The shape will be extruded from minY to maxY on the map)</i>
     * @param shape the new {@link Shape}
     * @param minY the new min-height (y-coordinate) of the shape on the map
     * @param maxY the new max-height (y-coordinate) of the shape on the map
     *
     * @see #centerPosition()
     */
    public void setShape(Shape shape, float minY, float maxY) {
        this.shape = Objects.requireNonNull(shape, "shape must not be null");
        this.shapeMinY = minY;
        this.shapeMaxY = maxY;
    }

    /**
     * Sets the position of this {@link ExtrudeMarker} to the center of the {@link Shape} (it's bounding box).
     * <p><i>(Invoke this after changing the {@link Shape} to make sure the markers position gets updated as well)</i></p>
     */
    public void centerPosition() {
        setPosition(calculateShapeCenter(shape, shapeMinY, shapeMaxY));
    }

    /**
     * If the depth-test is disabled, you can see the marker fully through all objects on the map. If it is enabled, you'll only see the marker when it is not behind anything.
     * @return <code>true</code> if the depthTest is enabled
     */
    public boolean isDepthTestEnabled() {
        return depthTest;
    }

    /**
     * If the depth-test is disabled, you can see the marker fully through all objects on the map. If it is enabled, you'll only see the marker when it is not behind anything.
     * @param enabled if the depth-test should be enabled for this {@link ExtrudeMarker}
     */
    public void setDepthTestEnabled(boolean enabled) {
        this.depthTest = enabled;
    }

    /**
     * Getter for the width of the lines of this {@link ExtrudeMarker}.
     * @return the width of the lines in pixels
     */
    public int getLineWidth() {
        return lineWidth;
    }

    /**
     * Sets the width of the lines for this {@link ExtrudeMarker}.
     * @param width the new width in pixels
     */
    public void setLineWidth(int width) {
        this.lineWidth = width;
    }

    /**
     * Getter for the {@link Color} of the border-line of the shape.
     * @return the line-color
     */
    public Color getLineColor() {
        return lineColor;
    }

    /**
     * Sets the {@link Color} of the border-line of the shape.
     * @param color the new line-color
     */
    public void setLineColor(Color color) {
        this.lineColor = Objects.requireNonNull(color, "color must not be null");
    }

    /**
     * Getter for the fill-{@link Color} of the shape.
     * @return the fill-color
     */
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * Sets the fill-{@link Color} of the shape.
     * @param color the new fill-color
     */
    public void setFillColor(Color color) {
        this.fillColor = Objects.requireNonNull(color, "color must not be null");
    }

    /**
     * Sets the border- and fill- color.
     * @param lineColor the new border-color
     * @param fillColor the new fill-color
     * @see #setLineColor(Color)
     * @see #setFillColor(Color)
     */
    public void setColors(Color lineColor, Color fillColor) {
        setLineColor(lineColor);
        setFillColor(fillColor);
    }

    private static Vector3d calculateShapeCenter(Shape shape, float shapeMinY, float shapeMaxY) {
        Vector2d center = shape.getMin().add(shape.getMax()).mul(0.5);
        float centerY = (shapeMinY + shapeMaxY) * 0.5f;
        return new Vector3d(center.getX(), centerY, center.getY());
    }

}
