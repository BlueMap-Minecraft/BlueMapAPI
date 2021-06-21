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

import de.bluecolored.bluemap.api.marker.Marker;
import de.bluecolored.bluemap.api.marker.MarkerAPI;
import de.bluecolored.bluemap.api.renderer.RenderAPI;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

/**
 * An API to control the running instance of BlueMap.
 * <p>This API is thread-save, so you <b>can</b> use it async, off the main-server-thread, to save performance!</p> 
 */
public abstract class BlueMapAPI {
	private static BlueMapAPI instance;

	@Deprecated
	private static final Collection<BlueMapAPIListener> listener = new HashSet<>(2);
	
	private static final Collection<Consumer<BlueMapAPI>> onEnableConsumers = new HashSet<>(2);
	private static final Collection<Consumer<BlueMapAPI>> onDisableConsumers = new HashSet<>(2);

	/**
	 * Getter for the {@link RenderAPI}.
	 * @return the {@link RenderAPI}
	 */
	public abstract RenderAPI getRenderAPI();
	
	/**
	 * Getter for the {@link MarkerAPI}.<br>
	 * Calling this gives you a fresh loaded {@link MarkerAPI}, so you don't have to call {@link MarkerAPI#load()} right away!
	 * @return the {@link MarkerAPI}
	 * @throws IOException if there was an IOException loading the marker.json
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
	 * 
	 * @deprecated Implementing {@link BlueMapAPIListener} can cause a ClassNotFoundException when you soft-depend on BlueMap and your plugin/mod gets used without BlueMap.
	 * Use {@link BlueMapAPI#onEnable(Consumer)} and {@link BlueMapAPI#onDisable(Consumer)} instead. 
	 */
	@Deprecated
	public static synchronized void registerListener(BlueMapAPIListener listener) {
		BlueMapAPI.listener.add(listener);
		if (BlueMapAPI.instance != null) listener.onEnable(BlueMapAPI.instance);
	}
	
	/**
	 * Removes/Unregisters a previously registered listener
	 * @param listener the {@link BlueMapAPIListener} instance that has been registered previously
	 * 
	 * @return <code>true</code> if a listener was removed as a result of this call
	 * 
	 * @deprecated Implementing {@link BlueMapAPIListener} can cause a ClassNotFoundException when you soft-depend on BlueMap and your plugin/mod gets used without BlueMap.
	 * Use {@link BlueMapAPI#onEnable(Consumer)} and {@link BlueMapAPI#onDisable(Consumer)} instead.
	 */
	@Deprecated
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
	 * Registers a {@link Consumer} that will be called every time BlueMap has just been loaded and started and the API is ready to use.<br>
	 * If {@link BlueMapAPI} is already enabled when this listener is registered the consumer will be called immediately <i>(once, on the same thread)</i>!
	 * <p><b>The {@link Consumer} can be called multiple times if BlueMap disables and enables again, e.g. if BlueMap gets reloaded!</b></p>
	 * <p><i>(Note: The consumer will likely be called asynchronously, <b>not</b> on the server-thread!)</i></p>
	 * <p>Remember to unregister the consumer when you no longer need it using {@link #unregisterListener(Consumer)}.</p>
	 * @param consumer the {@link Consumer}
	 */
	public static synchronized void onEnable(Consumer<BlueMapAPI> consumer) {
		onEnableConsumers.add(consumer);
		if (BlueMapAPI.instance != null) consumer.accept(BlueMapAPI.instance);
	}
	
	/**
	 * Registers a {@link Consumer} that will be called every time <b>before</b> BlueMap is being unloaded and stopped, after the consumer returns the API is no longer usable!<br>
	 * Unlike with {@link BlueMapAPI#onEnable(Consumer)}, if {@link BlueMapAPI} is not enabled when this listener is registered the consumer will <b>not</b> be called.
	 * <p><b>The {@link Consumer} can be called multiple times if BlueMap disables and enables again, e.g. if BlueMap gets reloaded!</b></p>
	 * <p><i>(Note: The consumer will likely be called asynchronously, <b>not</b> on the server-thread!)</i></p>
	 * <p>Remember to unregister the consumer when you no longer need it using {@link #unregisterListener(Consumer)}.</p>
	 * @param consumer the {@link Consumer}
	 */
	public static synchronized void onDisable(Consumer<BlueMapAPI> consumer) {
		onDisableConsumers.add(consumer);
	}

	/**
	 * Removes a {@link Consumer} that has been registered using {@link #onEnable(Consumer)} or {@link #onDisable(Consumer)}.
	 * @param consumer the {@link Consumer} instance that has been registered previously
	 * @return <code>true</code> if a listener was removed as a result of this call
	 */
	public static synchronized boolean unregisterListener(Consumer<BlueMapAPI> consumer) {
		return onEnableConsumers.remove(consumer) | onDisableConsumers.remove(consumer);
	}

	/**
	 * Used by BlueMap to register the API and call the listeners properly. 
	 * @param instance the {@link BlueMapAPI}-instance
	 * @return <code>true</code> if the instance has been registered, <code>false</code> if there already was an instance registered 
	 * @throws ExecutionException if a {@link BlueMapAPIListener} threw an exception during the registration
	 */
	@SuppressWarnings("deprecation")
	protected static synchronized boolean registerInstance(BlueMapAPI instance) throws ExecutionException {
		if (BlueMapAPI.instance != null) return false;
		
		BlueMapAPI.instance = instance;

		List<Throwable> thrownExceptions = new ArrayList<>(0);
		
		for (Consumer<BlueMapAPI> listener : BlueMapAPI.onEnableConsumers) {
			try {
				listener.accept(BlueMapAPI.instance);
			} catch (Throwable ex) {
				thrownExceptions.add(ex);
			}
		}
		
		// backwards compatibility
		for (BlueMapAPIListener listener : BlueMapAPI.listener) {
			try {
				listener.onEnable(BlueMapAPI.instance);
			} catch (Throwable ex) {
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
	 * @param instance the {@link BlueMapAPI} instance
	 * @return <code>true</code> if the instance was unregistered, <code>false</code> if there was no or an other instance registered
	 * @throws ExecutionException if a {@link BlueMapAPIListener} threw an exception during the un-registration
	 */
	@SuppressWarnings("deprecation")
	protected static synchronized boolean unregisterInstance(BlueMapAPI instance) throws ExecutionException {
		if (BlueMapAPI.instance != instance) return false;

		List<Exception> thrownExceptions = new ArrayList<>(0);
		
		for (Consumer<BlueMapAPI> listener : BlueMapAPI.onDisableConsumers) {
			try {
				listener.accept(BlueMapAPI.instance);
			} catch (Exception ex) {
				thrownExceptions.add(ex);
			}
		}

		// backwards compatibility
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
