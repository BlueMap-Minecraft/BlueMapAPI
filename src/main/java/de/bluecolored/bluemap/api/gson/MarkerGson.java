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
package de.bluecolored.bluemap.api.gson;

import com.flowpowered.math.vector.Vector2d;
import com.flowpowered.math.vector.Vector2i;
import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import de.bluecolored.bluemap.api.markers.*;
import de.bluecolored.bluemap.api.math.Color;
import de.bluecolored.bluemap.api.math.Line;
import de.bluecolored.bluemap.api.math.Shape;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class MarkerGson {

    public static final Gson INSTANCE = addAdapters(new GsonBuilder())
            .setLenient()
            .create();

    private MarkerGson() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static GsonBuilder addAdapters(GsonBuilder builder) {
        return builder
            .registerTypeAdapter(Marker.class, new MarkerDeserializer())
            .registerTypeAdapter(Marker.class, new MarkerSerializer())
            .registerTypeAdapter(Line.class, new LineAdapter())
            .registerTypeAdapter(Shape.class, new ShapeAdapter())
            .registerTypeAdapter(Color.class, new ColorAdapter())
            .registerTypeAdapter(Vector2d.class, new Vector2dAdapter())
            .registerTypeAdapter(Vector3d.class, new Vector3dAdapter())
            .registerTypeAdapter(Vector2i.class, new Vector2iAdapter())
            .registerTypeAdapter(Vector3i.class, new Vector3iAdapter());
    }

    static class MarkerDeserializer implements JsonDeserializer<Marker> {

        private static final Map<String, Class<? extends Marker>> MARKER_TYPES = Map.of(
            "html", HtmlMarker.class,
            "poi", POIMarker.class,
            "shape", ShapeMarker.class,
            "extrude", ExtrudeMarker.class,
            "line", LineMarker.class
        );

        @Override
        public Marker deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
            String markerType = jsonElement.getAsJsonObject().get("type").getAsString();
            Class<? extends Marker> markerClass = MARKER_TYPES.get(markerType);
            if (markerClass == null) throw new JsonParseException("Unknown marker type: " + markerType);
            return context.deserialize(jsonElement, markerClass);
        }

    }

    static class MarkerSerializer implements JsonSerializer<Marker> {

        @Override
        public JsonElement serialize(Marker src, Type typeOfSrc, JsonSerializationContext context) {
            return context.serialize(src, src.getClass()); // serialize the actual marker-subclass
        }

    }

    static class LineAdapter extends TypeAdapter<Line> {
        private static final Vector3dAdapter VEC3D_ADAPTER = new Vector3dAdapter();

        @Override
        public void write(JsonWriter out, Line value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }

            out.beginArray();
            for (Vector3d point : value.getPoints()) {
                VEC3D_ADAPTER.write(out, point);
            }
            out.endArray();
        }

        @Override
        public Line read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }

            List<Vector3d> points = new LinkedList<>();

            in.beginArray();
            while (in.peek() != JsonToken.END_ARRAY) {
                Vector3d point = VEC3D_ADAPTER.read(in);
                if (point == null) continue;
                points.add(point);
            }
            in.endArray();

            return new Line(points.toArray(Vector3d[]::new));
        }

    }

    static class ShapeAdapter extends TypeAdapter<Shape> {
        private static final Vector2dAdapter VEC2D_ADAPTER = new Vector2dAdapter(true);

        @Override
        public void write(JsonWriter out, Shape value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }

            out.beginArray();
            for (Vector2d point : value.getPoints()) {
                VEC2D_ADAPTER.write(out, point);
            }
            out.endArray();
        }

        @Override
        public Shape read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }

            List<Vector2d> points = new LinkedList<>();

            in.beginArray();
            while (in.peek() != JsonToken.END_ARRAY) {
                Vector2d point = VEC2D_ADAPTER.read(in);
                if (point == null) continue;
                points.add(point);
            }
            in.endArray();

            return new Shape(points.toArray(Vector2d[]::new));
        }

    }

    static class ColorAdapter extends TypeAdapter<Color> {

        @Override
        public void write(JsonWriter out, Color value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }

            out.beginObject();
            out.name("r"); out.value(value.getRed());
            out.name("g"); out.value(value.getGreen());
            out.name("b"); out.value(value.getBlue());
            out.name("a"); out.value(value.getAlpha());
            out.endObject();
        }

        @Override
        public Color read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }

            in.beginObject();
            int r = 0, g = 0, b = 0;
            float a = 1;
            while (in.peek() != JsonToken.END_OBJECT) {
                switch (in.nextName()) {
                    case "r" : r = in.nextInt(); break;
                    case "g" : g = in.nextInt(); break;
                    case "b" : b = in.nextInt(); break;
                    case "a" : a = (float) in.nextDouble(); break;
                    default : in.skipValue(); break;
                }
            }
            in.endObject();

            return new Color(r, g, b, a);
        }

    }

    static class Vector2dAdapter extends TypeAdapter<Vector2d> {

        private final boolean useZ;

        public Vector2dAdapter() {
            this.useZ = false;
        }

        public Vector2dAdapter(boolean useZ) {
            this.useZ = useZ;
        }

        @Override
        public void write(JsonWriter out, Vector2d value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }

            out.beginObject();
            out.name("x"); writeRounded(out, value.getX());
            out.name(useZ ? "z" : "y"); writeRounded(out, value.getY());
            out.endObject();
        }

        @Override
        public Vector2d read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }

            in.beginObject();
            double x = 0, y = 0;
            while (in.peek() != JsonToken.END_OBJECT) {
                switch (in.nextName()) {
                    case "x" : x = in.nextDouble(); break;
                    case "y" : if (!useZ) y = in.nextDouble(); else in.skipValue(); break;
                    case "z" : if (useZ) y = in.nextDouble(); else in.skipValue(); break;
                    default : in.skipValue(); break;
                }
            }
            in.endObject();

            return new Vector2d(x, y);
        }

        private void writeRounded(JsonWriter json, double value) throws IOException {
            // rounding and remove ".0" to save string space
            double d = Math.round(value * 10000d) / 10000d;
            if (d == (long) d) json.value((long) d);
            else json.value(d);
        }

    }

    static class Vector3dAdapter extends TypeAdapter<Vector3d> {

        @Override
        public void write(JsonWriter out, Vector3d value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }

            out.beginObject();
            out.name("x"); writeRounded(out, value.getX());
            out.name("y"); writeRounded(out, value.getY());
            out.name("z"); writeRounded(out, value.getZ());
            out.endObject();
        }

        @Override
        public Vector3d read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }

            in.beginObject();
            double x = 0, y = 0, z = 0;
            while (in.peek() != JsonToken.END_OBJECT) {
                switch (in.nextName()) {
                    case "x" : x = in.nextDouble(); break;
                    case "y" : y = in.nextDouble(); break;
                    case "z" : z = in.nextDouble(); break;
                    default : in.skipValue(); break;
                }
            }
            in.endObject();

            return new Vector3d(x, y, z);
        }

        private void writeRounded(JsonWriter json, double value) throws IOException {
            // rounding and remove ".0" to save string space
            double d = Math.round(value * 10000d) / 10000d;
            if (d == (long) d) json.value((long) d);
            else json.value(d);
        }

    }

    static class Vector2iAdapter extends TypeAdapter<Vector2i> {

        @Override
        public void write(JsonWriter out, Vector2i value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }

            out.beginObject();
            out.name("x"); out.value(value.getX());
            out.name("y"); out.value(value.getY());
            out.endObject();
        }

        @Override
        public Vector2i read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }

            in.beginObject();
            int x = 0, y = 0;
            while (in.peek() != JsonToken.END_OBJECT) {
                switch (in.nextName()) {
                    case "x" : x = in.nextInt(); break;
                    case "y" : y = in.nextInt(); break;
                    default : in.skipValue(); break;
                }
            }
            in.endObject();

            return new Vector2i(x, y);
        }

    }

    static class Vector3iAdapter extends TypeAdapter<Vector3i> {

        @Override
        public void write(JsonWriter out, Vector3i value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }

            out.beginObject();
            out.name("x"); out.value(value.getX());
            out.name("y"); out.value(value.getY());
            out.name("z"); out.value(value.getZ());
            out.endObject();
        }

        @Override
        public Vector3i read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }

            in.beginObject();
            int x = 0, y = 0, z = 0;
            while (in.peek() != JsonToken.END_OBJECT) {
                switch (in.nextName()) {
                    case "x" : x = in.nextInt(); break;
                    case "y" : y = in.nextInt(); break;
                    case "z" : z = in.nextInt(); break;
                    default : in.skipValue(); break;
                }
            }
            in.endObject();

            return new Vector3i(x, y, z);
        }

    }

}
