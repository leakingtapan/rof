package com.amazon.mqa.datagen.rof;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Supplier;

import java.lang.reflect.Array;

/**
 * Creates Object array with each element being instantiated with some specific value.
 */
final class ObjectArrayFactory implements ObjectFactory {

    /** Creates objects. */
    private final ObjectFactory objectFactory;

    /** Supplies array size. */
    private final Supplier<Integer> arraySizeSupplier;

    /**
     * Instantiates a new {@link ObjectArrayFactory}.
     *
     * @param objectFactory creates objects.
     * @param arraySizeSupplier supplies array size.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    ObjectArrayFactory(final ObjectFactory objectFactory, final Supplier<Integer> arraySizeSupplier) {
        this.objectFactory = checkNotNull(objectFactory, "objectFactory cannot be null");
        this.arraySizeSupplier = checkNotNull(arraySizeSupplier, "arraySizeSupplier cannot be null");
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T create(final Class<T> clazz) {
        checkNotNull(clazz, "clazz cannot be null");

        if (!clazz.isArray()) {
            return null;
        }

        final Class<?> componentType = clazz.getComponentType();
        final int howMany = arraySizeSupplier.get();

        final Object array = Array.newInstance(componentType, howMany);
        for (int i = 0; i < howMany; i++) {
            Array.set(array, i, objectFactory.create(componentType));
        }

        return (T) array;
    }

}
