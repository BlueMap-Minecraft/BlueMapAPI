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
import de.bluecolored.bluemap.api.math.Color;
import de.bluecolored.bluemap.api.math.Shape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

@SuppressWarnings("FieldMayBeFinal")
public class ShapeMarker extends ObjectMarker {
    private static final Shape DEFAULT_SHAPE = Shape.createRect(0, 0, 1, 1);

    private Shape shape;
    private Collection<Shape> holes = new ArrayList<>();
    private float shapeY;
    private boolean depthTest = true;
    private int lineWidth = 2;
    private Color lineColor = new Color(255, 0, 0, 1f);
    private Color fillColor = new Color(200, 0, 0, 0.3f);

    /**
     * Empty constructor for deserialization.
     */
    @SuppressWarnings("unused")
    private ShapeMarker() {
        this("", DEFAULT_SHAPE, 0);
    }

    /**
     * Creates a new {@link ShapeMarker}.
     * <p><i>(The position of the marker will be the center of the shape (it's bounding box))</i></p>
     *
     * @param label the label of the marker
     * @param shape the {@link Shape} of the marker
     * @param shapeY the y-position of the shape
     *
     * @see #setLabel(String)
     * @see #setShape(Shape, float)
     */
    public ShapeMarker(String label, Shape shape, float shapeY) {
        this(label, calculateShapeCenter(Objects.requireNonNull(shape, "shape must not be null"), shapeY), shape, shapeY);
    }

    /**
     * Creates a new {@link ShapeMarker}.
     * <p><i>(Since the shape has its own positions, the position is only used to determine
     * e.g. the distance to the camera)</i></p>
     *
     * @param label the label of the marker
     * @param position the coordinates of the marker
     * @param shape the shape of the marker
     * @param shapeY the y-position of the shape
     *
     * @see #setLabel(String)
     * @see #setPosition(Vector3d)
     * @see #setShape(Shape, float)
     */
    public ShapeMarker(String label, Vector3d position, Shape shape, float shapeY) {
        super("shape", label, position);
        this.shape = Objects.requireNonNull(shape, "shape must not be null");
        this.shapeY = shapeY;
    }

    /**
     * Getter for {@link Shape} of this {@link ShapeMarker}.
     * <p>The shape is placed on the xz-plane of the map, so the y-coordinates of the {@link Shape}'s points are
     * the z-coordinates in the map.</p>
     * @return the {@link Shape}
     */
    public Shape getShape() {
        return shape;
    }

    /**
     * Getter for the height (y-coordinate) of where the shape is displayed on the map.
     * @return the height of the shape on the map
     */
    public float getShapeY() {
        return shapeY;
    }

    /**
     * Sets the {@link Shape} of this {@link ShapeMarker}.
     * <p>The shape is placed on the xz-plane of the map, so the y-coordinates of the {@link Shape}'s points will be
     * the z-coordinates in the map.</p>
     * @param shape the new {@link Shape}
     * @param y the new height (y-coordinate) of the shape on the map
     *
     * @see #centerPosition()
     */
    public void setShape(Shape shape, float y) {
        this.shape = Objects.requireNonNull(shape, "shape must not be null");
        this.shapeY = y;
    }

    /**
     * Getter for the <b>mutable</b> collection of holes in this {@link ShapeMarker}.
     * <p>Any shape in this collection will be a hole in the main {@link Shape} of this marker</p>
     * @return A <b>mutable</b> collection of hole-shapes
     */
    public Collection<Shape> getHoles() {
        return holes;
    }

    /**
     * Sets the position of this {@link ShapeMarker} to the center of the {@link Shape} (it's bounding box).
     * <p><i>(Invoke this after changing the {@link Shape} to make sure the markers position gets updated as well)</i></p>
     */
    public void centerPosition() {
        setPosition(calculateShapeCenter(shape, shapeY));
    }

    /**
     * If the depth-test is disabled, you can see the marker fully through all objects on the map. If it is enabled,
     * you'll only see the marker when it is not behind anything.
     * @return <code>true</code> if the depthTest is enabled
     */
    public boolean isDepthTestEnabled() {
        return depthTest;
    }

    /**
     * If the depth-test is disabled, you can see the marker fully through all objects on the map. If it is enabled,
     * you'll only see the marker when it is not behind anything.
     * @param enabled if the depth-test should be enabled for this {@link ShapeMarker}
     */
    public void setDepthTestEnabled(boolean enabled) {
        this.depthTest = enabled;
    }

    /**
     * Getter for the width of the border-line of this {@link ShapeMarker}.
     * @return the width of the line in pixels
     */
    public int getLineWidth() {
        return lineWidth;
    }

