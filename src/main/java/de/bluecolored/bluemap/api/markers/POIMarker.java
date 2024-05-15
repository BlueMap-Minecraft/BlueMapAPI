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

import com.flowpowered.math.vector.Vector2i;
import com.flowpowered.math.vector.Vector3d;
import de.bluecolored.bluemap.api.BlueMapMap;

import java.util.*;

@SuppressWarnings("FieldMayBeFinal")
public class POIMarker extends DistanceRangedMarker implements DetailMarker, ElementMarker {

    private Set<String> classes = new HashSet<>();

    private String detail;

    private String icon;
    private Vector2i anchor;

    /**
     * Empty constructor for deserialization.
     */
    @SuppressWarnings("unused")
    private POIMarker() {
        this("", Vector3d.ZERO);
    }

    /**
     * Creates a new {@link POIMarker} with the standard icon.
     *
     * @param label the label of the marker
     * @param position the coordinates of the marker
     *
     * @see #setLabel(String)
     * @see #setPosition(Vector3d)
     */
    public POIMarker(String label, Vector3d position) {
        this(label, position, "assets/poi.svg", new Vector2i(25, 45));
    }

    /**
     * Creates a new {@link POIMarker}.
     *
     * @param label the label of the marker
     * @param position the coordinates of the marker
     * @param iconAddress the html-content of the marker
     * @param anchor the anchor-point of the html-content
     *
     * @see #setLabel(String)
     * @see #setPosition(Vector3d)
     * @see #setIcon(String, Vector2i)
     */
    public POIMarker(String label, Vector3d position, String iconAddress, Vector2i anchor) {
        super("poi", label, position);
        this.detail = Objects.requireNonNull(label, "label must not be null");
        this.icon = Objects.requireNonNull(iconAddress, "iconAddress must not be null");
        this.anchor = Objects.requireNonNull(anchor, "anchor must not be null");
    }

    @Override
    public String getDetail() {
        return detail;
    }

    @Override
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * Getter for the relative address of the icon used to display this {@link POIMarker}
     * @return the relative web-address of the icon
     */
    public String getIconAddress() {
        return icon;
    }

    @Override
    public Vector2i getAnchor() {
        return anchor;
    }

    @Override
    public void setAnchor(Vector2i anchor) {
        this.anchor = Objects.requireNonNull(anchor, "anchor must not be null");
    }

    /**
     * Sets the icon for this {@link POIMarker}.
     * @param iconAddress the web-address of the icon-image. Can be an absolute or relative.
     *                    (See: {@link BlueMapMap#getAssetStorage()})
     * @param anchorX the x-position of the position (in pixels) where the icon is anchored to the map
     * @param anchorY the y-position of the position (in pixels) where the icon is anchored to the map
     */
    public void setIcon(String iconAddress, int anchorX, int anchorY) {
        setIcon(iconAddress, new Vector2i(anchorX, anchorY));
    }

    /**
     * Sets the icon for this {@link POIMarker}.
     * @param iconAddress the web-address of the icon-image. Can be an absolute or relative.
     *                    (See: {@link BlueMapMap#getAssetStorage()})
     * @param anchor the position of the position (in pixels) where the icon is anchored to the map
     */
    public void setIcon(String iconAddress, Vector2i anchor) {
        this.icon = Objects.requireNonNull(iconAddress, "iconAddress must not be null");
        this.anchor = Objects.requireNonNull(anchor, "anchor must not be null");
    }

    @Override
    public Collection<String> getStyleClasses() {
        return Collections.unmodifiableCollection(this.classes);
    }

    @Override
    public void setStyleClasses(Collection<String> styleClasses) {
        if (!styleClasses.stream().allMatch(STYLE_CLASS_PATTERN.asMatchPredicate()))
            throw new IllegalArgumentException("One of the provided style-classes has an invalid format!");

        this.classes.clear();
        this.classes.addAll(styleClasses);
    }

