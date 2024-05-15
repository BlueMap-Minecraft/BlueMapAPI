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

/**
 * @see HtmlMarker
 * @see POIMarker
 * @see ShapeMarker
 * @see ExtrudeMarker
 * @see LineMarker
 */
public abstract class DistanceRangedMarker extends Marker {

    private double minDistance, maxDistance;

    public DistanceRangedMarker(String type, String label, Vector3d position) {
        super(type, label, position);
        this.minDistance = 0.0;
        this.maxDistance = 10000000.0;
    }

    /**
     * Getter for the minimum distance of the camera to the position for this {@link Marker} to be displayed.<br>
     * If the camera is closer to this {@link Marker} than this distance, it will be hidden!
     *
     * @return the minimum distance for this {@link Marker} to be displayed
     */
    public double getMinDistance() {
        return minDistance;
    }

    /**
     * Sets the minimum distance of the camera to the position of the {@link Marker} for it to be displayed.<br>
     * If the camera is closer to this {@link Marker} than this distance, it will be hidden!
     *
     * @param minDistance the new minimum distance
     */
    public void setMinDistance(double minDistance) {
        this.minDistance = minDistance;
    }

    /**
     * Getter for the maximum distance of the camera to the position of the {@link Marker} for it to be displayed.<br>
     * If the camera is further to this {@link Marker} than this distance, it will be hidden!
     *
     * @return the maximum distance for this {@link Marker} to be displayed
     */
    public double getMaxDistance() {
        return maxDistance;
    }

    /**
     * Sets the maximum distance of the camera to the position of the {@link Marker} for it to be displayed.<br>
     * If the camera is further to this {@link Marker} than this distance, it will be hidden!
     *
     * @param maxDistance the new maximum distance
     */
    public void setMaxDistance(double maxDistance) {
        this.maxDistance = maxDistance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        DistanceRangedMarker that = (DistanceRangedMarker) o;

        if (Double.compare(that.minDistance, minDistance) != 0) return false;
        return Double.compare(that.maxDistance, maxDistance) == 0;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(minDistance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(maxDistance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public static abstract class Builder<T extends DistanceRangedMarker, B extends DistanceRangedMarker.Builder<T, B>>
            extends Marker.Builder<T, B> {

        Double minDistance, maxDistance;

        /**
         * Sets the minimum distance of the camera to the position of the {@link Marker} for it to be displayed.<br>
         * If the camera is closer to this {@link Marker} than this distance, it will be hidden!
         *
         * @param minDistance the new minimum distance
         * @return this builder for chaining
         */
        public B minDistance(double minDistance) {
            this.minDistance = minDistance;
            return self();
        }

        /**
         * Sets the maximum distance of the camera to the position of the {@link Marker} for it to be displayed.<br>
         * If the camera is further to this {@link Marker} than this distance, it will be hidden!
         *
         * @param maxDistance the new maximum distance
         * @return this builder for chaining
         */
        public B maxDistance(double maxDistance) {
            this.maxDistance = maxDistance;
            return self();
        }

        @Override
        T build(T marker) {
            if (minDistance != null) marker.setMinDistance(minDistance);
            if (maxDistance != null) marker.setMaxDistance(maxDistance);
            return super.build(marker);
        }

    }

}
