package com.amazon.mqa.datagen.rof;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

/**
 * Decoration of {@link ObjectFactory} that throws exceptions if the underlying factory cannot create an instance
 * of a class.
 */
public final class NonNullObjectFactory implements ObjectFactory {

    /** The factory to decorate. */
    private final ObjectFactory factory;

    /**
     * Instantiates a new {@link NonNullObjectFactory}.
     *
     * @param factory - the factory to decorate.
     * @throws NullPointerException if an argument is <code>null</code>.
     */
    public NonNullObjectFactory(final ObjectFactory factory) {
        this.factory = requireNonNull(factory, "factory cannot be null");
    }

    @Override
    public <T> T create(final Class<T> clazz) {
        requireNonNull(clazz, "clazz cannot be null");

        return Optional.ofNullable(factory.create(clazz))
                .orElseThrow(() -> new ObjectCreationException("Unsupported class: " + clazz));
    }

}
