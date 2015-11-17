package com.amazon.mqa.datagen;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * <p>
 *     Creates objects with most of the fields populated from a class definition.
 *     Assists in creating random objects for unit tests.
 *     This is the main entry point for object creation.
 *     It can create Primitives, Array, Enum, POJO, Set, List and Map.
 *     It can create Proxy for interface and abstract class.
 * </p>
 */
public final class ReflectionObjectFactory extends AbstractObjectFactory implements ObjectFactory {

    /**
     * Creates the object using default configuration.
     *
     * @param clazz the class to create.
     * @param <T> the type of class.
     * @return the instance.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    public static <T> T createObject(final Class<T> clazz) {
        checkNotNull(clazz, "clazz cannot be null");

        return new ReflectionObjectFactory(Config.createDefault()).create(clazz);
    }

    /**
     * Instantiates a new {@link ReflectionObjectFactory} with the default configuration.
     */
    public ReflectionObjectFactory() {
        this(Config.createDefault());
    }

    /**
     * Instantiates a new {@link ReflectionObjectFactory}.
     *
     * @param config the configuration to use.
     */
    public ReflectionObjectFactory(final Config config) {
        super(config);
    }

}
