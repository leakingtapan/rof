package com.amazon.mqa.datagen.supplier;

import com.amazon.mqa.datagen.ReflectionObjectFactory;
import com.google.common.base.Supplier;

import java.math.BigInteger;

/**
 * Suppliers for {@link BigInteger}.
 */
public final class RandomBigInteger implements Supplier<BigInteger> {

    @Override
    public BigInteger get() {
        return BigInteger.valueOf(ReflectionObjectFactory.createObject(long.class));
    }
}
