package com.amazon.mqa.datagen.rof.typed;

import java.lang.reflect.Type;
import java.util.Collection;

/** Creates collection object. */
interface CollectionFactory {

    /**
     * Creates a collection from collection class and its element type.
     *
     * @param collectionClazz the sub-type of collection (eg. List or Set).
     * @param elementType the type of collection element.
     * @param <T> the collection type.
     * @return the collection.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    <T extends Collection> T create(Class<T> collectionClazz, Type elementType);

}
