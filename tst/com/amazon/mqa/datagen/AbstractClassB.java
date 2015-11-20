package com.amazon.mqa.datagen;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * An abstract class that doesn't have default constructor.
 */
public abstract class AbstractClassB {

    /** Value. */
    private final String value;


    /**
     * Instantiates a new {@link AbstractClassB}.
     *
     * @param value the value.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    protected AbstractClassB(final String value) {
        this.value = checkNotNull(value, "value cannot be null");
    }
}
