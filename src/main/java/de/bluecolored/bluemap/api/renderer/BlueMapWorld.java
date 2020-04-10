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

import java.nio.file.Path;
import java.util.Collection;
import java.util.UUID;

public interface BlueMapWorld {

	/**
	 * <p>Getter for the {@link UUID} of the world.</p>
	 * <p>
	 * 	The {@link UUID} of this world is <b>not</b> guaranteed to be consistent across reloads/restarts!
	 * </p>
	 * <p>
	 * 	<b>Implementation notes:</b><br>
	 * 	The used UUID highly depends on the implementation
	 * 	<table>
	 *  	<tr><th>Sponge</th><td>The UUID is equal to the returned UUID by World-Instances of the Sponge-API, so you can just use <code>spongeWorld.getUniqueId()</code><td><tr>
	 *  	<tr><th>Bukkit</th><td>The UUID is equal to the returned UUID by World-Instances of the Bukkit-API, so you can just use <code>bukkitWorld.getUID()</code><td><tr>
	 *  	<tr><th>Forge</th><td>The UUID is randomly generated, and changes on each reload/restart</code><td><tr>
	 *  	<tr><th>CLI</th><td>The UUID is randomly generated, and changes on each reload/restart</code><td><tr>
	 *  </table>
	 * 
	 * @return the {@link UUID} of the world
	 */
	UUID getUuid();
	
	/**
	 * Getter for the {@link Path} of this world's save-files (folder). This matches the folder configured in bluemap's config for this map ( <code>world:</code> ). 
	 * @return the save-folder of this world.
	 */
	Path getSaveFolder();
	
	/**
	 * Getter for all {@link BlueMapMap}s for this world
	 * @return a {@link Collection} of all {@link BlueMapMap}s for this world
	 */
	Collection<BlueMapMap> getMaps();
	
}
