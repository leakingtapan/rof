package com.amazon.mqa.datagen.rof.typed;

import java.lang.reflect.Type;
import java.util.Optional;

/**
 * Creates {@link Optional} objects.
 */
interface OptionalFactory {

    /**
     * Creates an {@link Optional} from a type.
     *
     * @param optionalType the type to create an {@link Optional} for.
     * @return the optional.
     */
    Optional create(Type optionalType);
}
