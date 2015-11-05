package com.amazon.mqa.datagen.rof.typed;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Default implementation of {@link CollectionInstanceProvider}.
 */
enum DefaultCollectionInstanceProvider implements CollectionInstanceProvider {

    /** Singleton instance. */
    INSTANCE;

    @Override
    public <T extends Collection> T provide(final Class<T> collectionClass) {
        checkNotNull(collectionClass, "collectionClass cannot be null");

        if (collectionClass.equals(List.class)) {
            return (T) Lists.newArrayList();
        } else if (collectionClass.equals(Set.class)) {
            return (T) Sets.newHashSet();
        } else if (collectionClass.equals(Collection.class)) {
            return (T) Lists.newArrayList();
        }

        return null;
    }

}
