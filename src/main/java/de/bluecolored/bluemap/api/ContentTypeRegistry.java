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
import java.util.HashMap;
import java.util.Map;

/**
 * This Content-Type registry is used by the internal webserver to get the content-type of an asset.<br>
 * The most commonly used file-suffixes and their content-types are registered by default, but you can use the static
 * methods of this class to add more, if you need them.<br>
 * <b>Note:</b> that any additionally added types won't work if the user uses an external webserver to serve their map-files.
 */
public class ContentTypeRegistry {

    private static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";
    private static final Map<String, String> SUFFIX_MAP = new HashMap<>();

    static {
        register("txt", "text/plain");
        register("css", "text/css");
        register("csv", "text/csv");
        register("htm", "text/html");
        register("html", "text/html");
        register("js", "text/javascript");
        register("xml", "text/xml");

        register("png", "image/png");
        register("jpg", "image/jpeg");
        register("jpeg", "image/jpeg");
        register("gif", "image/gif");
        register("webp", "image/webp");
        register("tif", "image/tiff");
        register("tiff", "image/tiff");
        register("svg", "image/svg+xml");

        register("json", "application/json");

        register("mp3", "audio/mpeg");
        register("oga", "audio/ogg");
        register("wav", "audio/wav");
        register("weba", "audio/webm");

        register("mp4", "video/mp4");
        register("mpeg", "video/mpeg");
        register("webm", "video/webm");

        register("ttf", "font/ttf");
        register("woff", "font/woff");
        register("woff2", "font/woff2");
    }

    /**
     * Derives the content-type (mime) string using a path denoting a file
     * @param path The path pointing at the file
     * @return The derived content-type string
     */
    public static String fromPath(Path path) {
        return fromFileName(path.getFileName().toString());
    }

    /**
     * Derives the content-type (mime) string from the name of a file
     * @param fileName The name of the file
     * @return The derived content-type string
     */
    public static String fromFileName(String fileName) {
        int i = fileName.lastIndexOf('.');
        if (i < 0) return DEFAULT_CONTENT_TYPE;

        int s = fileName.lastIndexOf('/');
        if (i < s) return DEFAULT_CONTENT_TYPE;

        String suffix = fileName.substring(i + 1);
        return fromFileSuffix(suffix);
    }

    /**
     * Searches and returns the content-type for the provided file-suffix.
     * @param suffix The type-suffix of a file-name
     * @return The content-type string
     */
    public static String fromFileSuffix(String suffix) {
        String contentType = SUFFIX_MAP.get(suffix);
        if (contentType != null) return contentType;
        return DEFAULT_CONTENT_TYPE;
    }

    /**
     * Registers a new file-suffix =&gt; content-type mapping to this registry.
     * @param fileSuffix The type-suffix of a file-name
     * @param contentType The content-type string
     */
    public static void register(String fileSuffix, String contentType) {
        SUFFIX_MAP.put(fileSuffix, contentType);
    }

}
