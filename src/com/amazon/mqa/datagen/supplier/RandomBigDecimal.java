package com.amazon.mqa.datagen.supplier;

import com.amazon.mqa.datagen.ReflectionObjectFactory;
import com.google.common.base.Supplier;

import java.math.BigDecimal;

/**
 * Supplier for {@link BigDecimal}.
 */
public final class RandomBigDecimal implements Supplier<BigDecimal> {

    @Override
    public BigDecimal get() {
        return BigDecimal.valueOf(ReflectionObjectFactory.createObject(double.class));
    }
}
