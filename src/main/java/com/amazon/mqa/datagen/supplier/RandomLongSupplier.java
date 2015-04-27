package com.amazon.mqa.datagen.supplier;

import com.google.common.base.Supplier;

import java.util.Random;

/** Random Long supplier. */
public final class RandomLongSupplier implements Supplier<Long> {

    /** Random. */
    private final Random random = new Random();

    @Override
    public Long get() {
        return random.nextLong();
    }
}
