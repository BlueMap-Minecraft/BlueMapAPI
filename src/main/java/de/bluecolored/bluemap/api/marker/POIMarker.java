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

import com.flowpowered.math.vector.Vector2i;

import de.bluecolored.bluemap.api.BlueMapAPI;

public interface POIMarker extends Marker {

	/**
	 * Getter for the relative address of the icon used to display this {@link POIMarker}
	 * @return the relative web-address of the icon
	 */
	String getIconAddress();
	
	/**
	 * Getter for the position (in pixels) where the icon is anchored to the map.
	 * @return the anchor-position in pixels
	 */
	Vector2i getIconAnchor();

	/**
	 * Sets the icon for this {@link POIMarker}.
	 * @param iconAddress the web-address of the icon-image. Can be an absolute or relative. You can also use an address returned by {@link BlueMapAPI#createImage(java.awt.image.BufferedImage, String)}.
	 * @param anchorX the x-position of the position (in pixels) where the icon is anchored to the map
	 * @param anchorY the y-position of the position (in pixels) where the icon is anchored to the map
	 */
	default void setIcon(String iconAddress, int anchorX, int anchorY) {
		setIcon(iconAddress, new Vector2i(anchorX, anchorY));
	}
	
	/**
	 * Sets the icon for this {@link POIMarker}.
	 * @param iconAddress the web-address of the icon-image. Can be an absolute or relative. You can also use an address returned by {@link BlueMapAPI#createImage(java.awt.image.BufferedImage, String)}.
	 * @param anchor the position of the position (in pixels) where the icon is anchored to the map
	 */
	void setIcon(String iconAddress, Vector2i anchor);
	
}
