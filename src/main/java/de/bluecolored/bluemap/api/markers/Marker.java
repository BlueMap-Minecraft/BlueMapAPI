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
import de.bluecolored.bluemap.api.debug.DebugDump;

import java.util.Objects;

/**
 * The Base-Class for all markers that can be displayed in the web-app.
 *
 * @see HtmlMarker
 * @see POIMarker
 * @see ShapeMarker
 * @see ExtrudeMarker
 * @see LineMarker
 */
@DebugDump
public abstract class Marker {

    private final String type;
    private String label;
    private Vector3d position;

    public Marker(String type, String label, Vector3d position) {
        this.type = Objects.requireNonNull(type, "type cannot be null");
        this.label = Objects.requireNonNull(label, "label cannot be null");
        this.position = Objects.requireNonNull(position, "position cannot be null");
    }

    /**
     * Returns the type of the marker.
     * @return the type-id of the marker.
     */
    public String getType() {
        return type;
    }

    /**
     * Getter for the label of this marker.
     * @return the label of this {@link Marker}
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label of this {@link Marker}.
     * <p><i>(HTML-Tags will be escaped.)</i></p>
     *
     * @param label the new label for this {@link Marker}
     */
    public void setLabel(String label) {
        //escape html-tags
        this.label = Objects.requireNonNull(label, "label cannot be null")
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    /**
     * Getter for the position of where this {@link Marker} lives on the map.
     * @return the position of this {@link Marker}
     */
    public Vector3d getPosition() {
        return position;
    }

    /**
     * Sets the position of where this {@link Marker} lives on the map.
     * @param position the new position
     */
    public void setPosition(Vector3d position) {
        this.position = Objects.requireNonNull(position, "position cannot be null");
    }

    /**
     * Sets the position of where this {@link Marker} lives on the map.
     * @param x the x-coordinate of the new position
     * @param y the y-coordinate of the new position
     * @param z the z-coordinate of the new position
     */
    public void setPosition(double x, double y, double z) {
        setPosition(new Vector3d(x, y, z));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Marker marker = (Marker) o;

        if (!type.equals(marker.type)) return false;
        if (!label.equals(marker.label)) return false;
        return position.equals(marker.position);
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + label.hashCode();
        result = 31 * result + position.hashCode();
        return result;
    }

    public static abstract class Builder<T extends Marker, B extends Marker.Builder<T, B>> {

        String label;
        Vector3d position;

        /**
         * Sets the label of the {@link Marker}.
         * <p><i>(HTML-Tags will be escaped.)</i></p>
         * @param label the new label for the {@link Marker}
         * @return this builder for chaining
         */
        public B label(String label) {
            this.label = label;
            return self();
        }

        /**
         * Sets the position of where the {@link Marker} lives on the map.
         * @param position the new position
         * @return this builder for chaining
         */
        public B position(Vector3d position) {
            this.position = position;
            return self();
        }

        /**
         * Sets the position of where the {@link Marker} lives on the map.
         * @param x the x-coordinate of the new position
         * @param y the y-coordinate of the new position
         * @param z the z-coordinate of the new position
         * @return this builder for chaining
         */
        public B position(double x, double y, double z) {
            return position(new Vector3d(x, y, z));
        }

        /**
         * Creates a new {@link Marker} with the current builder-settings
         * @return The new {@link Marker}-instance
         */
        public abstract T build();

        T build(T marker) {
            if (label != null) marker.setLabel(label);
            if (position != null) marker.setPosition(position);
            return marker;
        }

        @SuppressWarnings("unchecked")
        B self() {
            return (B) this;
        }

        <O> O checkNotNull(O object, String name) {
            if (object == null) throw new IllegalStateException(name + " has to be set and cannot be null");
            return object;
        }

        // -----

        /**
         * @deprecated use {@link #position(double, double, double)} instead
         */
        @Deprecated(forRemoval = true)
        public B position(int x, int y, int z) {
            return position(new Vector3d(x, y, z));
        }

    }

    // -----

    /**
     * @deprecated use {@link #setPosition(double, double, double)} instead
     */
    @Deprecated(forRemoval = true)
    public void setPosition(int x, int y, int z) {
        setPosition(new Vector3d(x, y, z));
    }

}
