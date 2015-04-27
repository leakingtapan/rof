package com.amazon.mqa.datagen.rof.typed;

import java.lang.reflect.Type;

/**
 * Creates object from type.
 */
public interface TypedObjectFactory {

    /**
     * Creates object from type.
     *
     * @param type the type.
     * @return the object.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    Object create(Type type);
}
