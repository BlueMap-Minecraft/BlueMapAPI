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

import java.nio.file.Path;
import java.util.Collection;

/**
 * This class represents a world loaded by BlueMap.
 */
public interface BlueMapWorld {

    /**
     * Getter for the id of this world.
     * @return the id of this world
     */
    String getId();

    /**
     * Getter for the {@link Path} of this world's save-files.<br>
     * (To be exact: the parent-folder of the regions-folder used for rendering)
     * @return the save-folder of this world.
     * @deprecated Getting the save-folder of a world is no longer supported. As it is not guaranteed that every world has a save-folder.
     */
    @Deprecated
    Path getSaveFolder();

    /**
     * Getter for all {@link BlueMapMap}s for this world
     * @return an unmodifiable {@link Collection} of all {@link BlueMapMap}s for this world
     */
    Collection<BlueMapMap> getMaps();

}
