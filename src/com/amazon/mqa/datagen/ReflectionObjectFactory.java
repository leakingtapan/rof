package com.amazon.mqa.datagen;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.amazon.mqa.datagen.rof.DefaultObjectFactory;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *     Creates objects with most of the fields populated from a class definition.
 *     Assists in creating random objects for unit tests.
 *     This is the main entry point for object creation.
 *     It can be used to create Primitives, Array, Enum, POJO, Set, List and Map.
 * </p>
 *
 * <p>NOTE: Interface and abstract class are not supported in current version.</p>
 */
public final class ReflectionObjectFactory implements ObjectFactory {

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

    /** Supplies sizes to uses for lists, sets and arrays. */
    private final Supplier<Integer> collectionSizeSupplier;

    /** Creates individual objects. */
    private final com.amazon.mqa.datagen.rof.ObjectFactory objectFactory;

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
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    public ReflectionObjectFactory(final Config config) {
        checkNotNull(config, "config can't be null");

        this.objectFactory = new DefaultObjectFactory(
                config.getSuppliers(),
                config.getPmSuppliers(),
                config.getArraySizeSupplier());
        this.collectionSizeSupplier = config.getArraySizeSupplier();
    }

    /**
     * Instantiates a new {@link ReflectionObjectFactory}.
     *
     * @param collectionSizeSupplier supplies sizes to use for collections.
     * @param objectFactory creates individual objects.
     * @throws NullPointerException if an argument is <code>null</code>.
     */
    ReflectionObjectFactory(
            final Supplier<Integer> collectionSizeSupplier,
            final com.amazon.mqa.datagen.rof.ObjectFactory objectFactory) {
        this.collectionSizeSupplier = checkNotNull(collectionSizeSupplier, "collectionSizeSupplier can't be null");
        this.objectFactory = checkNotNull(objectFactory, "objectFactory can't be null");
    }

    @Override
    public <T> T create(final Class<T> clazz) {
        checkNotNull(clazz, "clazz cannot be null");

        return objectFactory.create(clazz);
    }

    @Override
    public <T> List<T> listOf(final Class<T> clazz) {
        checkNotNull(clazz, "clazz cannot be null");

        return listOf(clazz, collectionSizeSupplier.get());
    }

    @Override
    public <T> List<T> listOf(final Class<T> clazz, final int howMany) {
        checkNotNull(clazz, "clazz cannot be null");
        checkArgument(howMany >= 0, "howMany can't be negative");

        final List<T> result = Lists.newArrayList();
        for (int i = 0; i < howMany; i++) {
            result.add(create(clazz));
        }

        return result;
    }

    @Override
    public <T> Set<T> setOf(final Class<T> clazz) {
        checkNotNull(clazz, "clazz cannot be null");

        return setOf(clazz, collectionSizeSupplier.get());
    }

    @Override
    public <T> Set<T> setOf(final Class<T> clazz, final int howMany) {
        checkNotNull(clazz, "clazz cannot be null");
        checkArgument(howMany >= 0, "howMany can't be negative");

        return ImmutableSet.copyOf(listOf(clazz, howMany));
    }

    @Override
    public <K, V> Map<K, V> mapOf(final Class<K> keyClass, final Class<V> valueClass) {
        checkNotNull(keyClass, "keyClass cannot be null");
        checkNotNull(valueClass, "valueClass cannot be null");

        return mapOf(keyClass, valueClass, collectionSizeSupplier.get());
    }

    @Override
    public <K, V> Map<K, V> mapOf(final Class<K> keyClass, final Class<V> valueClass, final int howMany) {
        checkNotNull(keyClass, "keyClass cannot be null");
        checkNotNull(valueClass, "valueClass cannot be null");
        checkArgument(howMany >= 0, "howMany cannot be negative");

        final Map<K, V> map = Maps.newHashMap();
        for (int i = 0; i < howMany; i++) {
            map.put(create(keyClass), create(valueClass));
        }

        return map;
    }
}
