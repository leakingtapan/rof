package com.amazon.mqa.datagen.rof.typed;

import static com.google.common.base.Preconditions.checkNotNull;

import com.amazon.mqa.datagen.supplier.MinMaxIntegerSupplier;
import com.google.common.base.Supplier;

import java.lang.reflect.Type;
import java.util.Collection;

/** Creates list, set and collection. */
final class DefaultCollectionFactory implements CollectionFactory {

    /**
     * Creates a {@link DefaultCollectionFactory}.
     *
     * @param typedObjectFactory create objects from its type.
     * @return the default instance.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    public static DefaultCollectionFactory create(final TypedObjectFactory typedObjectFactory) {
        checkNotNull(typedObjectFactory, "typeFactory cannot be null");

        final int minSize = 1;
        final int maxSize = 11;

        return new DefaultCollectionFactory(
                typedObjectFactory,
                new MinMaxIntegerSupplier(minSize, maxSize),
                DefaultCollectionInstanceProvider.INSTANCE
        );
    }

    /** Creates objects from type. */
    private final TypedObjectFactory typedObjectFactory;

    /** Supplies list size. */
    private final Supplier<Integer> sizeSupplier;

    /** Collection provider. */
    private final CollectionInstanceProvider provider;

    /**
     * Instantiates a new {@link DefaultCollectionFactory}.
     *
     * @param typedObjectFactory create objects from its type.
     * @param sizeSupplier supplies collection size.
     * @param provider provides concretes collection instance.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    DefaultCollectionFactory(final TypedObjectFactory typedObjectFactory,
                                    final Supplier<Integer> sizeSupplier,
                                    final CollectionInstanceProvider provider) {
        this.typedObjectFactory = checkNotNull(typedObjectFactory, "typeFactory cannot be null");
        this.sizeSupplier = checkNotNull(sizeSupplier, "sizeSupplier cannot be null");
        this.provider = checkNotNull(provider, "provider cannot be null");
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Collection> T create(final Class<T> collectionClazz, final Type elementType) {
        checkNotNull(collectionClazz, "collectionClazz cannot be null");
        checkNotNull(elementType, "elementType cannot be null");

        final T collection = provider.provide(collectionClazz);

        if (collection != null) {
            final int howMany = sizeSupplier.get();
            for (int i = 0; i < howMany; i++) {
                collection.add(typedObjectFactory.create(elementType));
            }
        }

        return collection;
    }

}
