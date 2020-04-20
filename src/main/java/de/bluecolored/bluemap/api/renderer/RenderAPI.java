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
package de.bluecolored.bluemap.api.renderer;

import java.util.UUID;

import com.flowpowered.math.vector.Vector2i;
import com.flowpowered.math.vector.Vector3i;

import de.bluecolored.bluemap.api.BlueMapMap;
import de.bluecolored.bluemap.api.BlueMapWorld;

/**
 * The {@link RenderAPI} is used to schedule tile-renders and process them on a number of different threads.
 */
public interface RenderAPI {

	/**
	 * Schedules the render of the map-tile at this block position for all maps of this world.<br>
	 * If there already is a render scheduled for one of the tiles, a second one will <b>not</b> be created.
	 * 
	 * @param world the {@link UUID} of the {@link BlueMapWorld} to render
	 * @param blockPosition a {@link Vector3i} for the block-position to render (the whole map-tiles will be rendered not only that block)
	 * 
	 * @throws IllegalArgumentException if there is no world loaded with the provided {@link UUID} 
	 */
	void render(UUID world, Vector3i blockPosition);

	/**
	 * Schedules the render of the map-tile at this block position for all maps of this world.<br>
	 * If there already is a render scheduled for one of the tiles, a second one will <b>not</b> be created.
	 * 
	 * @param world the {@link BlueMapWorld} to render
	 * @param blockPosition a {@link Vector3i} for the block-position to render (the whole map-tiles will be rendered not only that block)
	 */
	default void render(BlueMapWorld world, Vector3i blockPosition) {
		for (BlueMapMap map : world.getMaps()) {
			render(map, blockPosition);
		}
	}

	/**
	 * Schedules the render of the map-tile at this block position.<br>
	 * If there already is a render scheduled for the tile, a second one will <b>not</b> be created.
	 * 
	 * @param mapId the id of the {@link BlueMapMap} to render
	 * @param blockPosition a {@link Vector3i} for the block-position to render (the whole map-tile will be rendered not only that block)
	 * 
	 * @throws IllegalArgumentException if there is no map loaded with the provided mapId
	 */
	void render(String mapId, Vector3i blockPosition);

	/**
	 * Schedules the render of map-tile at this block position and returns the created render-ticket.<br>
	 * If there already is a render scheduled for the tile, a second one will <b>not</b> be created.
	 * 
	 * @param map the {@link BlueMapMap}
	 * @param blockPosition a {@link Vector3i} for the block-position to render (the whole map-tile will be rendered not only that block)
	 */
	default void render(BlueMapMap map, Vector3i blockPosition) {
		render(map, map.posToTile(blockPosition));
	}
	
	/**
	 * Schedules the render of the map-tile.<br>
	 * If there already is a render scheduled for the tile, a second one will <b>not</b> be created.
	 * 
	 * @param mapId the id of the {@link BlueMapMap} to render
	 * @param tile a {@link Vector2i} for the tile-position to render
	 * 
	 * @throws IllegalArgumentException if there is no map loaded with the provided mapId
	 */
	void render(String mapId, Vector2i tile);

	/**
	 * Schedules the render of the map-tile.<br>
	 * If there already is a render scheduled for the tile, a second one will <b>not</b> be created.
	 * 
	 * @param map the {@link BlueMapMap}
	 * @param tile a {@link Vector2i} for the tile-position to render
	 */
	void render(BlueMapMap map, Vector2i tile);
	
	/**
	 * Getter for the current size of the render-queue.
	 * @return the current size of the render-queue
	 */
	int renderQueueSize();
	
	/**
	 * Getter for the count of render threads.
	 * @return the count of render threads
	 */
	int renderThreadCount();
	
	/**
	 * Whether this {@link RenderAPI} is currently running or paused.
	 * @return <code>true</code> if this renderer is running
	 */
	boolean isRunning();
	
	/**
	 * Starts the renderer if it is not already running.
	 */
	void start();

	/**
	 * Pauses the renderer if it currently is running.
	 */
	void pause();
	
}
