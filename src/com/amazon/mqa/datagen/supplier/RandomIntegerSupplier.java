package com.amazon.mqa.datagen.supplier;

import com.google.common.base.Supplier;

import java.util.Random;

/** Random Integer supplier. */
public final class RandomIntegerSupplier implements Supplier<Integer> {

    /** Random. */
    private final Random random = new Random();
    
    @Override
    public Integer get() {
        return random.nextInt();
    }
}
