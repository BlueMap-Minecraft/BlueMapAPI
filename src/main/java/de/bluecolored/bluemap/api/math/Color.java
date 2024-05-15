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
package de.bluecolored.bluemap.api.math;

import java.util.Objects;

public class Color {

    private final int r, g, b;
    private final float a;

    /**
     * Creates a new color with the given red, green and blue values.
     *
     * @param red the red value in range 0-255
     * @param green the green value in range 0-255
     * @param blue the blue value in range 0-255
     */
    public Color(int red, int green, int blue) {
        this(red, green, blue, 1);
    }

    /**
     * Creates a new color with the given red, green, blue and alpha values.
     *
     * @param red the red value in range 0-255
     * @param green the green value in range 0-255
     * @param blue the blue value in range 0-255
     * @param alpha the alpha value in range 0-1
     */
    public Color(int red, int green, int blue, float alpha) {
        this.r = red;
        this.g = green;
        this.b = blue;
        this.a = alpha;
    }

    /**
     * Creates a new color from the given integer in the format 0xAARRGGBB.
     * @param i the integer to create the color from
     */
    public Color(int i) {
        this((i >> 16) & 0xFF, (i >> 8) & 0xFF, i & 0xFF, ((i >> 24) & 0xFF) / 255f);
    }

    /**
     * Creates a new color from the given integer in the format 0xRRGGBB.
     * @param i the integer to create the color from
     * @param alpha the alpha value in range 0-1
     */
    public Color(int i, float alpha) {
        this((i >> 16) & 0xFF, (i >> 8) & 0xFF, i & 0xFF, alpha);
    }

    /**
     * Creates a new color from the given integer in the format 0xRRGGBB.
     * @param i the integer to create the color from
     * @param alpha the alpha value in range 0-255
     */
    public Color(int i, int alpha) {
        this((i >> 16) & 0xFF, (i >> 8) & 0xFF, i & 0xFF, (float) alpha / 255f);
    }

    /**
     * The value can be an integer in String-Format (see {@link #Color(int)}) or a string in hexadecimal format
     * prefixed with # <i>(css-style: e.g. <code>#f16</code> becomes <code>#ff1166</code>)</i>.
     * @param cssColorString The string to parse to a color
     * @throws NumberFormatException If the value is not formatted correctly.
     */
    public Color(String cssColorString) {
        this(parseColorString(Objects.requireNonNull(cssColorString)));
    }

    /**
     * Getter for the red-component of the color.
     * @return the red-component of the color in range 0-255
     */
    public int getRed() {
        return r;
    }

    /**
     * Getter for the green-component of the color.
     * @return the green-component of the color in range 0-255
     */
    public int getGreen() {
        return g;
    }

    /**
     * Getter for the blue-component of the color.
     * @return the blue-component of the color in range 0-255
     */
    public int getBlue() {
        return b;
    }

    /**
     * Getter for the alpha-component of the color.
     * @return the alpha-component of the color in range 0-1
     */
    public float getAlpha() {
        return a;
    }

    private static int parseColorString(String value) {
        String val = value;
        if (val.charAt(0) == '#') {
            val = val.substring(1);
            if (val.length() == 3) val = val + "f";
            if (val.length() == 4) val = "" +
                    val.charAt(0) + val.charAt(0) + val.charAt(1) + val.charAt(1) +
                    val.charAt(2) + val.charAt(2) + val.charAt(3) + val.charAt(3);
            if (val.length() == 6) val = val + "ff";
            if (val.length() != 8) throw new NumberFormatException("Invalid color format: '" + value + "'!");
            val = val.substring(6, 8) + val.substring(0, 6); // move alpha to front
            return Integer.parseUnsignedInt(val, 16);
        }

        return Integer.parseInt(val);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Color color = (Color) o;

        if (r != color.r) return false;
        if (g != color.g) return false;
        if (b != color.b) return false;
        return Float.compare(color.a, a) == 0;
    }

    @Override
    public int hashCode() {
        int result = r;
        result = 31 * result + g;
        result = 31 * result + b;
        result = 31 * result + (a != +0.0f ? Float.floatToIntBits(a) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Color{" +
                "r=" + r +
                ", g=" + g +
                ", b=" + b +
                ", a=" + a +
                '}';
    }
}
