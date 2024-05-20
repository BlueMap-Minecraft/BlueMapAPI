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
package de.bluecolored.bluemap.api;

import com.flowpowered.math.vector.Vector2i;
import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import de.bluecolored.bluemap.api.markers.MarkerSet;
import org.jetbrains.annotations.ApiStatus;

import java.util.Map;
import java.util.function.Predicate;

/**
 * This class represents a map that is rendered by BlueMap of a specific world ({@link BlueMapWorld}).
 * Each map belongs to a map configured in BlueMap's configuration file (in the <code>maps: []</code> list).
 */
@SuppressWarnings("unused")
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
     * Getter for this map's {@link AssetStorage}. <br>
     * Each map has its own storage for assets. Assets that are stored here will be available to every webapp that
     * is displaying this map. E.g. these assets are also available in server-networks.
     * @return the {@link AssetStorage} of this map
     */
    AssetStorage getAssetStorage();

    /**
     * Getter for a (modifiable) {@link Map} of {@link MarkerSet}s with the key being the {@link MarkerSet}'s id.
     * Changing this map will change the {@link MarkerSet}s and markers displayed on the web-app for this map.
     * @return a {@link Map} of {@link MarkerSet}s.
     */
    Map<String, MarkerSet> getMarkerSets();

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
     * <p>Sets a filter that determines if a specific (hires) tile of this map should be updated or not.
     * If this filter returns false for a tile, the "render"-process of this tile will be cancelled and the tile will be left untouched.</p>
     * <p><b>Warning:</b> Using this method will harm the integrity of the map! Since BlueMap will still assume that the tile got updated properly.</p>
     * <p>Any previously set filters will get overwritten with the new one. You can get the current filter using {@link #getTileFilter()} and combine them if you wish.</p>
     * @param filter The filter that will be used from now on.
     */
    @ApiStatus.Experimental
    void setTileFilter(Predicate<Vector2i> filter);

    /**
     * Freezes or unfreezes the map in the same way the `/bluemap freeze` command does.
     * BlueMap will no longer attempt to update this map if it is frozen.
     * @param frozen Whether the map will be frozen or not
     */
    void setFrozen(boolean frozen);

    /**
     * Checks if the map is currently frozen
     * @return true if the map is frozen, false otherwise
     */
    boolean isFrozen();

    /**
     * Returns the currently set TileFilter. The default TileFilter is equivalent to <code>t -&gt; true</code>.
     */
    @ApiStatus.Experimental
    Predicate<Vector2i> getTileFilter();

    /**
     * Converts a block-position to a map-tile-coordinate for this map
     *
     * @param blockX the x-position of the block
     * @param blockZ the z-position of the block
     * @return the tile position
     */
    default Vector2i posToTile(double blockX, double blockZ){
        Vector2i offset = getTileOffset();
        Vector2i size = getTileSize();

        return new Vector2i(
                (int) Math.floor((blockX - offset.getX()) / size.getX()),
                (int) Math.floor((blockZ - offset.getY()) / size.getY())
            );
    }

    /**
     * Converts a block-position to a map-tile-coordinate for this map
     *
     * @param pos the position of the block
     * @return the tile position
     */
    default Vector2i posToTile(Vector3i pos){
        return posToTile(pos.getX(), pos.getZ());
    }

    /**
     * Converts a block-position to a map-tile-coordinate for this map
     *
     * @param pos the position of the block
     * @return the tile position
     */
    default Vector2i posToTile(Vector3d pos){
        return posToTile(pos.getX(), pos.getZ());
    }

}
