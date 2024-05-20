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

import java.util.Collection;

/**
 * The {@link RenderManager} is used to schedule tile-renders and process them on a number of different threads.
 */
@SuppressWarnings("unused")
public interface RenderManager {

    /**
     * Schedules a task to update the given map.
     * @param map the map to be updated
     * @return true if a new task has been scheduled, false if not (usually because there is already an update-task for this map scheduled)
     */
    default boolean scheduleMapUpdateTask(BlueMapMap map) {
        return scheduleMapUpdateTask(map, false);
    }

    /**
     * Schedules a task to update the given map.
     * @param map the map to be updated
     * @param force it this is true, the task will forcefully re-render all tiles, even if there are no changes since the last map-update.
     * @return true if a new task has been scheduled, false if not (usually because there is already an update-task for this map scheduled)
     */
    boolean scheduleMapUpdateTask(BlueMapMap map, boolean force);

    /**
     * Schedules a task to update the given map.
     * @param map the map to be updated
     * @param regions The regions that should be updated ("region" refers to the coordinates of a minecraft region-file (32x32 chunks, 512x512 blocks))<br>
     *                BlueMaps updating-system works based on region-files. For this reason you can always only update a whole region-file at once.
     * @param force it this is true, the task will forcefully re-render all tiles, even if there are no changes since the last map-update.
     * @return true if a new task has been scheduled, false if not (usually because there is already an update-task for this map scheduled)
     */
    boolean scheduleMapUpdateTask(BlueMapMap map, Collection<Vector2i> regions, boolean force);

    /**
     * Schedules a task to purge the given map.
     * An update-task will be scheduled right after the purge, to get the map up-to-date again.
     * @param map the map to be purged
     * @return true if a new task has been scheduled, false if not (usually because there is already an update-task for this map scheduled)
     */
    boolean scheduleMapPurgeTask(BlueMapMap map);

    /**
     * Getter for the current size of the render-queue.
     * @return the current size of the render-queue
     */
    int renderQueueSize();

    /**
     * Getter for the current count of render threads.
     * @return the count of render threads
     */
    int renderThreadCount();

    /**
     * Whether this {@link RenderManager} is currently running or stopped.
     * @return <code>true</code> if this renderer is running
     */
    boolean isRunning();

    /**
     * Starts the renderer if it is not already running.
     * The renderer will be started with the configured number of render threads.
     */
    void start();

    /**
     * Starts the renderer if it is not already running.
     * The renderer will be started with the given number of render threads.
     * @param threadCount the number of render threads to use,
     *                    must be greater than 0 and should be less than or equal to the number of available cpu-cores.
     */
    void start(int threadCount);

    /**
     * Stops the renderer if it currently is running.
     */
    void stop();

}
