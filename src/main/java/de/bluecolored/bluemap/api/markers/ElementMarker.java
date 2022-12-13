package de.bluecolored.bluemap.api.markers;

import com.flowpowered.math.vector.Vector2i;

import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;

/**
 * @see HtmlMarker
 * @see POIMarker
 */
public interface ElementMarker {

    Pattern STYLE_CLASS_PATTERN = Pattern.compile("-?[_a-zA-Z]+[_a-zA-Z0-9-]*");

    /**
     * Getter for the position (in pixels) where the element is anchored to the map.
     * @return the anchor-position in pixels
     */
    Vector2i getAnchor();

    /**
     * Sets the position (in pixels) where the element is anchored to the map.
     * @param anchor the anchor-position in pixels
     */
    void setAnchor(Vector2i anchor);

    /**
     * Sets the position (in pixels) where the element is anchored to the map.
     * @param x the anchor-x-position in pixels
     * @param y the anchor-y-position in pixels
     */
    default void setAnchor(int x, int y) {
        setAnchor(new Vector2i(x, y));
    }

    /**
     * Getter for an (unmodifiable) collection of CSS-classed that the element of this marker will have.
     * @return the style-classes of this element-marker
     */
    Collection<String> getStyleClasses();

    /**
     * Sets the CSS-classes that the element of this marker will have.<br>
     * All classes must match <code>-?[_a-zA-Z]+[_a-zA-Z0-9-]*</code><br>
     * Any existing classes on this marker will be removed.
     * @param styleClasses the style-classes this element-marker will have
     */
    default void setStyleClasses(String... styleClasses) {
        this.setStyleClasses(Arrays.asList(styleClasses));
    }

    /**
     * Sets the CSS-classes that the element of this marker will have.<br>
     * All classes must match <code>-?[_a-zA-Z]+[_a-zA-Z0-9-]*</code><br>
     * Any existing classes on this marker will be removed.
     * @param styleClasses the style-classes this element-marker will have
     */
    void setStyleClasses(Collection<String> styleClasses);

    /**
     * Adds the CSS-classes that the element of this marker will have.<br>
     * All classes must match <code>-?[_a-zA-Z]+[_a-zA-Z0-9-]*</code><br>
     * @param styleClasses the style-classes this element-marker will have
     */
    default void addStyleClasses(String... styleClasses) {
        this.setStyleClasses(Arrays.asList(styleClasses));
    }
    
    /**
     * Adds the CSS-classes that the element of this marker will have.<br>
     * All classes must match <code>-?[_a-zA-Z]+[_a-zA-Z0-9-]*</code><br>
     * @param styleClasses the style-classes this element-marker will have
     */
    void addStyleClasses(Collection<String> styleClasses);
    
    interface Builder<B> {

        /**
         * Sets the position (in pixels) where the element is anchored to the map.
         * @param anchor the anchor-position in pixels
         * @return this builder for chaining
         */
        B anchor(Vector2i anchor);

        /**
         * <b>Adds</b> the CSS-classes that the element of this marker will have.<br>
         * All classes must match <code>-?[_a-zA-Z]+[_a-zA-Z0-9-]*</code><br>
         * @return this builder for chaining
         */
        B styleClasses(String... styleClasses);

        /**
         * Removes any existing style-classes from this builder.
         * @see #styleClasses(String...) 
         * @return this builder for chaining
         */
        B clearStyleClasses();

        /**
         * Sets the position (in pixels) where the element is anchored to the map.
         * @param x the anchor-x-position in pixels
         * @param y the anchor-y-position in pixels
         * @return this builder for chaining
         */
        default B anchor(int x, int y) {
            return anchor(new Vector2i(x, y));
        }

    }

}
