package com.amazon.mqa.datagen;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Test class A.
 */
public final class TestClassA {

    /** a boolean field. */
    private final boolean booleanField;

    /** a byte field. */
    private final byte byteField;

    /** a char field. */
    private final char charField;

    /** a double field. */
    private final double doubleField;

    /** a float field. */
    private final float floatField;

    /** a int field. */
    private final int intField;

    /** a long field. */
    private final long longField;

    /** a short field. */
    private final short shortField;

    /** a string field. */
    private final String stringField;

    /** an optional field. */
    private final Optional<Integer> optionalField;

    /** a list field. */
    private final List<List<Integer>> list;

    /** a set of double. */
    private final Set<Double> doubleSet;

    /** a very complex map field. */
    private final Map<Set<Integer>, List<String>> mapField;

    /**
     * Instantiates a new {@link TestClassA}.
     *
     * @param byteField byte field.
     * @param booleanField boolean field.
     * @param charField char field.
     * @param doubleField double field.
     * @param floatField float field.
     * @param intField int field.
     * @param longField long Field.
     * @param shortField short field.
     * @param stringField string field.
     * @param list list field.
     * @param doubles the set of double.
     * @param mapField map field.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    //CHECKSTYLE:SUPPRESS:Param Count
    TestClassA(final boolean booleanField, final byte byteField, final char charField,
             final double doubleField, final float floatField, final int intField, final long longField,
             final short shortField, final String stringField, final Optional<Integer> optionalField,
             final List<List<Integer>> list, final Set<Double> doubles,
             final Map<Set<Integer>, List<String>> mapField) {
        this.booleanField = booleanField;
        this.byteField = byteField;
        this.charField = charField;
        this.doubleField = doubleField;
        this.floatField = floatField;
        this.intField = intField;
        this.longField = longField;
        this.shortField = shortField;
        this.stringField = checkNotNull(stringField, "stringField cannot be null");
        this.optionalField = checkNotNull(optionalField, "optionalField cannot be null");
        this.list = checkNotNull(list, "list cannot be null");
        this.doubleSet = checkNotNull(doubles, "doubles cannot be null");
        this.mapField = checkNotNull(mapField, "mapField cannot be null");
    }
    //CHECKSTYLE:UNSUPPRESS:Param Count


    /**
     * Gets is boolean.
     *
     * @return true if is boolean.
     */
    public boolean isBooleanField() {
        return booleanField;
    }

    /**
     * Gets byte field.
     *
     * @return the field.
     */
    public byte getByteField() {
        return byteField;
    }

    /**
     * Gets char field.
     *
     * @return the field.
     */
    public char getCharField() {
        return charField;
    }

    /**
     * Gets double field.
     *
     * @return the field.
     */
    public double getDoubleField() {
        return doubleField;
    }

    /**
     * Gets float field.
     *
     * @return the field.
     */
    public float getFloatField() {
        return floatField;
    }

    /**
     * Gets int field.
     *
     * @return the field.
     */
    public int getIntField() {
        return intField;
    }

    /**
     * Gets long field.
     *
     * @return the field.
     */
    public long getLongField() {
        return longField;
    }

    /**
     * Gets short field.
     *
     * @return the field.
     */
    public short getShortField() {
        return shortField;
    }

    /**
     * Gets string field.
     *
     * @return the field.
     */
    public String getStringField() {
        return stringField;
    }

    /**
     * Gets optional field.
     *
     * @return the field.
     */
    public Optional<Integer> getOptionalField() {
        return optionalField;
    }

    /**
     * Gets list of integers.
     *
     * @return the field.
     */
    public List<List<Integer>> getList() {
        return list;
    }

    /**
     * Gets the double set.
     *
     * @return the set.
     */
    public Set<Double> getDoubleSet() {
        return doubleSet;
    }

    /**
     * Gets the map field.
     *
     * @return the map.
     */
    public Map<Set<Integer>, List<String>> getMapField() {
        return mapField;
    }
}
