package com.amazon.mqa.datagen.supplier;

import com.google.common.base.Supplier;

/** Supplies Integer in the way that current value is 1 larger than the previous value. */
public final class IncrementalIntSupplier implements Supplier<Integer> {

    /** The next value to return. */
    private int value;

    /**
     * Instantiates a new {@link IncrementalIntSupplier}.
     *
     * @param startValue the start value.
     */
    public IncrementalIntSupplier(final int startValue) {
        this.value = startValue;
    }

    @Override
    public Integer get() {
        return value++;
    }
}
