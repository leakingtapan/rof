package com.amazon.mqa.datagen.rof.typed;

import java.util.Collection;

/**
 * Provides concrete collection instance for the given collection type.
 */
interface CollectionInstanceProvider {

    /**
     * Provides the instantiated collection object.
     *
     * @param collectionClass the collection class.
     * @param <T> the collection type
     * @return instantiated collection.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    <T extends Collection> T provide(Class<T> collectionClass);
}
