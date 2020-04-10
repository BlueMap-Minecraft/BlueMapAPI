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

import java.util.function.Consumer;

import com.flowpowered.math.vector.Vector2i;

public interface RenderTicket {
	
	/**
	 * <p>Getter for the {@link BlueMapMap} this ticket belongs to.</p>
	 * @return the map for this ticket
	 */
	BlueMapMap getMap();
	
	/**
	 * <p>Getter for the tile coordinates that this ticket renders.</p>
	 * @return the tile this ticket renders
	 */
	Vector2i getTile();

	/**
	 * The state of the ticket. This also returns <code>true</code> if the ticket has been processed but has failed to render the tile.
	 * @return <code>true</code> if this ticket has been processed, <code>false</code> otherwise.
	 */
	boolean isCompleted();
	
	/**
	 * Every listener registered with this method will be called (in an undefined order) after this ticket has been completed.
	 * <p>
	 * 	<b>Important:</b><br>
	 * 	The callback-method will likely (but is not guaranteed to) be called <b>asynchronously, on the render-thread!</b><br>
	 * 	You'll need to handle any further threading, scheduling or synchronizing yourself.
	 * </p>
	 * @param callback the method to be called on completion
	 */
	void onCompletion(Consumer<RenderTicket> callback);
	
}
