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
package de.bluecolored.bluemap.api.math;

import com.flowpowered.math.vector.Vector3d;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * A line consisting of 2 or more {@link Vector3d}-points.
 */
public class Line {

    private final Vector3d[] points;

    @Nullable
    private Vector3d min = null, max = null;

    public Line(Vector3d... points) {
        if (points.length < 2) throw new IllegalArgumentException("A line has to have at least 2 points!");
        this.points = points;
    }

    public Line(Collection<Vector3d> points) {
        this(points.toArray(Vector3d[]::new));
    }

    /**
     * Getter for the amount of points in this line.
     * @return the amount of points
     */
    public int getPointCount() {
        return points.length;
    }

    /**
     * Getter for the point at the given index.
     * @param i the index
     * @return the point at the given index
     */
    public Vector3d getPoint(int i) {
        return points[i];
    }

    /**
     * Getter for <b>a copy</b> of the points array.<br>
     * <i>(A line is immutable once created)</i>
     * @return the points of this line
     */
    public Vector3d[] getPoints() {
        return Arrays.copyOf(points, points.length);
    }

    /**
     * Calculates and returns the minimum corner of the axis-aligned-bounding-box of this line.
     * @return the min of the AABB of this line
     */
    public Vector3d getMin() {
        if (this.min == null) {
            Vector3d min = points[0];
            for (int i = 1; i < points.length; i++) {
                min = min.min(points[i]);
            }
            this.min = min;
        }
        return this.min;
    }

    /**
     * Calculates and returns the maximum corner of the axis-aligned-bounding-box of this line.
     * @return the max of the AABB of this line
     */
    public Vector3d getMax() {
        if (this.max == null) {
            Vector3d max = points[0];
            for (int i = 1; i < points.length; i++) {
                max = max.max(points[i]);
            }
            this.max = max;
        }
        return this.max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Line line = (Line) o;

        return Arrays.equals(points, line.points);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(points);
    }

    /**
     * Creates a builder to build {@link Line}s.
     * @return a new builder
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        List<Vector3d> points;

        public Builder() {
            this.points = new ArrayList<>();
        }

        /**
         * Adds a point to the end of line.
         * @param point the point to be added.
         * @return this builder for chaining
         */
        public Builder addPoint(Vector3d point) {
            this.points.add(point);
            return this;
        }

        /**
         * Adds multiple points to the end of line.
         * @param points the points to be added.
         * @return this builder for chaining
         */
        public Builder addPoints(Vector3d... points) {
            this.points.addAll(Arrays.asList(points));
            return this;
        }

        /**
         * Adds multiple points to the end of line.
         * @param points the points to be added.
         * @return this builder for chaining
         */
        public Builder addPoints(Collection<Vector3d> points) {
            this.points.addAll(points);
            return this;
        }

        /**
         * Builds a new {@link Line} with the points set in this builder.<br>
         * There need to be at least 2 points to build a {@link Line}.
         * @return the new {@link Line}
         * @throws IllegalStateException if there are less than 2 points added to this builder
         */
        public Line build() {
            if (points.size() < 2) throw new IllegalStateException("A line has to have at least 2 points!");
            return new Line(points);
        }

    }

}
