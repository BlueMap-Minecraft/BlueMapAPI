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

import com.flowpowered.math.vector.Vector2d;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * A shape consisting of 3 or more {@link Vector2d}-points on a plane.
 */
public class Shape {

    private final Vector2d[] points;

    @Nullable
    private Vector2d min = null, max = null;

    public Shape(Vector2d... points) {
        if (points.length < 3) throw new IllegalArgumentException("A shape has to have at least 3 points!");
        this.points = points;
    }

    public Shape(Collection<Vector2d> points) {
        this(points.toArray(Vector2d[]::new));
    }

    /**
     * Getter for the amount of points in this shape.
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
    public Vector2d getPoint(int i) {
        return points[i];
    }

    /**
     * Getter for <b>a copy</b> of the points array.<br>
     * <i>(A shape is immutable once created)</i>
     * @return the points of this shape
     */
    public Vector2d[] getPoints() {
        return Arrays.copyOf(points, points.length);
    }

    /**
     * Calculates and returns the minimum corner of the axis-aligned-bounding-box of this shape.
     * @return the min of the AABB of this shape
     */
    public Vector2d getMin() {
        if (this.min == null) {
            Vector2d min = points[0];
            for (int i = 1; i < points.length; i++) {
                min = min.min(points[i]);
            }
            this.min = min;
        }
        return this.min;
    }

    /**
     * Calculates and returns the maximum corner of the axis-aligned-bounding-box of this shape.
     * @return the max of the AABB of this shape
     */
    public Vector2d getMax() {
        if (this.max == null) {
            Vector2d max = points[0];
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

        Shape shape = (Shape) o;

        return Arrays.equals(points, shape.points);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(points);
    }

    /**
     * Creates a {@link Shape} representing a rectangle spanning over pos1 and pos2
     * @param pos1 one corner of the rectangle
     * @param pos2 the opposite corner of the rectangle
     * @return the created {@link Shape}
     */
    public static Shape createRect(Vector2d pos1, Vector2d pos2) {
        Vector2d min = pos1.min(pos2);
        Vector2d max = pos1.max(pos2);

        return new Shape(
                min,
                new Vector2d(max.getX(), min.getY()),
                max,
                new Vector2d(min.getX(), max.getY())
            );
    }

    /**
     * Creates a {@link Shape} representing a rectangle spanning over two points
     * @param x1 x position of one corner of the rectangle
     * @param y1 y position of one corner of the rectangle
     * @param x2 x position of the opposite corner of the rectangle
     * @param y2 y position of the opposite corner of the rectangle
     * @return the created {@link Shape}
     */
    public static Shape createRect(double x1, double y1, double x2, double y2) {
        return createRect(new Vector2d(x1, y1), new Vector2d(x2, y2));
    }

    /**
     * Creates a {@link Shape} representing an ellipse.
     * @param centerPos the center of the ellipse
     * @param radiusX the x radius of the ellipse
     * @param radiusY the y radius of the ellipse
     * @param points the amount of points used to create the ellipse (at least 3)
     * @return the created {@link Shape}
     */
    public static Shape createEllipse(Vector2d centerPos, double radiusX, double radiusY, int points) {
        if (points < 3) throw new IllegalArgumentException("A shape has to have at least 3 points!");

        Vector2d[] pointArray = new Vector2d[points];
        double segmentAngle = 2 * Math.PI / points;
        double angle = 0d;
        for (int i = 0; i < points; i++) {
            pointArray[i] = centerPos.add(Math.sin(angle) * radiusX, Math.cos(angle) * radiusY);
            angle += segmentAngle;
        }

        return new Shape(pointArray);
    }

    /**
     * Creates a {@link Shape} representing an ellipse.
     * @param centerX the x-position of the center of the ellipse
     * @param centerY the y-position of the center of the ellipse
     * @param radiusX the x radius of the ellipse
     * @param radiusY the y radius of the ellipse
     * @param points the amount of points used to create the ellipse (at least 3)
     * @return the created {@link Shape}
     */
    public static Shape createEllipse(double centerX, double centerY, double radiusX, double radiusY, int points) {
        return createEllipse(new Vector2d(centerX, centerY), radiusX, radiusY, points);
    }

    /**
     * Creates a {@link Shape} representing a circle.
     * @param centerPos the center of the circle
     * @param radius the radius of the circle
     * @param points the amount of points used to create the circle (at least 3)
     * @return the created {@link Shape}
     */
    public static Shape createCircle(Vector2d centerPos, double radius, int points) {
        return createEllipse(centerPos, radius, radius, points);
    }

    /**
     * Creates a {@link Shape} representing a circle.
     * @param centerX the x-position of the center of the circle
     * @param centerY the y-position of the center of the circle
     * @param radius the radius of the circle
     * @param points the amount of points used to create the circle (at least 3)
     * @return the created {@link Shape}
     */
    public static Shape createCircle(double centerX, double centerY, double radius, int points) {
        return createCircle(new Vector2d(centerX, centerY), radius, points);
    }

    /**
     * Creates a builder to build {@link Shape}s.
     * @return a new builder
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        List<Vector2d> points;

        public Builder() {
            this.points = new ArrayList<>();
        }

        /**
         * Adds a point to the end of line.
         * @param point the point to be added.
         * @return this builder for chaining
         */
        public Builder addPoint(Vector2d point) {
            this.points.add(point);
            return this;
        }

        /**
         * Adds multiple points to the end of line.
         * @param points the points to be added.
         * @return this builder for chaining
         */
        public Builder addPoints(Vector2d... points) {
            this.points.addAll(Arrays.asList(points));
            return this;
        }

        /**
         * Adds multiple points to the end of line.
         * @param points the points to be added.
         * @return this builder for chaining
         */
        public Builder addPoints(Collection<Vector2d> points) {
            this.points.addAll(points);
            return this;
        }

        /**
         * Builds a new {@link Shape} with the points set in this builder.<br>
         * There need to be at least 3 points to build a {@link Shape}.
         * @return the new {@link Shape}
         * @throws IllegalStateException if there are less than 3 points added to this builder
         */
        public Shape build() {
            if (points.size() < 3) throw new IllegalStateException("A shape has to have at least 3 points!");
            return new Shape(points);
        }

    }

}