    @Override
    public void addStyleClasses(Collection<String> styleClasses) {
        if (!styleClasses.stream().allMatch(STYLE_CLASS_PATTERN.asMatchPredicate()))
            throw new IllegalArgumentException("One of the provided style-classes has an invalid format!");

        this.classes.addAll(styleClasses);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        POIMarker poiMarker = (POIMarker) o;

        if (!icon.equals(poiMarker.icon)) return false;
        return anchor.equals(poiMarker.anchor);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + icon.hashCode();
        result = 31 * result + anchor.hashCode();
        return result;
    }

    /**
     * Creates a Builder for {@link POIMarker}s.
     * @return a new Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends DistanceRangedMarker.Builder<POIMarker, Builder>
        implements DetailMarker.Builder<Builder>, ElementMarker.Builder<Builder> {

        Set<String> classes = new HashSet<>();

        String detail;
        String icon;
        Vector2i anchor;

        @Override
        public Builder detail(String detail) {
            this.detail = detail;
            return this;
        }

        /**
         * Sets the icon for the {@link POIMarker}.
         * @param iconAddress the web-address of the icon-image. Can be an absolute or relative.
         *                    (See: {@link BlueMapMap#getAssetStorage()})
         * @param anchorX the x-position of the position (in pixels) where the icon is anchored to the map
         * @param anchorY the y-position of the position (in pixels) where the icon is anchored to the map
         * @return this builder for chaining
         */
        public Builder icon(String iconAddress, int anchorX, int anchorY) {
            return icon(iconAddress, new Vector2i(anchorX, anchorY));
        }

        /**
         * Sets the icon for the {@link POIMarker}.
         * @param iconAddress the web-address of the icon-image. Can be an absolute or relative.
         *                    (See: {@link BlueMapMap#getAssetStorage()})
         * @param anchor the position of the position (in pixels) where the icon is anchored to the map
         * @return this builder for chaining
         */
        public Builder icon(String iconAddress, Vector2i anchor) {
            this.icon = Objects.requireNonNull(iconAddress, "iconAddress must not be null");
            this.anchor = Objects.requireNonNull(anchor, "anchor must not be null");
            return this;
        }

        @Override
        public Builder anchor(Vector2i anchor) {
            this.anchor = Objects.requireNonNull(anchor, "anchor must not be null");
            return this;
        }

        /**
         * The {@link POIMarker} will use the default icon. (See: {@link #icon(String, Vector2i)})
         * @return this builder for chaining
         */
        public Builder defaultIcon() {
            this.icon = null;
            this.anchor = null;
            return this;
        }

        @Override
        public Builder styleClasses(String... styleClasses) {
            Collection<String> styleClassesCollection = Arrays.asList(styleClasses);
            if (!styleClassesCollection.stream().allMatch(STYLE_CLASS_PATTERN.asMatchPredicate()))
                throw new IllegalArgumentException("One of the provided style-classes has an invalid format!");

            this.classes.addAll(styleClassesCollection);
            return this;
        }

        @Override
        public Builder clearStyleClasses() {
            this.classes.clear();
            return this;
        }

        /**
         * Creates a new {@link POIMarker} with the current builder-settings.<br>
         * The minimum required settings to build this marker are:
         * <ul>
         *     <li>{@link #setLabel(String)}</li>
         *     <li>{@link #setPosition(Vector3d)}</li>
         * </ul>
         * @return The new {@link POIMarker}-instance
         */
        public POIMarker build() {
            POIMarker marker = new POIMarker(
                    checkNotNull(label, "label"),
                    checkNotNull(position, "position")
            );
            if (detail != null) marker.setDetail(detail);
            if (icon != null) marker.setIcon(icon, anchor);
            else if (anchor != null) marker.setAnchor(anchor);
            marker.setStyleClasses(classes);
            return build(marker);
        }

    }

    // ------

    /**
     * @deprecated use {@link #builder()} instead.
     */
    @Deprecated(forRemoval = true)
    public static Builder toBuilder() {
        return new Builder();
    }

}
