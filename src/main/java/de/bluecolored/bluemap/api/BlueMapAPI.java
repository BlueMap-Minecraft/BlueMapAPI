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

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import de.bluecolored.bluemap.api.marker.Marker;
import de.bluecolored.bluemap.api.marker.MarkerAPI;
import de.bluecolored.bluemap.api.renderer.RenderAPI;

/**
 * An API to control the running instance of BlueMap.
 * <p>This API is thread-save, so you <b>can</b> use it async, off the main-server-thread, to save performance!</p> 
 */
public abstract class BlueMapAPI {
	private static BlueMapAPI instance;
	private static Collection<BlueMapAPIListener> listener = new ArrayList<>();

	/**
	 * Getter for the {@link RenderAPI}.
	 * @return the {@link RenderAPI}
	 */
	public abstract RenderAPI getRenderAPI();
	
	/**
	 * Getter for the {@link MarkerAPI}.<br>
	 * Calling this gives you a fresh loaded {@link MarkerAPI}, so you don't have to call {@link MarkerAPI#load()} right away!
	 * @return the {@link MarkerAPI}
	 */
	public abstract MarkerAPI getMarkerAPI() throws IOException;
	
	/**
	 * Getter for all {@link BlueMapMap}s loaded by BlueMap.
	 * @return an unmodifiable collection of all loaded {@link BlueMapMap}s 
	 */
	public abstract Collection<BlueMapMap> getMaps();
	
	/**
	 * Getter for all {@link BlueMapWorld}s loaded by BlueMap.
	 * @return an unmodifiable collection of all loaded {@link BlueMapWorld}s
	 */
	public abstract Collection<BlueMapWorld> getWorlds();
	
	/**
	 * Getter for a {@link BlueMapWorld} loaded by BlueMap with the given {@link UUID}.
	 *
	 * <p><i>See the documentation of {@link BlueMapWorld#getUuid()} for more information about the nature of the worlds {@link UUID}s!</i></p>  
	 * 
	 * @see BlueMapWorld#getUuid()
	 * 
	 * @param uuid the {@link UUID} of the world
	 * 
	 * @return an {@link Optional} with the {@link BlueMapWorld} if it exists
	 */
	public abstract Optional<BlueMapWorld> getWorld(UUID uuid);
	
	/**
	 * Getter for a {@link BlueMapMap} loaded by BlueMap with the given id.
	 * @param id the map id (equivalent to the id configured in BlueMap's config
	 * @return an {@link Optional} with the {@link BlueMapMap} if it exists
	 */
	public abstract Optional<BlueMapMap> getMap(String id);
	
	/**
	 * Creates an image-file with the given {@link BufferedImage} somewhere in the web-root, so it can be used in the web-app (e.g. for {@link Marker}-icons).
	 * 
	 * <p>The given <code>path</code> is used as file-name and (separated with '/') optional folders to organize the image-files. Do NOT include the file-ending! (e.g. <code>"someFolder/somePOIIcon"</code> will result in a file "somePOIIcon.png" in a folder "someFolder").</p>
	 * <p>If the image file with the given path already exists, it will be replaced.</p>
	 * 
	 * @param image the image to create
	 * @param path the path/name of this image, the separator-char is '/'
	 * @return the relative address of the image in the web-app
	 * @throws IOException If an {@link IOException} is thrown while writing the image
	 */
	public abstract String createImage(BufferedImage image, String path) throws IOException;
	
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
	protected static synchronized boolean registerInstance(BlueMapAPI instance) throws ExecutionException {
		if (BlueMapAPI.instance != null) return false;
		
		BlueMapAPI.instance = instance;

		List<Exception> thrownExceptions = new ArrayList<>(0);
		for (BlueMapAPIListener listener : BlueMapAPI.listener) {
			try {
				listener.onEnable(BlueMapAPI.instance);
			} catch (Exception ex) {
				thrownExceptions.add(ex);
			}
		}
		
		if (!thrownExceptions.isEmpty()) {
			ExecutionException ex = new ExecutionException(thrownExceptions.get(0));
			for (int i = 1; i < thrownExceptions.size(); i++) {
				ex.addSuppressed(thrownExceptions.get(i));
			}
			throw ex;
		}

		return true;
	}
	
	/**
	 * Used by BlueMap to unregister the API and call the listeners properly.
	 */
	protected static synchronized boolean unregisterInstance(BlueMapAPI instance) throws ExecutionException {
		if (BlueMapAPI.instance != instance) return false;

		List<Exception> thrownExceptions = new ArrayList<>(0);
		for (BlueMapAPIListener listener : BlueMapAPI.listener) {
			try {
				listener.onDisable(BlueMapAPI.instance);	
			} catch (Exception ex) {
				thrownExceptions.add(ex);
			}
		}
		
		BlueMapAPI.instance = null;

		if (!thrownExceptions.isEmpty()) {
			ExecutionException ex = new ExecutionException(thrownExceptions.get(0));
			for (int i = 1; i < thrownExceptions.size(); i++) {
				ex.addSuppressed(thrownExceptions.get(i));
			}
			throw ex;
		}
		
		return true;
	}
}
