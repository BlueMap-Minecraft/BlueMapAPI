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
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

/**
 * @see ShapeMarker
 * @see ExtrudeMarker
 * @see LineMarker
 */
public abstract class ObjectMarker extends DistanceRangedMarker implements DetailMarker {

    private String detail;

    @Nullable
    private String link;
    private boolean newTab;

    public ObjectMarker(String type, String label, Vector3d position) {
        super(type, label, position);
        this.detail = Objects.requireNonNull(label, "label must not be null");
    }

    @Override
    public String getDetail() {
        return detail;
    }

    @Override
    public void setDetail(String detail) {
        this.detail = Objects.requireNonNull(detail);
    }

    /**
     * Gets the link-address of this {@link Marker}.<br>
     * If a link is present, this link will be followed when the user clicks on the marker in the web-app.
     *
     * @return the {@link Optional} link
     */
    public Optional<String> getLink() {
        return Optional.ofNullable(link);
    }

    /**
     * If this is <code>true</code> the link ({@link #getLink()}) will be opened in a new tab.
     * @return whether the link will be opened in a new tab
     * @see #getLink()
     */
    public boolean isNewTab() {
        return newTab;
    }

    /**
     * Sets the link-address of this {@link Marker}.<br>
     * If a link is present, this link will be followed when the user clicks on the marker in the web-app.
     *
     * @param link the link, or <code>null</code> to disable the link
     * @param newTab whether the link should be opened in a new tab
     */
    public void setLink(String link, boolean newTab) {
        this.link = Objects.requireNonNull(link, "link must not be null");
        this.newTab = newTab;
    }

    /**
     * Removes the link of this {@link Marker}.
     */
    public void removeLink() {
        this.link = null;
        this.newTab = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ObjectMarker that = (ObjectMarker) o;

        if (newTab != that.newTab) return false;
        if (!detail.equals(that.detail)) return false;
        return Objects.equals(link, that.link);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + detail.hashCode();
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (newTab ? 1 : 0);
        return result;
    }

    public static abstract class Builder<T extends ObjectMarker, B extends ObjectMarker.Builder<T, B>>
            extends DistanceRangedMarker.Builder<T, B>
            implements DetailMarker.Builder<B> {

        String detail;
        String link;
        boolean newTab;

        @Override
        public B detail(String detail) {
            this.detail = detail;
            return self();
        }

        /**
         * Sets the link-address of the {@link Marker}.<br>
         * If a link is present, this link will be followed when the user clicks on the marker in the web-app.
         *
         * @param link the link, or <code>null</code> to disable the link
         * @param newTab whether the link should be opened in a new tab
         * @return this builder for chaining
         */
        public B link(String link, boolean newTab) {
            this.link = link;
            this.newTab = newTab;
            return self();
        }

        /**
         * The {@link Marker} will have no link. (See: {@link #link(String, boolean)})
         * @return this builder for chaining
         */
        public B noLink() {
            this.link = null;
            this.newTab = false;
            return self();
        }

        T build(T marker) {
            if (detail != null) marker.setDetail(detail);
            if (link != null) marker.setLink(link, newTab);
            return super.build(marker);
        }

    }

}
