package com.amazon.mqa.datagen;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Supplier;

/**
 * Builds {@link ObjectFactory}.
 */
public final class ObjectFactoryBuilder {

    /** Configurations. */
    private Config config = Config.createDefault();

    /**
     * @see Config#withSupplier(Class, Supplier)
     * @param clazz the class to supplier
     * @param supplier the supplier of class.
     * @param <T> the type of class.
     * @return the builder.
     */
    public <T> ObjectFactoryBuilder withSupplier(final Class<T> clazz, final Supplier<T> supplier) {
        checkNotNull(clazz, "clazz cannot be null");
        checkNotNull(supplier, "supplier cannot be null");

        this.config = config.withSupplier(clazz, supplier);

        return this;
    }

    /**
     * @see Config#withSupplier(String, Supplier)
     * @param method the proxy method name.
     * @param supplier the supplier of class.
     * @param <T> the type of class.
     * @return the builder.
     */
    public <T> ObjectFactoryBuilder withSupplier(final String method, final Supplier<T> supplier) {
        checkNotNull(method, "method cannot be null");
        checkNotNull(supplier, "supplier cannot be null");

        this.config = config.withSupplier(method, supplier);

        return this;
    }

    /**
     * Builds {@link ObjectFactory}.
     *
     * @return the instance.
     */
    public ObjectFactory build() {
        return new ReflectionObjectFactory(config);
    }

}
