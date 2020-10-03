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

import java.util.Optional;

import com.flowpowered.math.vector.Vector3d;

import de.bluecolored.bluemap.api.BlueMapMap;

/**
 * A marker that is displayed on one of the maps in the web-app.
 * <p>Each marker has an id that is unique in the {@link MarkerSet} that it is in.</p> 
 */
public interface Marker {

	/**
	 * Getter for the id of this {@link Marker}.
	 * <p>The id is unique in the {@link MarkerSet} that this marker is in.</p>
	 * @return the id of this {@link Marker}
	 */
	String getId();
	
	/**
	 * Getter for the {@link BlueMapMap} this {@link Marker} lives in.
	 * @return the {@link BlueMapMap} this {@link Marker} lives in
	 */
	BlueMapMap getMap();
	
	/**
	 * Sets the {@link BlueMapMap} this {@link Marker} lives in
	 * @param map the new {@link BlueMapMap}
	 */
	void setMap(BlueMapMap map);
	
	/**
	 * Getter for the position of where this {@link Marker} lives on the map. 
	 * @return the position of this {@link Marker}
	 */
	Vector3d getPosition();
	
	/**
	 * Sets the position of where this {@link Marker} lives on the map. 
	 * @param position the new position
	 */
	void setPosition(Vector3d position);
	
	/**
	 * Getter for the minimum distance of the camera to the position ({@link #getPosition()} of the {@link Marker} for it to be displayed.<br>
	 * If the camera is closer to this {@link Marker} than this distance, it will be hidden!
	 *  
	 * @return the minimum distance for this {@link Marker} to be displayed
	 */
	double getMinDistance();
	
	/**
	 * Sets the minimum distance of the camera to the position ({@link #getPosition()} of the {@link Marker} for it to be displayed.<br>
	 * If the camera is closer to this {@link Marker} than this distance, it will be hidden!
	 * 
	 * @param minDistance the new minimum distance
	 */
	void setMinDistance(double minDistance);
	
	/**
	 * Getter for the maximum distance of the camera to the position ({@link #getPosition()} of the {@link Marker} for it to be displayed.<br>
	 * If the camera is further to this {@link Marker} than this distance, it will be hidden!
	 *  
	 * @return the maximum distance for this {@link Marker} to be displayed
	 */
	double getMaxDistance();
	
	/**
	 * Sets the maximum distance of the camera to the position ({@link #getPosition()} of the {@link Marker} for it to be displayed.<br>
	 * If the camera is closer to this {@link Marker} than this distance, it will be hidden!
	 * 
	 * @param maxDistance the new maximum distance
	 */
	void setMaxDistance(double maxDistance);
	
	/**
	 * Getter for the label of this marker. The label can include html-tags.
	 * @return the label of this 
	 */
	String getLabel();
	
	/**
	 * Sets the label of this {@link Marker}. The label can include html-tags.
	 * <p>
	 * 	<b>Important:</b><br>
	 * 	Html-tags in the label will not be escaped, so you can use them to style the {@link Marker}-labels.<br>
	 * 	Make sure you escape all html-tags from possible user inputs to prevent possible <a href="https://en.wikipedia.org/wiki/Cross-site_scripting">XSS-Attacks</a> on the web-client!
	 * </p>
	 * 
	 * @param label the new label for this {@link Marker}
	 */
	void setLabel(String label);
	
	/**
	 * Gets the link-address of this {@link Marker}.<br>
	 * If a link is present, this link will be followed when the user clicks on the marker in the web-app.
	 * 
	 * @return the {@link Optional} link
	 */
	Optional<String> getLink();

	/**
	 * If this is <code>true</code> the link ({@link #getLink()}) will be opened in a new tab.
	 * @return whether the link will be opened in a new tab
	 * @see #getLink()
	 */
	boolean isNewTab();
	
	/**
	 * Sets the link-address of this {@link Marker}.<br>
	 * If a link is present, this link will be followed when the user clicks on the marker in the web-app.
	 * 
	 * @param link the link, or <code>null</code> to disable the link
	 * @param newTab whether the link should be opened in a new tab
	 */
	void setLink(String link, boolean newTab);

	/**
	 * Removes the link of this {@link Marker}.
	 */
	void removeLink();
	
}
