package com.amazon.mqa.datagen;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Test class B.
 */
public final class TestClassB {

    /** String[] field. */
    private String[] s;

    /** boolean[]. */
    private boolean[] booleans;

    /** byte[]. */
    private byte[] bytes;

    /** char[]. */
    private char[] chars;

    /** double[]. */
    private double[] doubles;

    /** float[]. */
    private float[] floats;

    /** int[]. */
    private int[] ints;

    /** long[]. */
    private long[] longs;

    /** short[]. */
    private short[] shorts;

    /**
     * Sets the strings.
     *
     * @param s the strings.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    public void setS(final String[] s) {
        this.s = checkNotNull(s, "s cannot be null");
    }

    /**
     * Sets the booleans.
     *
     * @param booleans the boolean array.
     */
    public void setBooleans(final boolean[] booleans) {
        this.booleans = booleans;
    }

    /**
     * Sets the bytes.
     *
     * @param bytes the byte array.
     */
    public void setBytes(final byte[] bytes) {
        this.bytes = bytes;
    }

    /**
     * Sets the chars.
     *
     * @param chars the char array.
     */
    public void setChars(final char[] chars) {
        this.chars = chars;
    }

    /**
     * Sets the doubles.
     *
     * @param doubles the double array.
     */
    public void setDoubles(final double[] doubles) {
        this.doubles = doubles;
    }

    /**
     * Sets the floats.
     *
     * @param floats the float array.
     */
    public void setFloats(final float[] floats) {
        this.floats = floats;
    }

    /**
     * Sets the ints.
     *
     * @param ints the int array.
     */
    public void setInts(final int[] ints) {
        this.ints = ints;
    }

    /**
     * Sets the longs.
     *
     * @param longs the long array.
     */
    public void setLongs(final long[] longs) {
        this.longs = longs;
    }

    /**
     * Sets the shorts.
     *
     * @param shorts the short array.
     */
    public void setShorts(final short[] shorts) {
        this.shorts = shorts;
    }
}
