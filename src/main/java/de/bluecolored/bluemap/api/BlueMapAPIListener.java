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

/**
 * @deprecated Implementing {@link BlueMapAPIListener} can cause a ClassNotFoundException when you soft-depend on BlueMap and your plugin/mod gets used without BlueMap.
 * Use {@link BlueMapAPI#onEnable(Consumer)} and {@link BlueMapAPI#onDisable(Consumer)} instead.
 */
@Deprecated
public interface BlueMapAPIListener {

	/**
	 * Called when BlueMap has been loaded and started and the API is ready to use.<br>
	 * If {@link BlueMapAPI} is already enabled when this listener is registered this method will be called immediately <i>(on the same thread)</i>!
	 * <p><i>(Note: This method will likely be called asynchronously, <b>not</b> on the server-thread!</i></p>
	 * @param blueMapApi the {@link BlueMapAPI}
	 */
	default void onEnable(BlueMapAPI blueMapApi) {}
	
	/**
	 * Called <b>before</b> BlueMap is being unloaded and stopped, after this method returns the API is no longer usable!<br>
	 * Unlike {@link BlueMapAPIListener#onEnable(BlueMapAPI)}, if {@link BlueMapAPI} is not enabled when this listener is registered this method will <b>not</b> be called immediately.
	 * <p><i>(Note: This method will likely be called asynchronously, <b>not</b> on the server-thread!</i></p>
	 * @param blueMapApi the {@link BlueMapAPI}
	 */
	default void onDisable(BlueMapAPI blueMapApi) {}
	
}
