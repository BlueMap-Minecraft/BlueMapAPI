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

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

import com.flowpowered.math.vector.Vector2i;
import com.flowpowered.math.vector.Vector3i;

public interface Renderer {

	/**
	 * Schedules the map-tile at this block position for all maps of this world and returns the created render-tickets.<br>
	 * If there already is a render-ticket scheduled for a tile, the existing one is returned instead of creating a new one.
	 * 
	 * @param world the {@link UUID} of the {@link BlueMapWorld} to render
	 * @param blockPosition a {@link Vector3i} for the block-position to render (the whole map-tiles will be rendered not only that block)
	 * 
	 * @return a {@link Collection} of the scheduled {@link RenderTicket}s 
	 */
	Collection<RenderTicket> render(UUID world, Vector3i blockPosition);

	/**
	 * Schedules the map-tile at this block position for all maps of this world and returns the created render-tickets.<br>
	 * If there already is a render-ticket scheduled for a tile, the existing one is returned instead of creating a new one.
	 * 
	 * @param world the {@link BlueMapWorld} to render
	 * @param blockPosition a {@link Vector3i} for the block-position to render (the whole map-tiles will be rendered not only that block)
	 * 
	 * @return a {@link Collection} of the scheduled {@link RenderTicket}s 
	 */
	default Collection<RenderTicket> render(BlueMapWorld world, Vector3i blockPosition) {
		Collection<RenderTicket> tickets = new HashSet<>();
		
		for (BlueMapMap map : world.getMaps()) {
			tickets.add(render(map, blockPosition));
		}
		
		return tickets;
	}

	/**
	 * Schedules the map-tile at this block position and returns the created render-ticket.<br>
	 * If there already is a render-ticket scheduled for that map-tile, the existing one is returned instead of creating a new one.
	 * 
	 * @param mapId the id of the {@link BlueMapMap} to render
	 * @param blockPosition a {@link Vector3i} for the block-position to render (the whole map-tile will be rendered not only that block)
	 * 
	 * @return the scheduled {@link RenderTicket}
	 */
	RenderTicket render(String mapId, Vector3i blockPosition);

	/**
	 * Schedules the map-tile at this block position and returns the created render-ticket.<br>
	 * If there already is a render-ticket scheduled for that map-tile, the existing one is returned instead of creating a new one.
	 * 
	 * @param map the {@link BlueMapMap}
	 * @param blockPosition a {@link Vector3i} for the block-position to render (the whole map-tile will be rendered not only that block)
	 * 
	 * @return the scheduled {@link RenderTicket}
	 */
	default RenderTicket render(BlueMapMap map, Vector3i blockPosition) {
		return render(map, map.posToTile(blockPosition));
	}
	
	/**
	 * Schedules the map-tile and returns the created render-ticket.<br>
	 * If there already is a render-ticket scheduled for that map-tile, the existing one is returned instead of creating a new one.
	 * 
	 * @param mapId the id of the {@link BlueMapMap} to render
	 * @param tile a {@link Vector2i} for the tile-position to render
	 * 
	 * @return the scheduled {@link RenderTicket}
	 */
	RenderTicket render(String mapId, Vector2i tile);

	/**
	 * Schedules the map-tile and returns the created render-ticket.<br>
	 * If there already is a render-ticket scheduled for that map-tile, the existing one is returned instead of creating a new one.
	 * 
	 * @param map the {@link BlueMapMap}
	 * @param tile a {@link Vector2i} for the tile-position to render
	 * 
	 * @return the scheduled {@link RenderTicket}
	 */
	RenderTicket render(BlueMapMap map, Vector2i tile);
	
}
