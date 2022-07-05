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
     * The value can be an integer in String-Format (see {@link #Color(int)}) or a string in hexadecimal format
     * prefixed with # <i>(css-style: e.g. <code>#f16</code> becomes <code>#ff1166</code>)</i>.
     * @param cssColorString The string to parse to a color
     * @throws NumberFormatException If the value is not formatted correctly.
     */
    public Color(String cssColorString) {
        this(parseColorString(Objects.requireNonNull(cssColorString)));
    }

    public int getRed() {
        return r;
    }
    public int getGreen() {
        return g;
    }

    public int getBlue() {
        return b;
    }

    public float getAlpha() {
        return a;
    }

    private static int parseColorString(String val) {
        if (val.charAt(0) == '#') {
            val = val.substring(1);
            if (val.length() == 3) val = val + "f";
            if (val.length() == 4) val = "" + val.charAt(0) + val.charAt(0) + val.charAt(1) + val.charAt(1) + val.charAt(2) + val.charAt(2) + val.charAt(3) + val.charAt(3);
            if (val.length() == 6) val = val + "ff";
            if (val.length() != 8) throw new NumberFormatException("Invalid color format!");
            val = val.substring(6, 8) + val.substring(0, 6); // move alpha to front
            return Integer.parseUnsignedInt(val, 16);
        }

        return Integer.parseInt(val);
    }

}
