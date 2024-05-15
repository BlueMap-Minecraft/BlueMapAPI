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

import java.util.*;

/**
 * A marker that is a html-element placed somewhere on the map.
 */
@SuppressWarnings("FieldMayBeFinal")
public class HtmlMarker extends DistanceRangedMarker implements ElementMarker {

    private Set<String> classes = new HashSet<>();

    private Vector2i anchor;
    private String html;

    /**
     * Empty constructor for deserialization.
     */
    @SuppressWarnings("unused")
    private HtmlMarker() {
        this("", Vector3d.ZERO, "");
    }

    /**
     * Creates a new {@link HtmlMarker}.
     *
     * @param label the label of the marker
     * @param position the coordinates of the marker
     * @param html the html-content of the marker
     *
     * @see #setLabel(String)
     * @see #setPosition(Vector3d)
     * @see #setHtml(String)
     */
    public HtmlMarker(String label, Vector3d position, String html) {
        this(label, position, html, new Vector2i(0, 0));
    }

    /**
     * Creates a new {@link HtmlMarker}.
     *
     * @param label the label of the marker
     * @param position the coordinates of the marker
     * @param html the html-content of the marker
     * @param anchor the anchor-point of the html-content
     *
     * @see #setLabel(String)
     * @see #setPosition(Vector3d)
     * @see #setHtml(String)
     * @see #setAnchor(Vector2i)
     */
    public HtmlMarker(String label, Vector3d position, String html, Vector2i anchor) {
        super("html", label, position);
        this.html = Objects.requireNonNull(html, "html must not be null");
        this.anchor = Objects.requireNonNull(anchor, "anchor must not be null");
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
     * Getter for the html-code of this HTML marker
     * @return the html-code
     */
    public String getHtml() {
        return html;
    }

    /**
     * Sets the html for this {@link HtmlMarker}.
     *
     * <p>
     * 	<b>Important:</b><br>
     * 	Make sure you escape all html-tags from possible user inputs to prevent possible
     * 	<a href="https://en.wikipedia.org/wiki/Cross-site_scripting">XSS-Attacks</a> on the web-client!
     * </p>
     *
     * @param html the html that will be inserted as the marker.
     */
    public void setHtml(String html) {
        this.html = Objects.requireNonNull(html, "html must not be null");
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

        HtmlMarker that = (HtmlMarker) o;

        if (!anchor.equals(that.anchor)) return false;
        return html.equals(that.html);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + anchor.hashCode();
        result = 31 * result + html.hashCode();
        return result;
    }

    /**
     * Creates a Builder for {@link HtmlMarker}s.
     * @return a new Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends DistanceRangedMarker.Builder<HtmlMarker, Builder>
        implements ElementMarker.Builder<Builder> {

        Set<String> classes = new HashSet<>();

        Vector2i anchor;
        String html;

        @Override
        public Builder anchor(Vector2i anchor) {
            this.anchor = anchor;
            return this;
        }

        /**
         * Sets the html for the {@link HtmlMarker}.
         *
         * <p>
         * 	<b>Important:</b><br>
         * 	Make sure you escape all html-tags from possible user inputs to prevent possible <a href="https://en.wikipedia.org/wiki/Cross-site_scripting">XSS-Attacks</a> on the web-client!
         * </p>
         *
         * @param html the html that will be inserted as the marker.
         * @return this builder for chaining
         */
        public Builder html(String html) {
            this.html = html;
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
         * Creates a new {@link HtmlMarker} with the current builder-settings.<br>
         * The minimum required settings to build this marker are:
         * <ul>
         *     <li>{@link #setLabel(String)}</li>
         *     <li>{@link #setPosition(Vector3d)}</li>
         *     <li>{@link #setHtml(String)}</li>
         * </ul>
         * @return The new {@link HtmlMarker}-instance
         */
        @Override
        public HtmlMarker build() {
            HtmlMarker marker = new HtmlMarker(
                    checkNotNull(label, "label"),
                    checkNotNull(position, "position"),
                    checkNotNull(html, "html")
            );
            if (anchor != null) marker.setAnchor(anchor);
            marker.setStyleClasses(classes);
            return build(marker);
        }

    }

}
