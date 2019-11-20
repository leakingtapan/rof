package com.amazon.mqa.datagen.rof;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Map;

import com.amazon.mqa.datagen.rof.typed.DefaultTypedObjectFactory;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;

/**
 * Creates object by calling create in a list of {@link ObjectFactory} until an object is created
 * or null if failed to create.
 */
public final class DefaultObjectFactory implements ObjectFactory {

    /** List of object factory. */
    private final List<ObjectFactory> objectFactories;

    /**
     * Instantiates a new {@link DefaultObjectFactory}.
     *
     * @param primitiveSuppliers suppliers for primitives.
     * @param pmSuppliers suppliers for method of proxy object.
     * @param arraySizeSupplier supplies size for array.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    public DefaultObjectFactory(final Map<Class<?>, Supplier> primitiveSuppliers,
                                final Map<String, Supplier> pmSuppliers,
                                final Supplier<Integer> arraySizeSupplier) {
        checkNotNull(primitiveSuppliers, "primitiveSuppliers cannot be null");
        checkNotNull(pmSuppliers, "pmSuppliers cannot be null");
        checkNotNull(arraySizeSupplier, "arraySizeSupplier cannot be null");

        // proxy handler
        final Handler handler = new Handler(new DefaultTypedObjectFactory(this), pmSuppliers);

        // the order matters
        this.objectFactories = ImmutableList.of(
                new BasicObjectFactory(primitiveSuppliers),
                new ObjectArrayFactory(this, arraySizeSupplier),
                new EnumFactory(),
                PojoFactory.create(this),
                new InterfaceFactory(this),
                new InterfaceProxyFactory(handler),
                new AbstractClassProxyFactory(handler)
        );
    }

    /**
     * Instantiates a new {@link DefaultObjectFactory}.
     *
     * @param objectFactories list of factory to use.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    DefaultObjectFactory(final List<ObjectFactory> objectFactories) {
        this.objectFactories = checkNotNull(objectFactories, "objectFactories cannot be null");
    }

    @Override
    public <T> T create(final Class<T> clazz) {
        checkNotNull(clazz, "clazz cannot be null");

        return objectFactories.stream()
                .map(f -> f.create(clazz))
                .filter(e -> e != null)
                .findFirst()
                .orElse(null);
    }

}
