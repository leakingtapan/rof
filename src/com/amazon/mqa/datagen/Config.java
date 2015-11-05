package com.amazon.mqa.datagen;

import static com.google.common.base.Preconditions.checkNotNull;

import com.amazon.mqa.datagen.supplier.AlphanumericStringSupplier;
import com.amazon.mqa.datagen.supplier.MinMaxIntegerSupplier;
import com.amazon.mqa.datagen.supplier.Now;
import com.amazon.mqa.datagen.supplier.RandomBigDecimal;
import com.amazon.mqa.datagen.supplier.RandomBigInteger;
import com.amazon.mqa.datagen.supplier.RandomBooleanSupplier;
import com.amazon.mqa.datagen.supplier.RandomByteSupplier;
import com.amazon.mqa.datagen.supplier.RandomCharacterSupplier;
import com.amazon.mqa.datagen.supplier.RandomDoubleSupplier;
import com.amazon.mqa.datagen.supplier.RandomFloatSupplier;
import com.amazon.mqa.datagen.supplier.RandomIntegerSupplier;
import com.amazon.mqa.datagen.supplier.RandomLongSupplier;
import com.amazon.mqa.datagen.supplier.RandomShortSupplier;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Map;

/**
 * Immutable class that holds configuration for {@link ReflectionObjectFactory}.
 */
public final class Config {

    /** Map from basic class to its supplier. */
    private static final Map<Class<?>, Supplier> BASIC_SUPPLIERS =
            new ImmutableMap.Builder<Class<?>, Supplier>()
                    .put(boolean.class, new RandomBooleanSupplier())
                    .put(byte.class, new RandomByteSupplier())
                    .put(char.class, new RandomCharacterSupplier())
                    .put(double.class, new RandomDoubleSupplier())
                    .put(float.class, new RandomFloatSupplier())
                    .put(int.class, new RandomIntegerSupplier())
                    .put(long.class, new RandomLongSupplier())
                    .put(short.class, new RandomShortSupplier())
                    .put(Boolean.class, new RandomBooleanSupplier())
                    .put(Byte.class, new RandomByteSupplier())
                    .put(Character.class, new RandomCharacterSupplier())
                    .put(Double.class, new RandomDoubleSupplier())
                    .put(Float.class, new RandomFloatSupplier())
                    .put(Integer.class, new RandomIntegerSupplier())
                    .put(Long.class, new RandomLongSupplier())
                    .put(Short.class, new RandomShortSupplier())
                    .put(String.class, new AlphanumericStringSupplier())
                    .put(Date.class, new Now())
                    .put(BigInteger.class, new RandomBigInteger())
                    .put(BigDecimal.class, new RandomBigDecimal())
                    .build();

    /**
     * Creates a default {@link Config} with basic supplier in {@link Config#BASIC_SUPPLIERS}
     * and array size ranges from 1 to 10 (inclusive).
     *
     * @return the default instance.
     */
    public static Config createDefault() {
        final int minSize = 1;
        final int maxSize = 11;

        return new Config(BASIC_SUPPLIERS, new MinMaxIntegerSupplier(minSize, maxSize));
    }

    /** Map from class to supplier. */
    private final Map<Class<?>, Supplier> suppliers;

    /** Supplies array size. */
    private final Supplier<Integer> arraySizeSupplier;

    /**
     * Instantiates a new {@link Config}.
     *
     * @param suppliers a map from class to its supplier.
     * @param arraySizeSupplier supplies array size.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    Config(final Map<Class<?>, Supplier> suppliers, final Supplier<Integer> arraySizeSupplier) {
        this.suppliers = checkNotNull(suppliers, "suppliers cannot be null");
        this.arraySizeSupplier = checkNotNull(arraySizeSupplier, "arraySizeSupplier cannot be null");
    }

    /**
     * Create a new {@link Config} with supplier for class.
     *
     * If class is not in supplier map, add a supplier for class; otherwise, update the supplier.
     *
     * @param clazz the class to supplier
     * @param supplier the supplier of class.
     * @param <T> the type of class.
     * @return the new configuration.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    public <T> Config withSupplier(final Class<T> clazz, final Supplier<T> supplier) {
        checkNotNull(clazz, "clazz cannot be null");
        checkNotNull(supplier, "supplier cannot be null");

        final Map<Class<?>, Supplier> newSuppliers = Maps.newHashMap();
        newSuppliers.putAll(suppliers);
        newSuppliers.put(clazz, supplier);

        return new Config(newSuppliers, arraySizeSupplier);
    }

    /**
     * Creates a new {@link Config} with array size supplier.
     *
     * @param arraySizeSupplier supplies array size.
     * @return the new configuration.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    public Config withArraySizeSupplier(final Supplier<Integer> arraySizeSupplier) {
        checkNotNull(arraySizeSupplier, "arraySizeSupplier cannot be null");

        return new Config(suppliers, arraySizeSupplier);
    }

    /**
     * @return the suppliers.
     */
    public Map<Class<?>, Supplier> getSuppliers() {
        return ImmutableMap.copyOf(suppliers);
    }

    /**
     * @return the supplier for array size.
     */
    public Supplier<Integer> getArraySizeSupplier() {
        return arraySizeSupplier;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(final Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }
}
