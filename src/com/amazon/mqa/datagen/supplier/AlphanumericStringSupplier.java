package com.amazon.mqa.datagen.supplier;

import com.google.common.base.Supplier;
import org.apache.commons.lang3.RandomStringUtils;

/** Supplier for alphanumeric string. */
public final class AlphanumericStringSupplier implements Supplier<String> {

    /** The default length for generated strings. */
    public static final int DEFAULT_STRING_LENGTH = 50;

    @Override
    public String get() {
        return RandomStringUtils.randomAlphanumeric(DEFAULT_STRING_LENGTH);
    }
}
