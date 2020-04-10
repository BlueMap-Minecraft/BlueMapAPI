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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import de.bluecolored.bluemap.api.renderer.BlueMapMap;
import de.bluecolored.bluemap.api.renderer.BlueMapWorld;
import de.bluecolored.bluemap.api.renderer.Renderer;

public abstract class BlueMapAPI {
	private static BlueMapAPI instance;
	private static Collection<BlueMapAPIListener> listener = new ArrayList<>();

	/**
	 * Getter for the {@link Renderer} instance.
	 * @return the {@link Renderer}
	 */
	public abstract Renderer getRenderer();
	
	/**
	 * Getter for all {@link BlueMapMap}s loaded by BlueMap.
	 * @return an immutable collection of all loaded {@link BlueMapMap}s 
	 */
	public abstract Collection<BlueMapMap> getMaps();
	
	/**
	 * Getter for all {@link BlueMapWorld}s loaded by BlueMap.
	 * @return an immutable collection of all loaded {@link BlueMapWorld}s
	 */
	public abstract Collection<BlueMapWorld> getWorlds();
	
	/**
	 * Getter for the installed BlueMap version
	 * @return the version-string
	 */
	public abstract String getBlueMapVersion();
	
	/**
	 * Register a listener that will be called when the API enables/disables
	 * @param listener the {@link BlueMapAPIListener}
	 */
	public static synchronized void registerListener(BlueMapAPIListener listener) {
		BlueMapAPI.listener.add(listener);
		if (BlueMapAPI.instance != null) listener.onEnable(BlueMapAPI.instance);
	}
	
	/**
	 * Removes/Unregisters a previously registered listener
	 * @param listener the {@link BlueMapAPIListener} instance that has been registered previously
	 * 
	 * @return <code>true</code> if a listener was removed as a result of this call
	 */
	public static synchronized boolean unregisterListener(BlueMapAPIListener listener) {
		return BlueMapAPI.listener.remove(listener);
	}
	
	/**
	 * Returns an instance of {@link BlueMapAPI} if it is currently enabled, else an empty {@link Optional} is returned.
	 * @return an {@link Optional}&lt;{@link BlueMapAPI}&gt;
	 */
	public static synchronized Optional<BlueMapAPI> getInstance() {
		return Optional.ofNullable(instance);
	}
	
	/**
	 * Used by BlueMap to register the API and call the listeners properly. 
	 * @param instance {@link BlueMapAPI}-instance
	 */
	static synchronized void registerInstance(BlueMapAPI instance) {
		if (BlueMapAPI.instance != null) throw new IllegalStateException("There already is an API instance registered!");
		
		BlueMapAPI.instance = instance;

		for (BlueMapAPIListener listener : BlueMapAPI.listener) {
			listener.onEnable(BlueMapAPI.instance);
		}
	}
	
	/**
	 * Used by BlueMap to unregister the API and call the listeners properly.
	 */
	static synchronized void unregisterInstance() {
		if (BlueMapAPI.instance == null) throw new IllegalStateException("There is no API instance registered!");
		
		for (BlueMapAPIListener listener : BlueMapAPI.listener) {
			listener.onDisable(BlueMapAPI.instance);
		}	
		
		BlueMapAPI.instance = null;
	}
}
