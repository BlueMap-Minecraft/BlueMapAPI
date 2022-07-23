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

import de.bluecolored.bluemap.api.debug.DebugDump;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A set of {@link Marker}s that are displayed on the maps in the web-app.
 */
@DebugDump
public class MarkerSet {

    private String label;
    private boolean toggleable, defaultHidden;
    private final Map<String, Marker> markers;

    /**
     * Empty constructor for deserialization.
     */
    @SuppressWarnings("unused")
    private MarkerSet() {
        this("");
    }

    /**
     * Creates a new {@link MarkerSet}.
     *
     * @param label the label of the {@link MarkerSet}
     *
     * @see #setLabel(String)
     */
    public MarkerSet(String label) {
        this.label = label;
        this.toggleable = true;
        this.defaultHidden = false;
        this.markers = new ConcurrentHashMap<>();
    }

    /**
     * Creates a new {@link MarkerSet}.
     *
     * @param label the label of the {@link MarkerSet}
     * @param toggleable if the {@link MarkerSet} is toggleable
     * @param defaultHidden the default visibility of the {@link MarkerSet}
     *
     * @see #setLabel(String)
     * @see #setToggleable(boolean)
     * @see #setDefaultHidden(boolean)
     */
    public MarkerSet(String label, boolean toggleable, boolean defaultHidden) {
        this.label = label;
        this.toggleable = toggleable;
        this.defaultHidden = defaultHidden;
        this.markers = new ConcurrentHashMap<>();
    }

    /**
     * Getter for the label of this {@link MarkerSet}.
     * <p>The label is used in the web-app to name the toggle-button of this {@link MarkerSet} if it is toggleable. ({@link #isToggleable()})</p>
     * @return the label of this {@link MarkerSet}
     */
    public String getLabel() {
        return label;
    }
    /**
     * Sets the label of this {@link MarkerSet}.
     * <p>The label is used in the web-app to name the toggle-button of this {@link MarkerSet} if it is toggleable. ({@link #isToggleable()})</p>
     * @param label the new label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Checks if the {@link MarkerSet} is toggleable.
     * <p>If this is <code>true</code>, the web-app will display a toggle-button for this {@link MarkerSet} so the user can choose to enable/disable all markers of this set.</p>
     * @return whether this {@link MarkerSet} is toggleable
     */
    public boolean isToggleable() {
        return toggleable;
    }

    /**
     * Changes if this {@link MarkerSet} is toggleable.
     * <p>If this is <code>true</code>, the web-app will display a toggle-button for this {@link MarkerSet} so the user can choose to enable/disable all markers of this set.</p>
     * @param toggleable whether this {@link MarkerSet} should be toggleable
     */
    public void setToggleable(boolean toggleable) {
        this.toggleable = toggleable;
    }

    /**
     * Checks if this {@link MarkerSet} is hidden by default.
     * <p>This is basically the default-state of the toggle-button from {@link #isToggleable()}. If this is <code>true</code> the markers of this marker set will initially be hidden and can be displayed using the toggle-button.</p>
     *
     * @return whether this {@link MarkerSet} is hidden by default
     * @see #isToggleable()
     */
    public boolean isDefaultHidden() {
        return defaultHidden;
    }

    /**
     * Sets if this {@link MarkerSet} is hidden by default.
     * <p>This is basically the default-state of the toggle-button from {@link #isToggleable()}. If this is <code>true</code> the markers of this marker set will initially be hidden and can be displayed using the toggle-button.</p>
     *
     * @param defaultHide whether this {@link MarkerSet} should be hidden by default
     * @see #isToggleable()
     */
    public void setDefaultHidden(boolean defaultHide) {
        this.defaultHidden = defaultHide;
    }

    /**
     * Getter for a (modifiable) {@link Map} of all {@link Marker}s in this {@link MarkerSet}.
     * The keys of the map are the id's of the {@link Marker}s.
     *
     * @return a {@link Map} of all {@link Marker}s of this {@link MarkerSet}.
     */
    public Map<String, Marker> getMarkers() {
        return markers;
    }

}
