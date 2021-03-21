package de.bluecolored.bluemap.api.marker;

public interface DistanceRangedMarker extends Marker {

	/**
	 * Getter for the minimum distance of the camera to the position for this {@link Marker} to be displayed.<br>
	 * If the camera is closer to this {@link Marker} than this distance, it will be hidden!
	 *
	 * @return the minimum distance for this {@link Marker} to be displayed
	 */
	double getMinDistance();

	/**
	 * Sets the minimum distance of the camera to the position of the {@link Marker} for it to be displayed.<br>
	 * If the camera is closer to this {@link Marker} than this distance, it will be hidden!
	 *
	 * @param minDistance the new minimum distance
	 */
	void setMinDistance(double minDistance);

	/**
	 * Getter for the maximum distance of the camera to the position of the {@link Marker} for it to be displayed.<br>
	 * If the camera is further to this {@link Marker} than this distance, it will be hidden!
	 *
	 * @return the maximum distance for this {@link Marker} to be displayed
	 */
	double getMaxDistance();

	/**
	 * Sets the maximum distance of the camera to the position of the {@link Marker} for it to be displayed.<br>
	 * If the camera is further to this {@link Marker} than this distance, it will be hidden!
	 *
	 * @param maxDistance the new maximum distance
	 */
	void setMaxDistance(double maxDistance);

}
