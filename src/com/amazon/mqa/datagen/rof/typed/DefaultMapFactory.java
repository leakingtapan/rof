package com.amazon.mqa.datagen.rof.typed;

import static com.google.common.base.Preconditions.checkNotNull;

import com.amazon.mqa.datagen.supplier.MinMaxIntegerSupplier;
import com.google.common.base.Supplier;
import com.google.common.collect.Maps;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Create map as hash-map.
 */
final class DefaultMapFactory implements MapFactory {

    /** Supplies map size. */
    private final Supplier<Integer> sizeSupplier;

    /** Creates typed object. */
    private final TypedObjectFactory typedObjectFactory;

    /**
     * Creates a {@link DefaultMapFactory}.
     *
     * @param typedObjectFactory creates objects from type.
     * @return the default instance.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    public static DefaultMapFactory create(final TypedObjectFactory typedObjectFactory) {
        checkNotNull(typedObjectFactory, "typedObjectFactory cannot be null");

        final int minSize = 1;
        final int maxSize = 11;

        return new DefaultMapFactory(typedObjectFactory, new MinMaxIntegerSupplier(minSize, maxSize));
    }

    /**
     * Instantiates a new {@link DefaultMapFactory}.
     *
     * @param typedObjectFactory creates objects from type.
     * @param sizeSupplier supplies array size.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    public DefaultMapFactory(final TypedObjectFactory typedObjectFactory, final Supplier<Integer> sizeSupplier) {
        this.typedObjectFactory = checkNotNull(typedObjectFactory, "typeFactory cannot be null");
        this.sizeSupplier = checkNotNull(sizeSupplier, "sizeSupplier cannot be null");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map create(final Type keyType, final Type valueType) {
        checkNotNull(keyType, "keyType cannot be null");
        checkNotNull(valueType, "valueType cannot be null");

        final Map map = Maps.newHashMap();

        final int howMany = sizeSupplier.get();
        for (int i = 0; i < howMany; i++) {
            map.put(typedObjectFactory.create(keyType), typedObjectFactory.create(valueType));
        }

        return map;
    }
}
