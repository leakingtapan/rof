package com.amazon.mqa.datagen.supplier;

import com.google.common.base.Supplier;
import org.apache.commons.lang3.RandomStringUtils;

/** Random Character supplier. */
public final class RandomCharacterSupplier implements Supplier<Character> {

    @Override
    public Character get() {
        return RandomStringUtils.randomAlphanumeric(1).charAt(0);
    }
}
