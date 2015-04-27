package com.amazon.mqa.datagen.supplier;

import com.google.common.base.Supplier;

import java.util.Random;

/** short supplier. */
public final class RandomShortSupplier implements Supplier<Short> {

    /** Random. */
    private final Random random = new Random();

    @Override
    public Short get() {
        return (short) (random.nextInt() % Short.MAX_VALUE);
    }
}
