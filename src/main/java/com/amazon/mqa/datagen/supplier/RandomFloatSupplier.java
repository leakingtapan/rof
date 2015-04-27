package com.amazon.mqa.datagen.supplier;

import com.google.common.base.Supplier;

import java.util.Random;

/** Random Float supplier. */
public final class RandomFloatSupplier implements Supplier<Float> {

    /** Random. */
    private final Random random = new Random();

    @Override
    public Float get() {
        return random.nextFloat();
    }
}
