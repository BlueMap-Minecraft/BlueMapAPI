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

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import de.bluecolored.bluemap.api.BlueMapAPI;

/**
 * The {@link MarkerAPI} for easy manipulation of the <code>markers.json</code> that is used to display different Markers on the map.
 * <p>
 * 	<b>Important:</b><br>
 * 	If you made changes to any {@link MarkerSet} or {@link Marker} including creations and deletions, you need to finally save your changes by calling {@link #save()}!<br>
 * </p>
 * <p>To avoid any concurrent modifications to the <code>markers.json</code>, make sure your {@link MarkerAPI} is always loaded before making any changes, and saved right after the changes.</p> 
 * 
 * @see BlueMapAPI#getMarkerAPI()
 */
public interface MarkerAPI {

	/**
	 * Getter for an <i>unmodifiable</i> {@link Collection} containing all {@link MarkerSet}s that are currently loaded with BlueMap.
	 * 
	 * @return a {@link Collection} with all loaded {@link MarkerSet}s
	 */
	Collection<MarkerSet> getMarkerSets();
	
	/**
	 * Getter for a loaded {@link MarkerSet} with the given id.<br>
	 * Returns an empty {@link Optional} if no {@link MarkerSet} with that id is loaded.  
	 * 
	 * @param id the id of the {@link MarkerSet}
	 * @return an {@link Optional}&lt;{@link MarkerSet}&gt; with the given id 
	 */
	Optional<MarkerSet> getMarkerSet(String id);
	
	/**
	 * Created a new {@link MarkerSet} with the given id.<br>
	 * If there is already a {@link MarkerSet} with that id loaded, no new {@link MarkerSet} is created and the existing one is returned.
	 * 
	 * @param id the id of the {@link MarkerSet}
	 * @return a {@link MarkerSet} with the given id
	 */
	MarkerSet createMarkerSet(String id);
	
	/**
	 * Removes the given {@link MarkerSet}.<br>
	 * This is equivalent to calling <code>removeMarkerSet(markerSet.getId())</code>.
	 * 
	 * @param markerSet the {@link MarkerSet} to be removed
	 * @return <code>true</code> if the {@link MarkerSet} was removed, <code>false</code> if that {@link MarkerSet} didn't exist
	 */
	default boolean removeMarkerSet(MarkerSet markerSet) {
		return removeMarkerSet(markerSet.getId());
	}
	
	/**
	 * Removes the {@link MarkerSet} with the given id.
	 * 
	 * @param id the id of the {@link MarkerSet} to be removed
	 * @return <code>true</code> if the {@link MarkerSet} was removed, <code>false</code> if there was no {@link MarkerSet} with that id
	 */
	boolean removeMarkerSet(String id);
	
	/**
	 * Loads changes made by others, changes could be from other plugin's using the API or external changes to the <code>markers.json</code>.<br>
	 * Calling this will <b>override all unsaved changes</b> you made with this instance!
	 *  
	 * @throws IOException if an {@link IOException} occurred while loading the <code>markers.json</code>
	 */
	void load() throws IOException;
	
	/**
	 * Saves all changes made with this instance to the <code>markers.json</code>.<br> 
	 * 
	 * @throws IOException if an {@link IOException} occurred while saving the <code>markers.json</code>
	 */
	void save() throws IOException;
	
}
