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

import com.flowpowered.math.vector.Vector2i;
import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;

public interface BlueMapMap {

	/**
	 * Returns this maps id, this is equal to the id configured in bluemap's config for this map.
	 * @return the id of this map
	 */
	String getId();

	/**
	 * Returns this maps display-name, this is equal to the name configured in bluemap's config for this map.
	 * @return the name of this map
	 */
	String getName();
	
	/**
	 * Getter for the {@link BlueMapWorld} of this map.
	 * @return the {@link BlueMapWorld} of this map
	 */
	BlueMapWorld getWorld();

	/**
	 * Getter for the size of all tiles on this map in blocks.
	 * @return the tile-size in blocks
	 */
	Vector2i getTileSize();
	
	/**
	 * Getter for the offset of the tile-grid on this map.<br>
	 * E.g. an offset of (2|-1) would mean that the tile (0|0) has block (2|0|-1) at it's min-corner.
	 * @return the tile-offset in blocks
	 */
	Vector2i getTileOffset();
	
	/**
	 * Converts a block-position to a map-tile-coordinate for this map
	 */
	default Vector2i posToTile(double blockX, double blockZ){
		Vector2i offset = getTileOffset();
		Vector2i size = getTileSize();
		
		return Vector2i.from(
				(int) Math.floor((blockX - offset.getX()) / size.getX()),
				(int) Math.floor((blockZ - offset.getY()) / size.getY())  
			);
	}
	
	/**
	 * Converts a block-position to a map-tile-coordinate for this map
	 */
	default Vector2i posToTile(Vector3i pos){
		return posToTile(pos.getX(), pos.getZ());
	}

	/**
	 * Converts a block-position to a map-tile-coordinate for this map
	 */
	default Vector2i posToTile(Vector3d pos){
		return posToTile(pos.getX(), pos.getZ()); 
	}
	
}