    /**
     * Sets the width of the border-line for this {@link ShapeMarker}.
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ShapeMarker that = (ShapeMarker) o;

        if (Float.compare(that.shapeY, shapeY) != 0) return false;
        if (depthTest != that.depthTest) return false;
        if (lineWidth != that.lineWidth) return false;
        if (!shape.equals(that.shape)) return false;
        if (!lineColor.equals(that.lineColor)) return false;
        return fillColor.equals(that.fillColor);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + shape.hashCode();
        result = 31 * result + (shapeY != 0.0f ? Float.floatToIntBits(shapeY) : 0);
        result = 31 * result + (depthTest ? 1 : 0);
        result = 31 * result + lineWidth;
        result = 31 * result + lineColor.hashCode();
        result = 31 * result + fillColor.hashCode();
        return result;
    }

    private static Vector3d calculateShapeCenter(Shape shape, float shapeY) {
        Vector2d center = shape.getMin().add(shape.getMax()).mul(0.5);
        return new Vector3d(center.getX(), shapeY, center.getY());
    }

    /**
     * Creates a Builder for {@link ShapeMarker}s.
     * @return a new Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends ObjectMarker.Builder<ShapeMarker, Builder> {

        Shape shape;
        float shapeY;
        Collection<Shape> holes = new ArrayList<>();
        Boolean depthTest;
        Integer lineWidth;
        Color lineColor;
        Color fillColor;

        /**
         * Sets the {@link Shape} of the {@link ShapeMarker}.
         * <p>The shape is placed on the xz-plane of the map, so the y-coordinates of the {@link Shape}'s points
         * will be the z-coordinates in the map.</p>
         * @param shape the new {@link Shape}
         * @param y the new height (y-coordinate) of the shape on the map
         * @return this builder for chaining
         */
        public Builder shape(Shape shape, float y) {
            this.shape = shape;
            this.shapeY = y;
            return this;
        }

        /**
         * <b>Adds</b> some hole-{@link Shape}s.
         * @param holes the additional holes
         * @return this builder for chaining
         */
        public Builder holes(Shape... holes) {
            this.holes.addAll(Arrays.asList(holes));
            return this;
        }

        /**
         * Removes all hole-shapes from this Builder.
         * @return this builder for chaining
         */
        public Builder clearHoles() {
            this.holes.clear();
            return this;
        }

        /**
         * Sets the position of the {@link ShapeMarker} to the center of the {@link Shape} (it's bounding box).
         * @return this builder for chaining
         */
        public Builder centerPosition() {
            position(null);
            return this;
        }

        /**
         * If the depth-test is disabled, you can see the marker fully through all objects on the map.
         * If it is enabled, you'll only see the marker when it is not behind anything.
         * @param enabled if the depth-test should be enabled for the {@link ShapeMarker}
         * @return this builder for chaining
         */
        public Builder depthTestEnabled(boolean enabled) {
            this.depthTest = enabled;
            return this;
        }

        /**
         * Sets the width of the border-line for the {@link ShapeMarker}.
         * @param width the new width in pixels
         * @return this builder for chaining
         */
        public Builder lineWidth(int width) {
            this.lineWidth = width;
            return this;
        }

        /**
         * Sets the {@link Color} of the border-line of the shape.
         * @param color the new line-color
         * @return this builder for chaining
         */
        public Builder lineColor(Color color) {
            this.lineColor = color;
            return this;
        }

        /**
         * Sets the fill-{@link Color} of the shape.
         * @param color the new fill-color
         * @return this builder for chaining
         */
        public Builder fillColor(Color color) {
            this.fillColor = color;
            return this;
        }

        /**
         * Creates a new {@link ShapeMarker} with the current builder-settings.<br>
         * The minimum required settings to build this marker are:
         * <ul>
         *  <li>{@link #label(String)}</li>
         *  <li>{@link #shape(Shape, float)}</li>
         * </ul>
         * @return The new {@link ShapeMarker}-instance
         */
        public ShapeMarker build() {
            ShapeMarker marker = new ShapeMarker(
                    checkNotNull(label, "label"),
                    checkNotNull(shape, "shape"),
                    shapeY
            );
            marker.getHoles().addAll(holes);
            if (depthTest != null) marker.setDepthTestEnabled(depthTest);
            if (lineWidth != null) marker.setLineWidth(lineWidth);
            if (lineColor != null) marker.setLineColor(lineColor);
            if (fillColor != null) marker.setFillColor(fillColor);
            return build(marker);
        }

    }

}
