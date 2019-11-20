package com.amazon.mqa.datagen;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of {@link Baz}.
 */
public final class DefaultBaz implements Baz {

    /** The type. */
    private final String type;

    /**
     * Instantiates a new {@link DefaultBaz}.
     *
     * @param type - the type.
     * @throws NullPointerException if an argument is <code>null</code>.
     */
    public DefaultBaz(final String type) {
        this.type = requireNonNull(type, "type cannot be null");
    }

    @Override
    public String getType() {
        return type;
    }

}
