package com.amazon.mqa.datagen;

import com.amazon.mqa.datagen.rof.DefaultObjectFactory;
import com.amazon.mqa.datagen.rof.ObjectFactory;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Abstract object factory.
 */
public abstract class AbstractObjectFactory implements ObjectFactory {

    /** Inner object factory. */
    private final ObjectFactory factory;

    /** Factory config. */
    private final Config config;

    /**
     * Instantiate a new {@link AbstractObjectFactory}.
     */
    protected AbstractObjectFactory() {
        this(Config.createDefault());
    }

    /**
     * Instantiate a new {@link AbstractObjectFactory}.
     *
     * @param config factory configuration.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    protected AbstractObjectFactory(final Config config) {
        checkNotNull(config, "config cannot be null");
        checkNotNull(config, "config cannot be null");

        this.config = config;
        this.factory = new DefaultObjectFactory(
                config.getSuppliers(),
                config.getPmSuppliers(),
                config.getArraySizeSupplier());
    }

    /**
     * @return array size.
     */
    private int getArraySize() {
        return config.getArraySizeSupplier().get();
    }

    @Override
    public final <T> T create(final Class<T> clazz) {
        checkNotNull(clazz, "clazz cannot be null");

        return factory.create(clazz);
    }

    /**
     * Creates a list of populated objects with the default size (implementation dependent).
     *
     * @param clazz the class of object to create.
     * @param <T> the type of object to create.
     * @return a list of objects.
     * @throws IllegalArgumentException if the number of objects is negative.
     *
     * @see AbstractObjectFactory#setOf(Class, int)
     */
    public final <T> List<T> listOf(final Class<T> clazz) {
        checkNotNull(clazz, "clazz cannot be null");

        return listOf(clazz, getArraySize());
    }

    /**
     * Creates a list of populated objects.
     *
     * @param clazz the class of object to create.
     * @param howMany the number of objects to create.
     * @param <T> the type of object to create.
     * @return the indicated number of objects, populated with arbitrary data.
     * @throws NullPointerException if an argument is <code>null</code>.
     * @throws IllegalArgumentException if the number of objects is negative.
     *
     * @see AbstractObjectFactory#listOf(Class)
     */
    public final <T> List<T> listOf(final Class<T> clazz, final int howMany) {
        checkNotNull(clazz, "clazz cannot be null");
        checkArgument(howMany >= 0, "howMany can't be negative");

        final List<T> result = Lists.newArrayList();
        for (int i = 0; i < howMany; i++) {
            result.add(create(clazz));
        }

        return result;
    }

    /**
     * Creates a set of populated objects with the default size (implementation dependent).
     *
     * @param clazz the class of object to create.
     * @param <T> the type of object to create.
     * @return a set of objects.
     * @throws NullPointerException if an argument is <code>null</code>.
     *
     * @see AbstractObjectFactory#setOf(Class, int)
     */
    public final <T> Set<T> setOf(final Class<T> clazz) {
        checkNotNull(clazz, "clazz cannot be null");

        return setOf(clazz, getArraySize());
    }

    /**
     * <p>Creates a set of populated objects.</p>
     *
     * <p>
     * The actual number of objects created may occasionally be smaller than requested if it is hard to create
     * the requested number of unique objects. For instance, it isn't possible to create a set of more than
     * two Booleans. This method will attempt to create the desired number of objects, but it may not always
     * succeed.
     * </p>
     *
     * @param clazz the class of object to create.
     * @param howMany the number of objects to create.
     * @param <T> the type of object to create.
     * @return the indicated number of objects, populated with arbitrary data.
     * @throws NullPointerException if an argument is <code>null</code>.
     * @throws IllegalArgumentException if the number of objects is negative.
     *
     * @see AbstractObjectFactory#setOf(Class)
     */
    public final <T> Set<T> setOf(final Class<T> clazz, final int howMany) {
        checkNotNull(clazz, "clazz cannot be null");
        checkArgument(howMany >= 0, "howMany can't be negative");

        return ImmutableSet.copyOf(listOf(clazz, howMany));
    }

    /**
     * <p>Creates a map of populated objects with the default size (implementation dependent).</p>
     *
     * @param keyClass the class of map's key.
     * @param valueClass the class of map's value.
     * @param <K> the type of key.
     * @param <V> the type of value.
     * @return a map of object.
     * @throws NullPointerException if any argument is <code>null</code>.
     *
     * @see AbstractObjectFactory#mapOf(Class, Class, int)
     */
    public final <K, V> Map<K, V> mapOf(final Class<K> keyClass, final Class<V> valueClass) {
        checkNotNull(keyClass, "keyClass cannot be null");
        checkNotNull(valueClass, "valueClass cannot be null");

        return mapOf(keyClass, valueClass, getArraySize());
    }

    /**
     * <p>Creates a map of populated objects.</p>
     *
     * @param keyClass the class of map's key.
     * @param valueClass the class of map's value.
     * @param howMany the number of map entries to create.
     * @param <K> the type of key.
     * @param <V> the type of value.
     * @return a map of object.
     * @throws IllegalArgumentException if the number of objects is negative.
     * @throws NullPointerException if any argument is <code>null</code>.
     *
     * @see AbstractObjectFactory#mapOf(Class, Class)
     */
    public final <K, V> Map<K, V> mapOf(final Class<K> keyClass, final Class<V> valueClass, final int howMany) {
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
