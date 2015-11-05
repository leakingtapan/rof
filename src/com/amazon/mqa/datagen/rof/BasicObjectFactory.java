package com.amazon.mqa.datagen.rof;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Creates customized object with provided supplier for a specific class.
 */
final class BasicObjectFactory implements ObjectFactory {

    /** Map from class to the supplier that can supply object of that class. */
    private final Map<Class<?>, Supplier> suppliers;

    /**
     * Instantiates a new {@link BasicObjectFactory}.
     *
     * @param suppliers a map from class to the supplier that can get the object of that class.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    BasicObjectFactory(final Map<Class<?>, Supplier> suppliers) {
        checkNotNull(suppliers, "suppliers cannot be null");

        this.suppliers = ImmutableMap.copyOf(suppliers);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T create(final Class<T> clazz) {
        checkNotNull(clazz, "clazz cannot be null");

        if (!suppliers.containsKey(clazz)) {
            return null;
        }

        return (T) suppliers.get(clazz).get();
    }

}
