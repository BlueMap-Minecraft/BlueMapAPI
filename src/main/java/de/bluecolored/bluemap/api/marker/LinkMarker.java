package de.bluecolored.bluemap.api.marker;

import java.util.Optional;

public interface LinkMarker extends Marker {

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
