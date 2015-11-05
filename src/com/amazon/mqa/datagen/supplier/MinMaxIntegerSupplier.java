package com.amazon.mqa.datagen.supplier;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.Supplier;

/**
 * Supplies integer within min(inclusive), max(exclusive).
 */
public final class MinMaxIntegerSupplier implements Supplier<Integer> {

    /** The default min size. */
    public static final int DEFAULT_MIN_SIZE = 1;

    /** The default max size. */
    public static final int DEFAULT_MAX_SIZE = 11;

    /**
     * Creates a default {@link MinMaxIntegerSupplier}.
     *
     * @return the default instance.
     */
    public static MinMaxIntegerSupplier create() {
        return new MinMaxIntegerSupplier(DEFAULT_MIN_SIZE, DEFAULT_MAX_SIZE);
    }

    /** Supplies create integers. */
    private final Supplier<Integer> supplier = new RandomIntegerSupplier();

    /** The min value. */
    private final int min;

    /** The max value. */
    private final int max;

    /**
     * Instantiates a new {@link MinMaxIntegerSupplier}.
     *
     * @param min the min (inclusive).
     * @param max the max (exclusive).
     * @throws IllegalArgumentException if any argument is illegal.
     */
    public MinMaxIntegerSupplier(final int min, final int max) {
        checkArgument(min < max, "min is greater or equal to max");

        this.min = min;
        this.max = max;
    }

    @Override
    public Integer get() {
        return Math.abs(supplier.get()) % (max - min) + min;
    }
}
