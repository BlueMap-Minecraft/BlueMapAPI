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

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A set of {@link Marker}s that are displayed on the maps in the web-app.
 */
public class MarkerSet {

    private String label;
    private boolean toggleable, defaultHidden;
    private int sorting;
    private final ConcurrentHashMap<String, Marker> markers;

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
        this(label, true, false);
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
        this.label = Objects.requireNonNull(label);
        this.toggleable = toggleable;
        this.defaultHidden = defaultHidden;
        this.sorting = 0;
        this.markers = new ConcurrentHashMap<>();
    }

    /**
     * Getter for the label of this {@link MarkerSet}.
     * <p>The label is used in the web-app to name the toggle-button of this {@link MarkerSet} if it is toggleable.
     * ({@link #isToggleable()})</p>
     *
     * @return the label of this {@link MarkerSet}
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label of this {@link MarkerSet}.
     * <p>The label is used in the web-app to name the toggle-button of this {@link MarkerSet} if it is toggleable.
     * ({@link #isToggleable()})</p>
     *
     * @param label the new label
     */
    public void setLabel(String label) {
        this.label = Objects.requireNonNull(label);
    }

    /**
     * Checks if the {@link MarkerSet} is toggleable.
     * <p>If this is <code>true</code>, the web-app will display a toggle-button for this {@link MarkerSet} so the user
     * can choose to enable/disable all markers of this set.</p>
     *
     * @return whether this {@link MarkerSet} is toggleable
     */
    public boolean isToggleable() {
        return toggleable;
    }

    /**
     * Changes if this {@link MarkerSet} is toggleable.
     * <p>If this is <code>true</code>, the web-app will display a toggle-button for this {@link MarkerSet} so the user
     * can choose to enable/disable all markers of this set.</p>
     *
     * @param toggleable whether this {@link MarkerSet} should be toggleable
     */
    public void setToggleable(boolean toggleable) {
        this.toggleable = toggleable;
    }

    /**
     * Checks if this {@link MarkerSet} is hidden by default.
     * <p>This is basically the default-state of the toggle-button from {@link #isToggleable()}.
     * If this is <code>true</code> the markers of this marker set will initially be hidden and can be displayed
     * using the toggle-button.</p>
     *
     * @return whether this {@link MarkerSet} is hidden by default
     * @see #isToggleable()
     */
    public boolean isDefaultHidden() {
        return defaultHidden;
    }

    /**
     * Sets if this {@link MarkerSet} is hidden by default.
     * <p>This is basically the default-state of the toggle-button from {@link #isToggleable()}. If this is
     * <code>true</code> the markers of this marker set will initially be hidden and can be displayed using the toggle-button.</p>
     *
     * @param defaultHidden whether this {@link MarkerSet} should be hidden by default
     * @see #isToggleable()
     */
    public void setDefaultHidden(boolean defaultHidden) {
        this.defaultHidden = defaultHidden;
    }

    /**
     * Returns the sorting-value that will be used by the webapp to sort the marker-sets.<br>
     * A lower value makes the marker-set sorted first (in lists and menus), a higher value makes it sorted later.<br>
     * If multiple marker-sets have the same sorting-value, their order will be arbitrary.<br>
     * This value defaults to 0.
     * @return This marker-sets sorting-value
     */
    public int getSorting() {
        return sorting;
    }

    /**
     * Sets the sorting-value that will be used by the webapp to sort the marker-sets ("default"-sorting).<br>
     * A lower value makes the marker-set sorted first (in lists and menus), a higher value makes it sorted later.<br>
     * If multiple marker-sets have the same sorting-value, their order will be arbitrary.<br>
     * This value defaults to 0.
     * @param sorting the new sorting-value for this marker-set
     */
    public void setSorting(int sorting) {
        this.sorting = sorting;
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

    /**
     * Convenience method to add a {@link Marker} to this {@link MarkerSet}.<br>
     * Shortcut for: <code>getMarkers().get(String)</code>
     * @see Map#get(Object)
     */
    public Marker get(String key) {
        return getMarkers().get(key);
    }

    /**
     * Convenience method to add a {@link Marker} to this {@link MarkerSet}.<br>
     * Shortcut for: <code>getMarkers().put(String,Marker)</code>
     * @see Map#put(Object, Object)
     */
    public Marker put(String key, Marker marker) {
        return getMarkers().put(key, marker);
    }

    /**
     * Convenience method to remove a {@link Marker} from this {@link MarkerSet}.<br>
     * Shortcut for: <code>getMarkers().remove(String)</code>
     * @see Map#remove(Object)
     */
    public Marker remove(String key) {
        return getMarkers().remove(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MarkerSet markerSet = (MarkerSet) o;

        if (toggleable != markerSet.toggleable) return false;
        if (defaultHidden != markerSet.defaultHidden) return false;
        if (!label.equals(markerSet.label)) return false;
        return markers.equals(markerSet.markers);
    }

    @Override
    public int hashCode() {
        int result = label.hashCode();
        result = 31 * result + (toggleable ? 1 : 0);
        result = 31 * result + (defaultHidden ? 1 : 0);
        result = 31 * result + markers.hashCode();
        return result;
    }

    /**
     * Creates a Builder for {@link MarkerSet}s.
     * @return a new Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String label;
        private Boolean toggleable, defaultHidden;
        private Integer sorting;

        /**
         * Sets the label of the {@link MarkerSet}.
         * <p>The label is used in the web-app to name the toggle-button of the {@link MarkerSet} if it is toggleable.
         * ({@link #toggleable(Boolean)})</p>
         *
         * @param label the new label
         * @return this builder for chaining
         */
        public Builder label(String label) {
            this.label = label;
            return this;
        }

        /**
         * Changes if the {@link MarkerSet} is toggleable.
         * <p>If this is <code>true</code>, the web-app will display a toggle-button for the {@link MarkerSet}
         * so the user can choose to enable/disable all markers of this set.</p>
         *
         * @param toggleable whether the {@link MarkerSet} should be toggleable
         * @return this builder for chaining
         */
        public Builder toggleable(Boolean toggleable) {
            this.toggleable = toggleable;
            return this;
        }

        /**
         * Sets if this {@link MarkerSet} is hidden by default.
         * <p>This is basically the default-state of the toggle-button from {@link #toggleable(Boolean)}.
         * If this is <code>true</code> the markers of this marker set will initially be hidden and can be displayed
         * using the toggle-button.</p>
         *
         * @param defaultHidden whether this {@link MarkerSet} should be hidden by default
         * @return this builder for chaining
         * @see #isToggleable()
         */
        public Builder defaultHidden(Boolean defaultHidden) {
            this.defaultHidden = defaultHidden;
            return this;
        }

        /**
         * Sets the sorting-value that will be used by the webapp to sort the marker-sets ("default"-sorting).<br>
         * A lower value makes the marker-set sorted first (in lists and menus), a higher value makes it sorted later.<br>
         * If multiple marker-sets have the same sorting-value, their order will be arbitrary.<br>
         * This value defaults to 0.
         * @param sorting the new sorting-value for this marker-set
         */
        public Builder sorting(Integer sorting) {
            this.sorting = sorting;
            return this;
        }

        /**
         * Creates a new {@link MarkerSet} with the current builder-settings.<br>
         * The minimum required settings to build this marker-set are:
         * <ul>
         *     <li>{@link #setLabel(String)}</li>
         * </ul>
         * @return The new {@link MarkerSet}-instance
         */
        public MarkerSet build() {
            MarkerSet markerSet = new MarkerSet(
                    checkNotNull(label, "label")
            );
            if (toggleable != null) markerSet.setToggleable(toggleable);
            if (defaultHidden != null) markerSet.setDefaultHidden(defaultHidden);
            if (sorting != null) markerSet.setSorting(sorting);
            return markerSet;
        }

        @SuppressWarnings("SameParameterValue")
        <O> O checkNotNull(O object, String name) {
            if (object == null) throw new IllegalStateException(name + " has to be set and cannot be null");
            return object;
        }

    }

}
