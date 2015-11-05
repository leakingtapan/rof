package com.amazon.mqa.datagen.supplier;

import com.google.common.base.Supplier;

import java.util.Random;

/** Random double supplier. */
public final class RandomDoubleSupplier implements Supplier<Double> {

    /** Random. */
    private final Random random = new Random();

    @Override
    public Double get() {
        return random.nextDouble();
    }
}
