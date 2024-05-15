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

import com.flowpowered.math.vector.Vector3d;
import de.bluecolored.bluemap.api.math.Color;
import de.bluecolored.bluemap.api.math.Line;

import java.util.Objects;

public class LineMarker extends ObjectMarker {
    private static final Line DEFAULT_LINE = new Line(Vector3d.ZERO, Vector3d.ONE);

    private Line line;
    private boolean depthTest = true;
    private int lineWidth = 2;
    private Color lineColor = new Color(255, 0, 0, 1f);

    /**
     * Empty constructor for deserialization.
     */
    @SuppressWarnings("unused")
    private LineMarker() {
        this("", DEFAULT_LINE);
    }

    /**
     * Creates a new {@link LineMarker}.
     * <p><i>(The position of the marker will be the center of the line (it's bounding box))</i></p>
     *
     * @param label the label of the marker
     * @param line the {@link Line} of the marker
     *
     * @see #setLabel(String)
     * @see #setLine(Line)
     */
    public LineMarker(String label, Line line) {
        this(label, calculateLineCenter(Objects.requireNonNull(line, "line must not be null")), line);
    }

    /**
     * Creates a new {@link LineMarker}.
     * <p><i>(Since the line has its own positions, the position is only used to determine
     * e.g. the distance to the camera)</i></p>
     *
     * @param label the label of the marker
     * @param position the coordinates of the marker
     * @param line the {@link Line} of the marker
     *
     * @see #setLabel(String)
     * @see #setPosition(Vector3d)
     * @see #setLine(Line)
     */
    public LineMarker(String label, Vector3d position, Line line) {
        super("line", label, position);
        this.line = Objects.requireNonNull(line, "line must not be null");
    }

    /**
     * Getter for {@link Line} of this {@link LineMarker}.
     * @return the {@link Line}
     */
    public Line getLine() {
        return line;
    }

    /**
     * Sets the {@link Line} of this {@link LineMarker}.
     * @param line the new {@link Line}
     *
     * @see #centerPosition()
     */
    public void setLine(Line line) {
        this.line = Objects.requireNonNull(line, "line must not be null");
    }

    /**
     * Sets the position of this {@link LineMarker} to the center of the {@link Line} (it's bounding box).
     * <p><i>(Invoke this after changing the {@link Line} to make sure the markers position gets updated as well)</i></p>
     */
    public void centerPosition() {
        setPosition(calculateLineCenter(line));
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
     * @param enabled if the depth-test should be enabled for this {@link LineMarker}
     */
    public void setDepthTestEnabled(boolean enabled) {
        this.depthTest = enabled;
    }

    /**
     * Getter for the width of the lines of this {@link LineMarker}.
     * @return the width of the lines in pixels
     */
    public int getLineWidth() {
        return lineWidth;
    }

    /**
     * Sets the width of the lines for this {@link LineMarker}.
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        LineMarker that = (LineMarker) o;

        if (depthTest != that.depthTest) return false;
        if (lineWidth != that.lineWidth) return false;
        if (!line.equals(that.line)) return false;
        return lineColor.equals(that.lineColor);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + line.hashCode();
        result = 31 * result + (depthTest ? 1 : 0);
        result = 31 * result + lineWidth;
        result = 31 * result + lineColor.hashCode();
        return result;
    }

    private static Vector3d calculateLineCenter(Line line) {
        return line.getMin().add(line.getMax()).mul(0.5);
    }

    /**
     * Creates a Builder for {@link LineMarker}s.
     * @return a new Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends ObjectMarker.Builder<LineMarker, Builder> {

        Line line;
        Boolean depthTest;
        Integer lineWidth;
        Color lineColor;

        /**
         * Sets the {@link Line} of the {@link LineMarker}.
         * @param line the new {@link Line}
         * @return this builder for chaining
         */
        public Builder line(Line line) {
            this.line = line;
            return this;
        }

        /**
         * Sets the position of the {@link LineMarker} to the center of the {@link Line} (it's bounding box).
         * @return this builder for chaining
         */
        public Builder centerPosition() {
            position(null);
            return this;
        }

        /**
         * If the depth-test is disabled, you can see the marker fully through all objects on the map.
         * If it is enabled, you'll only see the marker when it is not behind anything.
         * @param enabled if the depth-test should be enabled for the {@link LineMarker}
         * @return this builder for chaining
         */
        public Builder depthTestEnabled(boolean enabled) {
            this.depthTest = enabled;
            return this;
        }

        /**
         * Sets the width of the lines for this {@link LineMarker}.
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
         */
        public Builder lineColor(Color color) {
            this.lineColor = color;
            return this;
        }

        /**
         * Creates a new {@link LineMarker} with the current builder-settings.<br>
         * The minimum required settings to build this marker are:
         * <ul>
         *  <li>{@link #label(String)}</li>
         *  <li>{@link #line(Line)}</li>
         * </ul>
         * @return The new {@link LineMarker}-instance
         */
        public LineMarker build() {
            LineMarker marker = new LineMarker(
                    checkNotNull(label, "label"),
                    checkNotNull(line, "line")
            );
            if (depthTest != null) marker.setDepthTestEnabled(depthTest);
            if (lineWidth != null) marker.setLineWidth(lineWidth);
            if (lineColor != null) marker.setLineColor(lineColor);
            return build(marker);
        }

    }

}
