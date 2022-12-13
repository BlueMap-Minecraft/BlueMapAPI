package de.bluecolored.bluemap.api.markers;

/**
 * @see POIMarker
 * @see ShapeMarker
 * @see ExtrudeMarker
 * @see LineMarker
 */
public interface DetailMarker {

    /**
     * Getter for the detail of this marker. The label can include html-tags.
     * @return the detail of this {@link Marker}
     */
    String getDetail();

    /**
     * Sets the detail of this {@link Marker}. The detail can include html-tags.<br>
     * This is the text that will be displayed on the popup when you click on this marker.
     * <p>
     * 	<b>Important:</b><br>
     * 	Html-tags in the label will not be escaped, so you can use them to style the {@link Marker}-detail.<br>
     * 	Make sure you escape all html-tags from possible user inputs to prevent possible
     * 	<a href="https://en.wikipedia.org/wiki/Cross-site_scripting">XSS-Attacks</a> on the web-client!
     * </p>
     *
     * @param detail the new detail for this {@link ObjectMarker}
     */
    void setDetail(String detail);

    interface Builder<B> {

        /**
         * Sets the detail of the {@link Marker}. The detail can include html-tags.<br>
         * This is the text that will be displayed on the popup when you click on the marker.
         * <p>
         * 	<b>Important:</b><br>
         * 	Html-tags in the label will not be escaped, so you can use them to style the {@link Marker}-detail.<br>
         * 	Make sure you escape all html-tags from possible user inputs to prevent possible
         * 	<a href="https://en.wikipedia.org/wiki/Cross-site_scripting">XSS-Attacks</a> on the web-client!
         * </p>
         *
         * @param detail the new detail for the {@link Marker}
         * @return this builder for chaining
         */
        B detail(String detail);

    }

}
