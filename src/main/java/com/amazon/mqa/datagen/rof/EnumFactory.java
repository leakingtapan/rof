package com.amazon.mqa.datagen.rof;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Random;

/**
 * Creates object from enumeration type.
 */
final class EnumFactory implements ObjectFactory {

    /** Creates random numbers. */
    private static final Random RANDOM = new Random();

    @Override
    public <T> T create(final Class<T> clazz) {
        checkNotNull(clazz, "clazz cannot be null");

        if (!clazz.isEnum()) {
            return null;
        }

        final T[] constants = clazz.getEnumConstants();

        return constants.length == 0 ? null : constants[RANDOM.nextInt(constants.length)];
    }

}
