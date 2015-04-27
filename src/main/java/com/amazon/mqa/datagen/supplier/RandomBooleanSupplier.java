package com.amazon.mqa.datagen.supplier;

import com.google.common.base.Supplier;

import java.util.Random;

/** Boolean supplier. */
public final class RandomBooleanSupplier implements Supplier<Boolean> {

    /** Random. */
    private final Random random = new Random();

    @Override
    public Boolean get() {
        return random.nextBoolean();
    }
}
