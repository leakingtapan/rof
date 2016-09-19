package com.amazon.mqa.datagen.rof.typed;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.Type;
import java.util.Optional;

/**
 * The default implementation of {@link OptionalFactory}.
 */
final class DefaultOptionalFactory implements OptionalFactory {

    /** Creates objects from type. */
    private final TypedObjectFactory typedObjectFactory;

    /**
     * Instantiates a new {@link DefaultOptionalFactory}.
     *
     * @param typedObjectFactory the object factory to use
     */
    public DefaultOptionalFactory(final TypedObjectFactory typedObjectFactory) {
        checkNotNull(typedObjectFactory, "typedObjectFactory cannot be null");

        this.typedObjectFactory = typedObjectFactory;
    }

    @Override
    public Optional create(final Type optionalType) {
        checkNotNull(optionalType, "optionalType cannot be null");

        return Optional.of(typedObjectFactory.create(optionalType));
    }
}
