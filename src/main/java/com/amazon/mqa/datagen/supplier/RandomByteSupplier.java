package com.amazon.mqa.datagen.supplier;

import com.google.common.base.Supplier;

import java.util.Random;

/** byte supplier. */
public final class RandomByteSupplier implements Supplier<Byte> {

    /** Random. */
    private final Random random = new Random();

    @Override
    public Byte get() {
        return (byte) (random.nextInt() % Byte.MAX_VALUE);
    }
}
