package com.amazon.mqa.datagen.supplier;

import com.google.common.base.Supplier;

import java.util.Date;

/**
 * Supplies now as date.
 */
public final class Now implements Supplier<Date> {

    @Override
    public Date get() {
        return new Date();
    }
}
