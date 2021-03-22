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

public interface DistanceRangedMarker extends Marker {

	/**
	 * Getter for the minimum distance of the camera to the position for this {@link Marker} to be displayed.<br>
	 * If the camera is closer to this {@link Marker} than this distance, it will be hidden!
	 *
	 * @return the minimum distance for this {@link Marker} to be displayed
	 */
	double getMinDistance();

	/**
	 * Sets the minimum distance of the camera to the position of the {@link Marker} for it to be displayed.<br>
	 * If the camera is closer to this {@link Marker} than this distance, it will be hidden!
	 *
	 * @param minDistance the new minimum distance
	 */
	void setMinDistance(double minDistance);

	/**
	 * Getter for the maximum distance of the camera to the position of the {@link Marker} for it to be displayed.<br>
	 * If the camera is further to this {@link Marker} than this distance, it will be hidden!
	 *
	 * @return the maximum distance for this {@link Marker} to be displayed
	 */
	double getMaxDistance();

	/**
	 * Sets the maximum distance of the camera to the position of the {@link Marker} for it to be displayed.<br>
	 * If the camera is further to this {@link Marker} than this distance, it will be hidden!
	 *
	 * @param maxDistance the new maximum distance
	 */
	void setMaxDistance(double maxDistance);

}
